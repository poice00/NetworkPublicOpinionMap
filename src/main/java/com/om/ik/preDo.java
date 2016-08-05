package com.om.ik;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.om.domain.DataSource;
import com.om.domain.Datasource1;
import com.om.service.DataSourceNoTagService;
import com.om.service.DataSourceService;

@SuppressWarnings("unchecked")
public class preDo {
	public static void main(String[] args) throws IOException {
		//分词  
		//创建分词对象  
//		String text = "首先想一个简单的问题，如果你现在想看个电影，但你不知道具体看哪部，你会怎么做？大部分的人会问问周围的朋友，看看最近有什么好看的电影推荐，";
//		String text1 = "雷永远方";
//		System.out.println(DivideWords.split(text1));
		// 取数据
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//		SessionFactory sf = (SessionFactory) ctx.getBean("sessionFactory");
//		Session session = sf.openSession();
//		DataSourceService dataSourceService = (DataSourceService) ctx.getBean("dataSourceServiceImpl");
//		List<Datasource1> dataList = dataSourceService.findAll();
//		for (Datasource1 datasource1 : dataList) {
//			String result = datasource1.getDataTitle();
//			System.out.println(datasource1.getDataTitle());
//			System.out.println(DivideWords.split(result));
//			break;
//		}
//		Transaction tx = session.beginTransaction();
//		tx.commit();
//		session.close();
//		ctx.close();
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		SessionFactory sf = (SessionFactory) ctx.getBean("sessionFactory");
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		DataSourceNoTagService dsnt = (DataSourceNoTagService) ctx.getBean("dataSourceNoTagServiceImpl");
		System.out.println(dsnt);
		String zz = "经济论坛";
		String hb = "房产观澜";
		String jj = "股市论坛";
		List<String> typeList = new ArrayList<String>();
		typeList.add(zz);
		typeList.add(hb);
		typeList.add(jj);
		for (String type : typeList) {
			List<DataSource> dataList = dsnt.getByWebsiteName(type);
			for (DataSource dataSource : dataList) {
				System.out.println(dataSource.getTitle());
				dataSource.setSsyClassifyResult("经济");
				dsnt.update(dataSource);
			}
		}
		tx.commit();
		session.close();
		ctx.close();
	}
}
