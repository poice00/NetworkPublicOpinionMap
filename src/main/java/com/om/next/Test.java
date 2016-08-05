package com.om.next;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Test {
	
	public static void main(String[] args) {
		 List<Cluster> clusterList=new ArrayList<Cluster>();
		 Cluster cluster1 = new Cluster();
		 cluster1.setClusterName("cluster1");
		 List<DataPoint> dp1 = new ArrayList<>(); 
		 dp1.add(new DataPoint());
		 dp1.add(new DataPoint());
		 dp1.add(new DataPoint());
		 cluster1.setDocument(dp1);

		 Cluster cluster2 = new Cluster();
		 cluster2.setClusterName("cluster2");
		 List<DataPoint> dp2 = new ArrayList<>(); 
		 dp2.add(new DataPoint());
		 dp2.add(new DataPoint());
		 dp2.add(new DataPoint());
		 dp2.add(new DataPoint());
		 dp2.add(new DataPoint());
		 cluster2.setDocument(dp2);
		 
		 Cluster cluster3 = new Cluster();
		 cluster3.setClusterName("cluster3");
		 List<DataPoint> dp3 = new ArrayList<>(); 
		 dp3.add(new DataPoint());
		 dp3.add(new DataPoint());
		 dp3.add(new DataPoint());
		 dp3.add(new DataPoint());
		 dp3.add(new DataPoint());
		 dp3.add(new DataPoint());
		 cluster3.setDocument(dp3);
		 
		 Cluster cluster4 = new Cluster();
		 cluster4.setClusterName("cluster4");
		 List<DataPoint> dp4 = new ArrayList<>(); 
		 dp4.add(new DataPoint());
		 dp4.add(new DataPoint());
		 cluster4.setDocument(dp4);
		 Cluster cluster5 = new Cluster();
		 cluster5.setClusterName("cluster5");
		 List<DataPoint> dp5 = new ArrayList<>(); 
		 dp5.add(new DataPoint());
		 dp5.add(new DataPoint());
		 dp5.add(new DataPoint());
		 cluster5.setDocument(dp5);
		 
		 clusterList.add(cluster1);
		 clusterList.add(cluster2);
		 clusterList.add(cluster3);
		 clusterList.add(cluster4);
		 clusterList.add(cluster5);
		 
		 for (Cluster cluster : clusterList) {
				System.out.println(cluster.getDocument().size()+ "\t" + cluster.getClusterName());
			}
		 System.out.println("排序后...");
		 ss(clusterList);
		 for (Cluster cluster : clusterList) {
			System.out.println(cluster.getDocument().size()+ "\t" + cluster.getClusterName());
		}
	}

	private static void ss(List<Cluster> clusterList) {
		 Collections.sort(clusterList, new Comparator<Cluster>(){
				@Override
				public int compare(Cluster o1, Cluster o2) {
					int x1 = o1.getDocument().size();
					int x2 = o2.getDocument().size();
					if(x1>x2) 
						return -1;
					else
						return 1 ;
				}
				 
			 });
		
	}
}
