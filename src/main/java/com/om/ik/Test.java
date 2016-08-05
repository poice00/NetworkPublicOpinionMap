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

import com.om.domain.Datasource1;
import com.om.service.DataSourceService;

@SuppressWarnings("unchecked")
public class Test {
	public static void main(String[] args) throws IOException {
		//分词  
		//创建分词对象  
		String text = "首先想一个简单的问题，如果你现在想看个电影，但你不知道具体看哪部，你会怎么做？大部分的人会问问周围的朋友，看看最近有什么好看的电影推荐，";
		String text1 = "雷永远方";
		System.out.println(DivideWords.split(text1));
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
	}
}
