package com.om.opinionleader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.om.domain.Comment;
import com.om.domain.DataSource;
import com.om.domain.Datasource1;
import com.om.domain.Writer;
import com.om.domain.WriterFactor;
import com.om.service.CommentService;
import com.om.service.DataSourceNoTagService;
import com.om.service.DataSourceService;
import com.om.service.WriterFactorService;
import com.om.service.WriterService;

public class Influence {
	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	SessionFactory sf = (SessionFactory) ctx.getBean("sessionFactory");
	Session session=null;
	Transaction tx=null;
	public void open(){
		session = sf.openSession();
		tx = session.beginTransaction();
	}
	public void close(){
		tx.commit();
		session.close();
		ctx.close();
	}
	public Set<Writer> writerList(){
		DataSourceNoTagService dataSourceNoTagService = (DataSourceNoTagService) ctx.getBean("dataSourceNoTagServiceImpl");
		List<DataSource> dataList = dataSourceNoTagService.getByName("经济");
		Set<Writer> writerList = new HashSet<Writer>();
		//找出经济领域下的发帖人
		for (DataSource data : dataList) {
			data.getVisitNum();
			writerList.add(data.getWriter());
		}
		return writerList;
	}
	public void CalInf(){
		open();
		DataSourceNoTagService dataSourceNoTagService = (DataSourceNoTagService) ctx.getBean("dataSourceNoTagServiceImpl");
		List<DataSource> dataList = dataSourceNoTagService.getByName("经济");
		WriterFactorService writerFactorService = (WriterFactorService) ctx.getBean("writerFactorServiceImpl");
		//得到经济领域下的作者
		Set<Writer> writerList = writerList();
		System.out.println("----经济领域作者------ : 共 " +writerList.size()+ " 个");
		List<Double> N1List = new ArrayList<Double>();
		List<Double> NList = new ArrayList<Double>();
		//g1取0.05，g2取0.25，g3取0.3，g4取0.4
		double g1 = 0.05;
		double g2 = 0.25;
		double g3 = 0.3;
		double g4 = 0.4;
		double g5 = 0.95;
		//计算maxN
		for (Writer writer : writerList) {
			int a[] = getBrowersNum(writer,dataList);
			int N1 = a[0];
			int N2 = a[1];
			int N3 = a[2];
			int N4 = writer.getFansNum();
			double N = Util.calfluencePre(N2, N3, N4, g2, g3, g4);
			NList.add(N);
			N1List.add((double) N1);
		}
		for (Writer writer : writerList) {
			//找出每个发帖人在经济领域下的帖子，统计浏览次数
			int a[] = getBrowersNum(writer,dataList);
			int N1 = a[0];
			int N2 = a[1];
			int N3 = a[2];
			int N4 = writer.getFansNum();
			double influence = Util.calfluence(N1, N2, N3, N4, g1, g2, g3, g4, g5, N1List, NList);
			WriterFactor wf = new WriterFactor();
			wf.setWriter(writer);
			wf.setAuthorInfluence(influence);
			wf.setBrosesTimes((double) N1);
			wf.setFansNum((double) N4);
			wf.setPublishTipNum((double) N2);
			wf.setTipReplyNum((double) N3);
			writerFactorService.save(wf);
//			System.out.println(writer.getWriterName() + " 访问数: " + N1 + " 发帖数： " + N2 +" 回复数 ：" + N3 + " 粉丝数:" + N4);
//			System.out.println("影响力： " + influence);
		}
		close();
	}
	public void getTopInf(){
		WriterFactorService writerFactorService = (WriterFactorService) ctx.getBean("writerFactorServiceImpl");
		List<WriterFactor> writerFactorList = writerFactorService.findByInfluence(10);
		for (int i = 0; i < 20; i++) {
			System.out.println(writerFactorList.get(i).getWriter().getWriterName() +" : " + writerFactorList.get(i).getAuthorInfluence()
			+",点击数:　" + writerFactorList.get(i).getBrosesTimes() + ",发帖数： "+ writerFactorList.get(i).getPublishTipNum() + "，帖子回复数： "
			+ writerFactorList.get(i).getTipReplyNum()+",粉丝数 ： " + writerFactorList.get(i).getFansNum());
		}
		
	}
	
	private int[] getBrowersNum(Writer writer,List<DataSource> dataList) {
		CommentService commentService = (CommentService) ctx.getBean("commentServiceImpl");
		//浏览次数
		int vistorNum = 0;
		//发帖数
		int num = 0;
		int a[]  = new int[3];
		for (DataSource ds : dataList) {
			if(writer.getWriterName().equals(ds.getWriter().getWriterName())){
				vistorNum += ds.getVisitNum();
				num ++;
			}
		}
		//计算回复数
		List<Comment> commentList = commentService.getByWriter(writer);
		a[0] = vistorNum; 
		a[1] = num; 
		a[2] = commentList.size();
		return a;
	}
	public static void main(String[] args) {
		//new Influence().CalInf();
		Influence in = new Influence();
		in.getTopInf();
	}
}
	
