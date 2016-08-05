package com.om.next;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.om.domain.DataSource;
import com.om.service.DataSourceNoTagService;
/*
 *  算法：DBSCAN
	输入：    E — 半径
	      MinPts — 给定点在E领域内成为核心对象的最小领域点数
	      D — 集合
	输出：    目标类簇集合
	方法：repeat
	1) 判断输入点是否为核心对象
	2) 找出核心对象的E领域中的所有直接密度可达点
	  util 所有输入点都判断完毕
	  repeat
	     	针对所有核心对象的E领域所有直接密度可达点找到最大密度相连对象集合，中间涉及到一些密度可达对象的合并。
	  Util 所有核心对象的E领域都遍历完毕
 *
 */
public class DBScan {
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
	/**
	 * 
	 * @param dataSources 数据源
	 * @param radius 半径
	 * @param MinPts 最小邻域点数
	 * @return
	 */
	 public List<Cluster> doDbscanAnalysis(List<DataPoint> dataPointsList,
	            double radius, int MinPts) {
		 DataSourceNoTagService dataSourceNoTagService = (DataSourceNoTagService) ctx.getBean("dataSourceNoTagServiceImpl");
		 //先划分核心点，边缘点
		 System.out.println("开始划分。。");
		 List<Cluster> clusterList=new ArrayList<Cluster>();
		 for (int i=0; i<dataPointsList.size();i++) {
			 System.out.println(i);
			 DataPoint ds = dataPointsList.get(i);
			 List<DataPoint> arrivableObjects=isKeyAndReturnObjects(ds,dataPointsList,radius,MinPts);//得到核心点集合
			 if(arrivableObjects!=null){
//				 for (DataPoint dataPoint : arrivableObjects) {
//					 DataSource x = dataSourceNoTagService.getByDocumentId(dataPoint.getDataPointName());
//					 x.setClusterResult2("Cluster "+i);
//					 dataSourceNoTagService.update(x);
//				 }
                 Cluster tempCluster=new Cluster();
                 tempCluster.setClusterName("Cluster "+i);
                 tempCluster.setDocument(arrivableObjects);;
                 clusterList.add(tempCluster);
            }
		 }
		 System.out.println("核心簇： " + clusterList.size());
		 Collections.sort(clusterList, new Comparator<Cluster>(){

			@Override
			public int compare(Cluster o1, Cluster o2) {
				int x1 = o1.getDocument().size();
				int x2 = o2.getDocument().size();
				if(x1>x2) 
					return -1;
				else
					return 1 ;
//				double d1=(double)o1.getDocument().size();
//				double d2=(double)o2.getDocument().size();
//				return d1.compareTo(d2);
			}
			 
		 });
		 for (int i = 0; i < clusterList.size(); i++) {
			 Cluster c = clusterList.get(i);
			 System.out.println(c.getDocument().size() + ":" + c.getClusterName());
		 }
		 for (int i = 0; i <= 3 ; i++) {
			 Cluster c = clusterList.get(i);
			 for (DataPoint d : c.getDocument()) {
				 DataSource x = dataSourceNoTagService.getByDocumentId(d.getDataPointName());
				 if(x.getClusterResult2()==null){
					 x.setClusterResult2(c.getClusterName());
					 dataSourceNoTagService.update(x);
				 }else{
					 x.setClusterResult2(x.getClusterResult2() +","+c.getClusterName());
					 dataSourceNoTagService.update(x);
				 }
			}
		 }
	 	//合并
		 System.out.println("开始合并。。");
		 for(int i=0;i<clusterList.size();i++){
             for(int j=0;j<clusterList.size();j++){
//            	 System.out.println(j);
                  if(i!=j){
                      Cluster clusterA=clusterList.get(i);
                      Cluster clusterB=clusterList.get(j);

                      List<DataPoint> dpsA=clusterA.getDocument();
                      List<DataPoint> dpsB=clusterB.getDocument();

                      boolean flag=mergeList(dpsA,dpsB);
//                      System.out.println("flag: " + flag);
                      if(flag){
                          clusterList.set(j, new Cluster());
//                    	  clusterList.remove(j);
                      }
                  }
             }
         }
		 List<Cluster> list=new ArrayList<Cluster>();
         for (int i = 0; i < clusterList.size(); i++) {
			if(clusterList.get(i).getClusterName() != null){
				list.add(clusterList.get(i));
			}
		}
         for (Cluster cluster : list) {
			System.out.print("簇名： " + cluster.getClusterName());
			System.out.println("\t" + "一共有" + cluster.getDocument().size() + "篇");
            List<DataPoint> clusterLists=cluster.getDocument();
            for (DataPoint dataPoint : clusterLists) {
            	DataSource ds = dataSourceNoTagService.getByDocumentId(dataPoint.getDataPointName());
            	ds.setClusterResult2(cluster.getClusterName());
            	dataSourceNoTagService.update(ds);
			}
			
		}
		 System.out.println("聚成了： " + list.size() + "个类");
         return clusterList;
	 }
	 /**
	  * 
	  * @param ds 当前的文章
	  * @param dataSource 所有的文庄
	  * @param radius 半径
	  * @param minPts 最小点
	  * @return 核心集合
	  */
	 private List<DataPoint> isKeyAndReturnObjects(DataPoint ds, List<DataPoint> dataSource, double radius, int minPts) {
		 
		 List<DataPoint> arrivableObjects=new ArrayList<DataPoint>(); //用来存储所有直接密度可达对象
	       for(DataPoint dp:dataSource){
	          double distance=getDistance(ds,dp);
//	          System.out.println(distance);
	          if(distance<=radius){
	              arrivableObjects.add(dp);
	          }
	       }
	       if(arrivableObjects.size()>=minPts){
	    	   ds.setKey(true);
	           return arrivableObjects;
	       }
	       return null; 
	}
	/*private double getDistance(DataPoint ds, DataPoint dp) {
		Map<String, Double> map1, map2;
		map1 = ds.getWeight();
		map2 = dp.getWeight();
		double x = 0.0; //积
		double mo1 = 0.0; //map1的模
		double mo2 = 0.0; //map2的模
		for (String word : map1.keySet()) {
			if(map2.containsKey(word)){
				x += map1.get(word)*map2.get(word);
			}
			mo1 += map1.get(word)*map1.get(word);
		}
		for (String word : map2.keySet()) {
			mo2 += map2.get(word)*map2.get(word);
		}
		double y = Math.sqrt(mo1) * Math.sqrt(mo2);
		return x/y;
	}*/
	 //欧式距离
	 private double getDistance(DataPoint ds, DataPoint dp) {
		Map<String, Double> map1, map2;
		map1 = ds.getWeight();
		map2 = dp.getWeight();
		double distance = 0.0; //
		for (String word : map1.keySet()) {
			if(map2.containsKey(word)){
				 double temp=Math.pow((map1.get(word)-map2.get(word)), 2);
				 distance += temp;
			}else{
				distance += Math.pow(map1.get(word), 2);
			}
		}
		for (String word : map2.keySet()) {
			if(!map1.containsKey(word)){
				distance += Math.pow(map2.get(word), 2);
			}
		}
		distance=Math.pow(distance, 0.5);
		return distance;
	}
	private  Map<String, Double> convetTomap(DataSource ds) {
		Map<String, Double> map = new TreeMap<>();
		if(ds.getWordWeight()!=null&&!ds.getWordWeight().startsWith("{")){
			String con = ds.getWordWeight();
			String []x = con.split(",");
			for (String s : x) {
				String []word = s.split(":");
				map.put(word[0], Double.parseDouble(word[1]));
			}
		}
		return map;
	}
	/**
	   * 
	   * @param dp 当前点
	   * @param dpsA 点集
	   * @return true包含  false不包含
	   */
	   private boolean isContain(DataPoint dp,List<DataPoint> dpsA){ 
	      boolean flag=false;
	      //String name=dp.getDataPointName().trim();
	      for(DataPoint tempDp:dpsA){
	        // String tempName=tempDp.getDataPointName().trim(); //取每个点的名称
	         if(dp.equals(tempDp)){ //有一个等于当前点的名称 则返回包含
	             flag=true;
	             break;
	         }
	      }

	      return flag;
	   }
	   /**
	    * 
	    * @param dpsA 点集1
	    * @param dpsB 点集2
	    * @return 是否可以合并
	    */
	    private boolean mergeList(List<DataPoint> dpsA,List<DataPoint> dpsB){
	        boolean flag=false;

	        if(dpsA==null||dpsB==null||dpsA.size()==0||dpsB.size()==0){
	            return flag;
	        }

	        for(DataPoint dp:dpsB){
	           if(dp.isKey()&&isContain(dp,dpsA)){ //如果点集2核心点有一个包含在点集1中，则可以合并，返回true
	              flag=true;
	              break;
	           }
	        }

	        if(flag){
	            for(DataPoint dp:dpsB){
	               if(!isContain(dp,dpsA)){ //如果不包含
//	                   DataPoint tempDp=new DataPoint(dp.getWeight(),dp.getDataPointName(),dp.isKey()); // 
	                   dpsA.add(dp);
	               }
	            }
	        }
	        return flag;
	    }
	    private List<DataPoint> init() {
	    	 DataSourceNoTagService dataSourceNoTagService = (DataSourceNoTagService) ctx.getBean("dataSourceNoTagServiceImpl");
			 List<DataSource> dataList = dataSourceNoTagService.getByName("经济");
			 System.out.println("一共有 ： " + dataList.size());
			 List<DataPoint> dataPointsList = new ArrayList<>();
			 for (DataSource dataSource : dataList) {
				 if(dataSource.getContentFenci()!=null){
					 DataPoint dp = new DataPoint(convetTomap(dataSource),dataSource.getDataSourceId(),false);
					 dataPointsList.add(dp);
				 }
			 }
			return dataPointsList;
	    	
	    }
	    public void displayCluster(List<Cluster> clusterList){
	        if(clusterList!=null){
	            for(Cluster tempCluster:clusterList){
	                if(tempCluster.getDocument()!=null&&tempCluster.getDocument().size()>0){
	                    System.out.println("----------"+tempCluster.getClusterName()+"----------");
	                    for(DataPoint dp:tempCluster.getDocument()){
	                        System.out.println(dp.getDataPointName());
	                    }
	                }
	            }
	        }
	    }
		public static void main(String[] args) {
			DBScan dbs = new DBScan();
			List<DataPoint> dataPointsList = dbs.init();
//			for (double i = 0.076; i < 0.8; i+=0.001) {
				List<Cluster> clusterList = dbs.doDbscanAnalysis(dataPointsList, 0.5, 5);
//				if(list.size()!=0||list.size()!=1){
//					System.out.println("计算出来的i是： " + i);
//					break;
//				}
//			}
//			dbs.displayCluster(clusterList);
		}
}
