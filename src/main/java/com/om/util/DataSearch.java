package com.om.util;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.om.domain.DataSource;
import com.om.domain.Theme;
import com.om.domain.Writer;
import com.om.service.ThemeService;


public class DataSearch {
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<DataSource> selectSourceData() {
		SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		String sql="select * from data_source where ssyClassifyResult='经济'";		
		List<DataSource> dataSources=session.createSQLQuery(sql).addEntity(DataSource.class).list();
		session.getTransaction().commit();
		session.clear();
		session.close();
		sessionFactory.close();
		return dataSources;
	}
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Theme> selectTheme() {
		SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		String sql="select * from theme where totalVisit > 0 ";		
		List<Theme> themes=session.createSQLQuery(sql).addEntity(Theme.class).list();
		session.getTransaction().commit();
		session.clear();
		session.close();
		sessionFactory.close();
		return themes;
	}
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<DataSource> selectSourceDataByIds(HashSet<String> set) {
		SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		String sql="select * from data_source where data_source_id in (";
		int mm=1;
		for (String str:set) {
			if (mm<set.size()) {
				sql+="'"+str+"',";
			} else {
				sql+="'"+str+"')";
			}
			mm=mm+1;
		}
		List<DataSource> dataSources=session.createSQLQuery(sql).addEntity(DataSource.class).list();
		session.getTransaction().commit();
		session.clear();
		session.close();
		sessionFactory.close();
		return dataSources;
	}
	//将聚好后的专题插入数据库中
	@SuppressWarnings({ "deprecation", "unchecked" })
	public void titleInsert(HashSet<String> set) {
		SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		String sql="insert into theme (theme_id,theme_intro,theme_state) values ";
		int mm=1;
		for (String str:set) {
			if (mm<set.size()) {
				sql+="('类簇"+String.valueOf(mm)+"主题','"+str+"','已计算'),";
			} else {
				sql+="('类簇"+String.valueOf(mm)+"主题','"+str+"','已计算')";
			}
			mm=mm+1;
		}
		System.out.println(sql);
		session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();
		session.clear();
		session.close();
		sessionFactory.close();
	}
	//计算某些帖子的回复数量
	@SuppressWarnings({ "deprecation", "unchecked" })
	public int searchPartNum(HashSet<String> set) {
		SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		String sql="select * from writer where writer_id in (select writer_id from comment where data_source_id in (";
		int mm=1;
		for (String str:set) {
			if (mm<set.size()) {
				sql+="'"+str+"',";
			} else {
				sql+="'"+str+"'))";
			}
			mm=mm+1;
		}
		System.out.println(sql);
		int partNum=session.createSQLQuery(sql).addEntity(Writer.class).list().size();
		session.getTransaction().commit();
		session.clear();
		session.close();
		sessionFactory.close();
		return partNum;
	}
	@SuppressWarnings({ "deprecation", "unchecked" })
	public void titleInsert(String[] themes, int[] totalTopics, int[] totalVisits, int[] totalReplys,
			int[] totalPartINs, Map<Integer, HashSet<String>> topTenCluster) {
		// TODO Auto-generated method stub
		SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		String sql="insert into theme (theme_id,theme_intro,theme_state,totalTopicNum,totalVisit,totalReply,totalPart,topicId) values ";
		int mm=0;
		for (Integer ii:topTenCluster.keySet() ) {
			String str=String.valueOf(ii);
			String douid="";
			int keySize=1;
			for (String string:topTenCluster.get(ii)) {
				if (keySize<topTenCluster.get(ii).size()) {
					douid+=string+",";
				} else {
					douid+=string;
				}
				keySize=keySize+1;
			}
			if (mm< topTenCluster.size()-1) {
				sql+="('类簇"+str+"主题','"+themes[mm]+"','已计算',"+totalTopics[mm]+","+totalVisits[mm]+","+totalReplys[mm]+","+totalPartINs[mm]+",'"+douid+"'),";
			} else {
				sql+="('类簇"+str+"主题','"+themes[mm]+"','已计算',"+totalTopics[mm]+","+totalVisits[mm]+","+totalReplys[mm]+","+totalPartINs[mm]+",'"+douid+"')";
			}
			mm=mm+1;
		}
		System.out.println(sql);
		session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();
		session.clear();
		session.close();
		sessionFactory.close();
	}
	//用聚类好的结果对数据库进行更新
	@SuppressWarnings({ "deprecation", "unchecked" })
	public void upDatasource(String id,String string) {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String sql = "update data_source set clusterResult = '"+string+"' where data_source_id = '"+id+"'";
		System.out.println(sql);
		session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();
		session.clear();
		session.close();
		sessionFactory.close();
	}
	
	//查询单条记录
	@SuppressWarnings({ "deprecation", "unchecked" })
	public DataSource oneDatasource(String id) {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String sql = "select * from data_source where data_source_id = '" + id + "'";
		List<DataSource> dataSources=session.createSQLQuery(sql).addEntity(DataSource.class).list();
		DataSource dataSource=dataSources.get(0);
		session.getTransaction().commit();
		session.clear();
		session.close();
		sessionFactory.close();
		return dataSource;
	}
	
	//更新热点子专题的热度
	@SuppressWarnings({ "deprecation", "unchecked" })
	public void updatethemeHot(String id,Double hot) {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String sql = "update theme set theme_hot = "+hot+" where theme_id = '" + id + "'";
		session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();
		session.clear();
		session.close();
		sessionFactory.close();
	}
	//按热度查找theme
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Theme> searchthemeHot() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String sql = "select * from theme where totalVisit > 0 order by theme_hot desc";
		List<Theme> themes=session.createSQLQuery(sql).addEntity(Theme.class).list();
		session.getTransaction().commit();
		session.clear();
		session.close();
		sessionFactory.close();
		return themes;
	}
	//更新专题的热度
	@SuppressWarnings({ "deprecation", "unchecked" })
	public void updateDataHotnum(String id,Double hot) {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String sql = "update data_source set hotNum = " + hot + " where data_source_id = '" + id + "'";
		session.createSQLQuery(sql).executeUpdate();
		session.getTransaction().commit();
		session.clear();
		session.close();
		sessionFactory.close();
	}
	//查询相关热度
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<DataSource> SelectDataHotnum() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String sql = "select * from data_source where hotNum > 0 order by hotNum desc";
		System.out.println(sql);
		List<DataSource> dataSources=session.createSQLQuery(sql).addEntity(DataSource.class).list();
		session.getTransaction().commit();
		session.clear();
		session.close();
		sessionFactory.close();
		return dataSources;
	}
	public List<DataSource> SelectFangdichan() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String sql = "select * from data_source where clusterResult = '房地产'";
		System.out.println(sql);
		List<DataSource> dataSources=session.createSQLQuery(sql).addEntity(DataSource.class).list();
		session.getTransaction().commit();
		session.clear();
		session.close();
		sessionFactory.close();
		return dataSources;
	}
}
