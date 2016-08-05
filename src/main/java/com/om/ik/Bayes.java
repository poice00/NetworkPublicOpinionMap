package com.om.ik;

import java.util.List;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.om.domain.DataSource;
import com.om.service.DataSourceNoTagService;

public class Bayes {
	static ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	static SessionFactory sf = (SessionFactory) ctx.getBean("sessionFactory");
	static Session session=null;
	static Transaction tx=null;
	public static  void open(){
		session = sf.openSession();
		tx = session.beginTransaction();
	}
	public static void close(){
		tx.commit();
		session.close();
		ctx.close();
	}
	public static void main(String[] args) {
		open();
		System.out.println("初始化分类器。。");
		DataSourceNoTagService dataSourceNoTagService = (DataSourceNoTagService) ctx.getBean("dataSourceNoTagServiceImpl");
		List<DataSource> dataList = dataSourceNoTagService.findAll();	
		BayesClassifier classifier = new BayesClassifier();//构造Bayes分类器
		System.out.println("分类器构造完毕！");
		for (DataSource ds : dataList) {
			String content = ds.getContent();
	        String result = classifier.classify(content);//进行分类
	        //ds.setSsyClassifyResult(result);
	        //dataSourceNoTagService.update(ds);
	        System.out.println("此项属于["+result+"]");
//	        break;
		}
//		String text = "污染治理";
//		BayesClassifier classifier = new BayesClassifier();//构造Bayes分类器
//        String result = classifier.classify(text);//进行分类
//        System.out.println("此项属于["+result+"]");
        close();
	}
}
