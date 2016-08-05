package com.om.opinionleader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.om.domain.Comment;
import com.om.domain.DataSource;
import com.om.domain.Writer;
import com.om.domain.WriterFactor;
import com.om.service.CommentService;
import com.om.service.DataSourceNoTagService;
import com.om.service.WriterFactorService;
import com.om.service.WriterService;

public class Activedegree {
	static ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	static SessionFactory sf = (SessionFactory) ctx.getBean("sessionFactory");
	static Session session=null;
	static Transaction tx=null;
	public void open(){
		session = sf.openSession();
		tx = session.beginTransaction();
	}
	public void close(){
		tx.commit();
		session.close();
		ctx.close();
	}
	public List<WriterFactor> writerList(){
		WriterFactorService writerFactorService = (WriterFactorService) ctx.getBean("writerFactorServiceImpl");
		List<WriterFactor> writerList = writerFactorService.findAll();
		return writerList;
	}
	private int getTipReply(Writer writer) {
		CommentService commentService = (CommentService) ctx.getBean("commentServiceImpl");
		//计算作者在经济领域下参与的回复数
		List<Comment> commentList = commentService.getByTipReplyWriter(writer);
		return commentList.size();
	}
	
	public void calActiveDegree(List<WriterFactor> writerList){
		open();
		WriterFactorService writerFactorService = (WriterFactorService) ctx.getBean("writerFactorServiceImpl");
		double g1 = 0.6;
		double g2 = 0.4;
		List<Double> NList = new ArrayList<Double>();
		//计算maxN
		for (WriterFactor writerfactor : writerList) {
			double x = writerfactor.getPublishTipNum();
			int N1 = (int)x;
			int N2 = getTipReply(writerfactor.getWriter());
			double N = Util.calActivePre(N1, N2, g1, g2);
			NList.add(N);
		}
		for (WriterFactor writerfactor : writerList) {
			//找出每个发帖人在经济领域下的帖子，统计浏览次数
			double x = writerfactor.getPublishTipNum();
			int N1 = (int)x;
			int N2 = getTipReply(writerfactor.getWriter());
			double activeDegree = Util.calActive(N1, N2, g1, g2, NList);
			WriterFactor wf = writerFactorService.getByWriterfactorId(writerfactor.getWriterFactorId());
			wf.setAuthorActiveDegree(activeDegree);
			wf.setReplyTipNum((double) N2);
			writerFactorService.update(wf);
			System.out.println(writerfactor.getWriter().getWriterName() +"　活跃度： " + activeDegree);
		}
		close();
	}
	public void updateInf(List<WriterFactor> writerList){
		WriterFactorService writerFactorService = (WriterFactorService) ctx.getBean("writerFactorServiceImpl");
		double g1 = 0.15;
		double g2 = 0.25;
		double g3 = 0.3;
		double g4 = 0.3;
		double g5 = 0.85;
		List<Double> N1List = new ArrayList<Double>();
		List<Double> NList = new ArrayList<Double>();
		//计算maxN
		for (WriterFactor writerFactor : writerList) {
			double n1 = writerFactor.getBrosesTimes();
			double n2 = writerFactor.getPublishTipNum();
			double n3 = writerFactor.getTipReplyNum();
			double n4 = writerFactor.getFansNum();
			int N1 = (int) n1;
			int N2 = (int) n2;
			int N3 = (int) n3;
			int N4 = (int) n4;
			double N = Util.calfluencePre(N2, N3, N4, g2, g3, g4);
			NList.add(N);
			N1List.add((double) N1);
		}
		for (WriterFactor writerFactor : writerList) {
			double n1 = writerFactor.getBrosesTimes();
			double n2 = writerFactor.getPublishTipNum();
			double n3 = writerFactor.getTipReplyNum();
			double n4 = writerFactor.getFansNum();
			int N1 = (int) n1;
			int N2 = (int) n2;
			int N3 = (int) n3;
			int N4 = (int) n4;
			double influence = Util.calfluence(N1, N2, N3, N4, g1, g2, g3, g4, g5, N1List, NList);
			WriterFactor wf = writerFactorService.getByWriterfactorId(writerFactor.getWriterFactorId());
			wf.setAuthorInfluence(influence);
			writerFactorService.update(wf);
		}
//		close();
	}
	public void updateInf2(List<WriterFactor> writerList){
		WriterFactorService writerFactorService = (WriterFactorService) ctx.getBean("writerFactorServiceImpl");
		DataSourceNoTagService dataSourceNoTagService = (DataSourceNoTagService) ctx.getBean("dataSourceNoTagServiceImpl");
		for (WriterFactor writerFactor : writerList) {
			List<DataSource> dataList = dataSourceNoTagService.getByWriterId("经济", writerFactor.getWriterFactorId());
			double emv = 0.0;
			if(dataList.size()!=0){
				for (DataSource dataSource : dataList) {
					emv += dataSource.getEmotionValue();
				}
				double emvresult = emv/dataList.size();
				WriterFactor wf = writerFactorService.getByWriterfactorId(writerFactor.getWriterFactorId());
				wf.setAuthorInfluence2(emvresult);
				writerFactorService.update(wf);
			}
		}
//		close();
	}
	public void getTopInf(){
		WriterFactorService writerFactorService = (WriterFactorService) ctx.getBean("writerFactorServiceImpl");
		List<WriterFactor> writerFactorList = writerFactorService.findByActiveDegree(10);
		for (int i = 0; i < 20; i++) {
			System.out.println(writerFactorList.get(i).getWriter().getWriterName() +" : " + writerFactorList.get(i).getAuthorActiveDegree()
					+",发帖数" +writerFactorList.get(i).getPublishTipNum()+",回复的帖子" + writerFactorList.get(i).getReplyTipNum());
		}
		
	}
	public static void main(String[] args) {
		Activedegree ad = new Activedegree();
		List<WriterFactor> writerList =ad.writerList();
//		ad.calActiveDegree(writerList);;
//		ad.getTopInf();
		ad.updateInf2(writerList);
//		ad.updateInf(writerList);
	}
}
