package com.om.opinionleader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.om.domain.DataSource;
import com.om.service.DataSourceNoTagService;

public class UpdateData {
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
	public Map<String, Double> getData(){
		BufferedReader br;
		Map<String, Double> maps = new HashMap<String, Double>();
		try {
			String x ;
			br = new BufferedReader(new FileReader(new File("E:/datSource.sql")));
			while((x = br.readLine())!=null){
				String x1 = x.split(",")[0];
				double x2 = Double.parseDouble(x.split(",")[1]);
				maps.put(x1,x2);
			}
		}
		catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return maps;
	}
	public void updateDate(Map<String, Double> DataMap){
		DataSourceNoTagService dataSourceNoTagService = (DataSourceNoTagService) ctx.getBean("dataSourceNoTagServiceImpl");
		List<DataSource> dataList = dataSourceNoTagService.getByName("经济");
		for (DataSource dataSource : dataList) {
			if(DataMap.get(dataSource.getDataSourceId())!=null){
				dataSource.setEmotionValue(DataMap.get(dataSource.getDataSourceId()));
			}
			dataSourceNoTagService.update(dataSource);
		}
	}
	public static void main(String[] args) {
		UpdateData ud = new UpdateData();
		Map<String, Double> DataMap = ud.getData();
		ud.updateDate(DataMap);

		
	}
}
