package com.om.util;

import java.io.FileWriter;  
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;  
import java.util.Set;  
import java.util.TreeMap;  
import java.util.Vector;

import org.junit.Test;
import org.junit.validator.PublicClassValidator;

import com.hankcs.hanlp.dependency.nnparser.util.math;
import com.hankcs.hanlp.summary.TextRankSentence;
import com.mysql.fabric.xmlrpc.base.Array;
import com.om.domain.DataSource;
import com.om.domain.Theme;
  
/**Kmeans聚类算法的实现类，将newsgroups文档集聚成10类、20类、30类 
 * 算法结束条件:当每个点最近的聚类中心点就是它所属的聚类中心点时，算法结束 
 * 
 */  
  
public class KmeansCluster {  
      
    /**Kmeans算法主过程 
     * @param Map<String, Map<String, Double>> allTestSampleMap 聚类算法测试样本map 
     * @param int K 聚类的数量 
     * @return Map<String,Integer> 聚类的结果  即<文件名，聚类完成后所属的类别标号> 
     * @throws IOException  
     */  
    private Map<String, Integer> doProcess(  
            Map<String, Map<String, Double>> allTestSampleMap, int K) {  
        // TODO Auto-generated method stub  
        //0、首先获取allTestSampleMap所有文件名顺序组成的数组  
        String[] testSampleNames = new String[allTestSampleMap.size()];  
        int count = 0, tsLength = allTestSampleMap.size();  
        Set<Map.Entry<String, Map<String, Double>>> allTestSampeleMapSet = allTestSampleMap.entrySet();  
        for(Iterator<Map.Entry<String, Map<String, Double>>> it = allTestSampeleMapSet.iterator(); it.hasNext(); ){  
            Map.Entry<String, Map<String, Double>> me = it.next();  
            testSampleNames[count++] = me.getKey();  
        }  
        //1、初始点的选择算法是随机选择或者是均匀分开选择，这里采用后者  
        Map<Integer, Map<String, Double>> meansMap = getInitPoint(allTestSampleMap, K, testSampleNames);//保存K个中心点  
        double [][] distance = new double[tsLength][K];//distance[i][j]记录点i到聚类中心j的距离  
        //2、初始化K个聚类  
        int [] assignMeans = new int[tsLength];//记录所有点属于的聚类序号，初始化全部为0  
        Map<Integer, Vector<Integer>> clusterMember = new TreeMap<Integer,Vector<Integer>>();//记录每个聚类的成员点序号  
        Vector<Integer> mem = new Vector<Integer>();  
        int iterNum = 0;//迭代次数  
        while(true){  
            System.out.println("Iteration No." + (iterNum++) + "----------------------");  
            //3、计算每个点和每个聚类中心的距离  
            for(int i = 0; i < tsLength; i++){  
                for(int j = 0; j < K; j++){  
                    distance[i][j] = getDistance(allTestSampleMap.get(testSampleNames[i]),meansMap.get(j));  
                }  
            }  
            //4、找出每个点最近的聚类中心  
            int[] nearestMeans = new int[tsLength];  
            for(int i = 0; i < tsLength; i++){  
                nearestMeans[i] = findNearestMeans(distance, i);
                System.out.println(nearestMeans[i]);
            }  
            //5、判断当前所有点属于的聚类序号是否已经全部是其离得最近的聚类，如果是或者达到最大的迭代次数，那么结束算法  
            int okCount = 0;  
            for(int i = 0; i <tsLength; i++){  
                if(nearestMeans[i] == assignMeans[i]) okCount++;  
            }  
            System.out.println("okCount = " + okCount);  
            if(okCount == tsLength || iterNum >= 10) break;  
            //6、如果前面条件不满足，那么需要重新聚类再进行一次迭代，需要修改每个聚类的成员和每个点属于的聚类信息  
            clusterMember.clear();  
            for(int i = 0; i < tsLength; i++){  
                assignMeans[i] = nearestMeans[i];   //将每个点划分到距离最近的类簇中
                if(clusterMember.containsKey(nearestMeans[i])){
                    clusterMember.get(nearestMeans[i]).add(i);    
                }  
                else {  
                    mem.clear();  //mem是文档在字符串数组中对应的序列号
                    mem.add(i);  
                    Vector<Integer> tempMem = new Vector<Integer>();  
                    tempMem.addAll(mem);  
                    clusterMember.put(nearestMeans[i], tempMem);  
                }  
            }  
            //7、重新计算每个聚类的中心点!  
            for(int i = 0; i < K; i++){  
                if(!clusterMember.containsKey(i)){//注意kmeans可能产生空聚类  
                    continue;  
                }  
                Map<String, Double> newMean = computeNewMean(clusterMember.get(i), allTestSampleMap, testSampleNames);  
                Map<String, Double> tempMean = new TreeMap<String, Double>();  
                tempMean.putAll(newMean);  
                meansMap.put(i, tempMean);  
            }  
        }  
        //8、形成聚类结果并且返回  
        Map<String, Integer> resMap = new TreeMap<String, Integer>();  
        for(int i = 0; i < tsLength; i++){  
            resMap.put(testSampleNames[i], assignMeans[i]);  
        }  
        return resMap;  
    }  
  
    /**计算当前聚类新的中心，采用向量平均 
     * @param clusterM 类序号，及类序号中的文档编号
     * @param allTestSampleMap 所有测试样例的<文件名，向量>构成的map 
     * @param testSampleNames 所有测试样例文件名构成的数组 
     * @return Map<String, Double> 新的聚类中心的向量 
     * @throws IOException  
     */  
    private Map<String, Double> computeNewMean(Vector<Integer> clusterM,  
            Map<String, Map<String, Double>> allTestSampleMap,  
            String[] testSampleNames) {  
        // TODO Auto-generated method stub  
        double memberNum = (double)clusterM.size();  
        Map<String, Double> newMeanMap = new TreeMap<String,Double>();  
        Map<String, Double> currentMemMap = new TreeMap<String,Double>();  
        for(Iterator<Integer> it = clusterM.iterator(); it.hasNext();){  //迭代每个文档
            int me = it.next();  
            currentMemMap = allTestSampleMap.get(testSampleNames[me]);   //取当前文档的向量
            Set<Map.Entry<String, Double>> currentMemMapSet = currentMemMap.entrySet();  
            for(Iterator<Map.Entry<String, Double>> jt = currentMemMapSet.iterator(); jt.hasNext();){   //迭代当前向量
                Map.Entry<String, Double> ne = jt.next();  
                if(newMeanMap.containsKey(ne.getKey())){  //如果新的包含，则值相加
                    newMeanMap.put(ne.getKey(), newMeanMap.get(ne.getKey()) + ne.getValue());  
                }   
                else {  //如果新的不包含，则值不相加，即对于第一次迭代时
                    newMeanMap.put(ne.getKey(), ne.getValue());  
                }  
            }  
        }  
          
        Set<Map.Entry<String, Double>> newMeanMapSet = newMeanMap.entrySet();  //更新新的聚类中心点，即把向量中心点平均
            for(Iterator<Map.Entry<String, Double>> jt = newMeanMapSet.iterator(); jt.hasNext();){  
                Map.Entry<String, Double> ne = jt.next();  
                newMeanMap.put(ne.getKey(), newMeanMap.get(ne.getKey()) / memberNum);     
        }  
        return newMeanMap;  
    }  
  
    /**找出距离当前点最近的聚类中心 ,基于余弦夹角计算，那就是相似度最高的点
     * @param double[][] 点到所有聚类中心的距离 
     * @return i 最近的聚类中心的序 号 
     * @throws IOException  
     */  
    private int findNearestMeans(double[][] distance,int m) {  
        // TODO Auto-generated method stub  
        /*double minDist = 10;  
        int j = 0;  
        for(int i = 0; i < distance[m].length; i++){  
            if(distance[m][i] < minDist){  
                minDist = distance[m][i];  
                j = i;  
            }  
        }  
        return j;  */
        double maxDist = 0;  
        int j = 0;  
        for(int i = 0; i < distance[m].length; i++){  
            if(distance[m][i] > maxDist){  
            	maxDist = distance[m][i];  
                j = i;  
            }  
        }  
        return j;  
    }  
  
  
    /**计算两个点的余弦相似度距离 
     * @param map1 点1的向量map 
     * @param map2 点2的向量map 
     * @return double 两个点的距离 ,
     */  
    private double getDistance(Map<String, Double> map1, Map<String, Double> map2) {  
        // TODO Auto-generated method stub  
        return computeSim(map1,map2);  
    }  
     
    /**计算两个点的欧式距离 
     * @param map1 点1的向量map 
     * @param map2 点2的向量map 
     * @return double 两个点的距离 
     */  
    private double getEuclideanDistance(Map<String, Double> map1, Map<String, Double> map2) {  
        // TODO Auto-generated method stub  
        return computeEuclideanDistance(map1,map2);  
    }  
    
    /**计算两个文本的相似度 ，使用余弦相似度的方法计算
     * @param testWordTFMap 文本1的<单词,词频>向量 ，在此使用的是单词，权重，即tf*idf
     * @param trainWordTFMap 文本2<单词,词频>向量  ，在此使用的是单词，权重，即tf*idf
     * @return Double 向量之间的相似度 以向量夹角余弦计算或者向量内积计算（效果相当而速度更快） 
     * @throws IOException  
     */  
    //由于是稀疏map，没有存零值的点，所以计算方法得有所改变
    private double computeSim(Map<String, Double> testWordTFMap,  
            Map<String, Double> trainWordTFMap) {  
        // TODO Auto-generated method stub  
        double mul = 0;//, testAbs = 0, trainAbs = 0;
        double xAbs = 0, yAbs = 0;
        Set<Map.Entry<String, Double>> testWordTFMapSet = testWordTFMap.entrySet();  
        for(Iterator<Map.Entry<String, Double>> it = testWordTFMapSet.iterator(); it.hasNext();){  
            Map.Entry<String, Double> me = it.next();  
            if(trainWordTFMap.containsKey(me.getKey())){  
                mul += me.getValue()*trainWordTFMap.get(me.getKey());  
            }  
            xAbs += me.getValue() * me.getValue();    
        }
        for (String str:trainWordTFMap.keySet()) {
        	yAbs += trainWordTFMap.get(str) * trainWordTFMap.get(str);
		}
        xAbs = Math.sqrt(xAbs);
        yAbs = Math.sqrt(yAbs);
        //testAbs = Math.sqrt(testAbs);  
          
        /*Set<Map.Entry<String, Double>> trainWordTFMapSet = trainWordTFMap.entrySet(); 
        for(Iterator<Map.Entry<String, Double>> it = trainWordTFMapSet.iterator(); it.hasNext();){ 
            Map.Entry<String, Double> me = it.next(); 
            trainAbs += me.getValue()*me.getValue(); 
        } 
        trainAbs = Math.sqrt(trainAbs);*/  
        return mul/( xAbs * yAbs);/// (testAbs * trainAbs);  
    }
    
    /**计算两个文本的相似度 ，使用欧式距离的方法计算
     * @param testWordTFMap 文本1的<单词,词频>向量 ，在此使用的是单词，权重，即tf*idf
     * @param trainWordTFMap 文本2<单词,词频>向量  ，在此使用的是单词，权重，即tf*idf
     * @return Double 向量之间的相似度 以向量夹角余弦计算或者向量内积计算（效果相当而速度更快） 
     * @throws IOException  
     */  
	private double computeEuclideanDistance(Map<String, Double> testWordTFMap,  
            Map<String, Double> trainWordTFMap) {  
        // TODO Auto-generated method stub  
        double mul = 0;//, testAbs = 0, trainAbs = 0;
        Set<Map.Entry<String, Double>> testWordTFMapSet = testWordTFMap.entrySet();  
        for(Iterator<Map.Entry<String, Double>> it = testWordTFMapSet.iterator(); it.hasNext();){  
            Map.Entry<String, Double> me = it.next();  
            if(trainWordTFMap.containsKey(me.getKey())){  
                mul += (me.getValue()-trainWordTFMap.get(me.getKey()))*(me.getValue()-trainWordTFMap.get(me.getKey()));  
            }  
        } 
        //testAbs = Math.sqrt(testAbs); 
        return Math.sqrt(mul);/// (testAbs * trainAbs);  
    }
	/**计算两个文本的相似度 ，使用向量内积的方法计算
     * @param testWordTFMap 文本1的<单词,词频>向量 
     * @param trainWordTFMap 文本2<单词,词频>向量  
     * @return Double 向量之间的相似度 以向量夹角余弦计算或者向量内积计算（效果相当而速度更快） 
     * @throws IOException  
     */  
	private double computeSimVector(Map<String, Double> testWordTFMap,  
            Map<String, Double> trainWordTFMap) {  
		// TODO Auto-generated method stub  
        double mul = 0;//, testAbs = 0, trainAbs = 0;
        Set<Map.Entry<String, Double>> testWordTFMapSet = testWordTFMap.entrySet();  
        for(Iterator<Map.Entry<String, Double>> it = testWordTFMapSet.iterator(); it.hasNext();){  
            Map.Entry<String, Double> me = it.next();  
            if(trainWordTFMap.containsKey(me.getKey())){  
                mul += me.getValue()*trainWordTFMap.get(me.getKey());  
            }
        } 
        //testAbs = Math.sqrt(testAbs);  
          
        /*Set<Map.Entry<String, Double>> trainWordTFMapSet = trainWordTFMap.entrySet(); 
        for(Iterator<Map.Entry<String, Double>> it = trainWordTFMapSet.iterator(); it.hasNext();){ 
            Map.Entry<String, Double> me = it.next(); 
            trainAbs += me.getValue()*me.getValue(); 
        } 
        trainAbs = Math.sqrt(trainAbs);*/  
        return mul;/// (testAbs * trainAbs);  
    }
    /**获取kmeans算法迭代的初始点 ，采用最远距离算法
     * @param testSampleNames 
     * @param k 聚类的数量 
     * @param Map<String, Map<String, Double>> allTestSampleMap 所有测试样例的<文件名，向量>构成的map 
     * @return Map<Integer, Map<String, Double>> 初始中心点的Map 
     * @throws IOException  
     */  
    private Map<Integer, Map<String, Double>> getInitPoint(Map<String, Map<String, Double>> mapUse, int K, String[] testSampleNames) {  
        // TODO Auto-generated method stub  
        int count = 0, i = 0;  
        Map<Integer, Map<String, Double>> meansMap = new TreeMap<Integer, Map<String, Double>>();//保存K个聚类中心点向量  
        
        /*String currentString=null;
        Map<String, Double> currentMap=new HashMap<>();
    	int current=0;
        for (int j = 0; j < K; j++) {
			if (meansMap.size()==0) {
				String str=testSampleNames[0];
		        meansMap.put(0, mapUse.get(str));
		        currentMap=mapUse.get(str);
		        mapUse.remove(str);
			} else {
				double disdance = 1;
				String string_temp=null;
				for (String str : mapUse.keySet()) {
					double disdance_temp=getDistance(currentMap, mapUse.get(str));
					if (disdance>disdance_temp) {
						string_temp = str;
						disdance=disdance_temp;
					}
				}
				meansMap.put(j, mapUse.get(string_temp));
				currentMap=mapUse.get(string_temp);
				mapUse.remove(string_temp);
			}
		}*/
        int initD1=0,initD2=0;
        double maxdisdance=1;
        for (int j = 0; j < testSampleNames.length; j++) {  //第一步，选出所有点中距离最大即相似度最小的两个
			for (int j2 = 0; j2 < testSampleNames.length; j2++) {
				if (j!=j2) {
				double disdance_temp=getDistance(mapUse.get(testSampleNames[j]), mapUse.get(testSampleNames[j2]));
					if (maxdisdance > disdance_temp) {
						maxdisdance=disdance_temp;
						initD1=j;
						initD2=j2;
					}
				} 
			}
		}
       // System.out.println(initD1+"和"+initD2+"之间的距离最大，是"+maxdisdance);
        meansMap.put(0, mapUse.get(testSampleNames[initD1]));
        meansMap.put(1, mapUse.get(testSampleNames[initD2]));
        HashSet<Integer> initSet=new HashSet<>();
        initSet.add(initD1);
        initSet.add(initD2);
        for (int j = 2; j < K; j++) {
        	double maxMinDisdance=1;
            String doc=null;
            int currentNum=0;
			for (int mm=0;mm<testSampleNames.length;mm++) { //对于每一个文档，计算与当前已有集合的最小距离
				double minDisdance=0;
				String doucId=testSampleNames[mm];
				//System.out.println("开始计算"+mm+"点与已存在点的最小距离");
				for (Integer ii:initSet) { //对于每一个已有点,计算所有点与这已有点的距离，并选出最小的，即相似度最大的
					double minDisdance_temp=0;
					if (doucId.contains(testSampleNames[ii])) {
						//System.out.println(mm+"和"+ii+"一样的？");
						//System.out.println(mapUse.get(testSampleNames[mm]));
						//System.out.println(mapUse.get(testSampleNames[ii]));
						minDisdance_temp=1;
					} else {
						minDisdance_temp=getDistance(mapUse.get(doucId), mapUse.get(testSampleNames[ii]));
					}
					if (minDisdance<minDisdance_temp) {
						minDisdance=minDisdance_temp;
					}
					//System.out.println(mm+"和"+ii+"的距离"+minDisdance_temp);
				}
				//System.out.println(mm+"和当前点的最小距离"+minDisdance);
				if (maxMinDisdance>minDisdance) { //在所有相似度最大的找最小的，即在所有距离最小的找距离最大的
					maxMinDisdance=minDisdance;
					doc=doucId;
					currentNum=mm;	
				}
				//System.out.println("结束计算最小距离");
			}
			initSet.add(currentNum);
			//System.out.println("最大最小距离"+maxMinDisdance);
			meansMap.put(j, mapUse.get(doc));
		}
        //System.out.println(meansMap.size());
        return meansMap;  
    }   
    /**计算当前聚类的中心，采用向量平均 
     * @param clusterM 类序号，及类序号中的文档编号
     * @param allTestSampleMap 所有测试样例的<文件名，向量>构成的map 
     * @param testSampleNames 所有测试样例文件名构成的数组 
     * @return Map<String, Double> 新的聚类中心的向量 
     * @throws IOException  
     */  
    private Map<Integer, Map<String, Double>> computeNewMean(Map<Integer, HashSet<String>> kmeansClusterResult,Map<String, Map<String, Double>> allTestSampleMap) {  
        // TODO Auto-generated method stub  
        
        
        Map<Integer, Map<String, Double>>  newMeanMapAll = new TreeMap<Integer, Map<String, Double>>();  //所有的聚类中心点 
        Map<String, Double> currentMemMap = new TreeMap<String,Double>(); //存储当前的数值

        for (Integer mm:kmeansClusterResult.keySet()) { //对于所有的聚类
        	Map<String, Double> newMeanMap = new TreeMap<String,Double>();  //新的聚类中心点 
        	double memberNum = (double)kmeansClusterResult.get(mm).size();
			for (String str:kmeansClusterResult.get(mm)) { //对于每一篇文档
				currentMemMap=allTestSampleMap.get(str); //取当前文档的值
				for (String cruStr:currentMemMap.keySet()) {  //对于文档里的每一个值
					if(newMeanMap.containsKey(cruStr)){  //如果新的包含，则值相加
	                    newMeanMap.put(cruStr, newMeanMap.get(cruStr) + currentMemMap.get(cruStr));
	                }   
	                else {  //如果新的不包含，则值不相加，即对于第一次迭代时
	                    newMeanMap.put(cruStr, currentMemMap.get(cruStr));  
	                }
				}  
			}
			/*Set<Map.Entry<String, Double>> newMeanMapSet = newMeanMap.entrySet();  //更新新的聚类中心点，即把向量中心点平均
            for(Iterator<Map.Entry<String, Double>> jt = newMeanMapSet.iterator(); jt.hasNext();){  
                Map.Entry<String, Double> ne = jt.next();  
                newMeanMap.put(ne.getKey(), newMeanMap.get(ne.getKey()) / memberNum);     
            }*/
            //更新新的聚类中心点，即把向量中心点平均
            for (String str:newMeanMap.keySet()) {
            	newMeanMap.put(str, newMeanMap.get(str) / memberNum);
			}
            newMeanMapAll.put(mm, newMeanMap);
		}
        return newMeanMapAll;  
    }
	private Map<Integer, HashSet<String>> kmeansResult(Map<String, Map<String, Double>> allTestSampleMap, int K) {
		System.out.println("开始进行聚类");
		//定义的返回map，map里面记录每个类的编号，类内的成员
		Map<Integer, HashSet<String>> KmeansClusterResult3 = new TreeMap<Integer,HashSet<String>>();
		//0、首先获取allTestSampleMap所有文件名顺序组成的数组  
		String[] testSampleNames = new String[allTestSampleMap.size()];
		if (K==1) {
			HashSet<String> strings=new HashSet<String>();
			for (String str:allTestSampleMap.keySet()) {
				strings.add(str);
			}
			KmeansClusterResult3.put(1, strings);
		} else {
			int count = 0, tsLength = allTestSampleMap.size();
			//把map转换成集合，用于迭代
			Set<Map.Entry<String, Map<String, Double>>> allTestSampeleMapSet = allTestSampleMap.entrySet();
			
			//迭代获取map里面的键值对，其中testSampleNames数组用于记录map里面的Key值，即每个文档的ID
			for (Iterator<Map.Entry<String, Map<String, Double>>> it = allTestSampeleMapSet.iterator(); it.hasNext();) {
				Map.Entry<String, Map<String, Double>> me = it.next();
				testSampleNames[count++] = me.getKey();
			}
			
			//1、初始点的选择算法采用最远距离算法
			Map<String, Map<String, Double>> mapUse=new TreeMap<>();
			for (String str:allTestSampleMap.keySet()) {
				mapUse.put(str, allTestSampleMap.get(str));
			}
	        Map<Integer, Map<String, Double>> meansMap = getInitPoint(mapUse, K, testSampleNames);//保存K个中心点  
	        
	        double[][] distance = new double[tsLength][K];//distance[i][j]记录点i到聚类中心j的距离  
	        //2、初始化K个聚类  
	        int[] assignMeans = new int[tsLength];//记录所有点属于的聚类序号，初始化全部为0
	        //Vector 本身就是可实现自动增长的对象数组。
	        //java.util.vector提供了向量类(vector)以实现类似动态数组的功能。
	        //在Java语言中没有指针的概念，但如果正确灵活地使用指针又确实可以大大提高程序的质量。
	        //比如在c,c++中所谓的“动态数组”一般都由指针来实现。为了弥补这个缺点，Java提供了丰富的类库来方便编程者使用，vector类便是其中之一。
	        //事实上，灵活使用数组也可以完成向量类的功能，但向量类中提供大量的方法大大方便了用户的使用。
	        Map<Integer, Vector<Integer>> clusterMember = new TreeMap<Integer,Vector<Integer>>();//记录每个聚类的成员点序号  
	        Vector<Integer> mem = new Vector<Integer>(); 
	        int iterNum = 0;//迭代次数  
	        while(true){  
	            System.out.println("Iteration No." + (iterNum++) + "----------------------");  
	            //3、计算每个点和每个聚类中心的距离  
	            for(int i = 0; i < tsLength; i++){  //每个点
	                for(int j = 0; j < K; j++){  //每个聚类中心
	                    distance[i][j] = getDistance(allTestSampleMap.get(testSampleNames[i]),meansMap.get(j));  
	                }  
	            }  
	            //4、找出每个点最近的聚类中心,即每个类别的序号  
	            int[] nearestMeans = new int[tsLength];  
	            for(int i = 0; i < tsLength; i++){  
	                nearestMeans[i] = findNearestMeans(distance, i);  
	            }  
	            //5、判断当前所有点属于的聚类序号是否已经全部是其离得最近的聚类，即当前聚类中心是否在改变，如果没有改变或者达到最大的迭代次数，那么结束算法  
	            int okCount = 0;  
	            for(int i = 0; i <tsLength; i++){  
	                if(nearestMeans[i] == assignMeans[i]) okCount++;  
	            }  
	            System.out.println("okCount = " + okCount);  
	            if(okCount == tsLength || iterNum >= 20) break;  
	            //6、如果前面条件不满足，那么需要重新聚类再进行一次迭代，需要修改每个聚类的成员和每个点属于的聚类信息 
	            //对于第一次，就是将各个成员划分到初始的聚类中心里面去
	            clusterMember.clear(); 
	            KmeansClusterResult3.clear();
	            for(int i = 0; i < tsLength; i++){  
	                assignMeans[i] = nearestMeans[i]; //将每个点都划分到计算的距离最近的点属于的聚类序号
	                if(KmeansClusterResult3.containsKey(nearestMeans[i])){  //如果当前聚类结果的Key包含该聚类序号
	                	HashSet<String> set=KmeansClusterResult3.get(nearestMeans[i]);
	                    set.add(testSampleNames[i]);
	                    KmeansClusterResult3.put(nearestMeans[i], set);
	                }  
	                else {  
	                	HashSet<String> set=new HashSet<String>();
	                	set.add(testSampleNames[i]);
	                	KmeansClusterResult3.put(nearestMeans[i], set);
	                }
	            }  
	            //7、重新计算每个聚类的中心点!  
	            for(int i = 0; i < K; i++){  
	                if(!clusterMember.containsKey(i)){//注意kmeans可能产生空聚类  
	                    continue;  
	                }  
	                Map<String, Double> newMean = computeNewMean(clusterMember.get(i), allTestSampleMap, testSampleNames);  
	                Map<String, Double> tempMean = new TreeMap<String, Double>();  
	                tempMean.putAll(newMean);  
	                meansMap.put(i, tempMean);  
	            }  
	        }  
	        //8、形成聚类结果并且返回  
	        Map<String, Integer> resMap = new TreeMap<String, Integer>();  
	        for(int i = 0; i < tsLength; i++){  
	            resMap.put(testSampleNames[i], assignMeans[i]);  
	        }  
		}
		
        return KmeansClusterResult3; 
	}  
	@Test
	public void KmeansClusterMain() throws Exception {  
		
		
        //首先计算文档TF-IDF向量，保存为Map<String,Map<String,Double>> 即为Map<文件名，Map<特征词，TF-IDF值>>  
    	WordSegment wordSegment=new WordSegment();
        ArrayList listMap=new ArrayList<>();
        listMap=wordSegment.computeTFMultiIDF(5);
        Map<String,Map<String,Double>> allTestSampleMap = (Map<String, Map<String, Double>>) listMap.get(5);
       
        Map<String, Map<String, Integer>> titleMap=(Map<String, Map<String, Integer>>) listMap.get(3);
        Map<String, String> titleNameMap= (Map<String, String>) listMap.get(4);
        Map<String,Map<String,Double>> allTestSampleMap2 = (Map<String, Map<String, Double>>) listMap.get(2);
        //Map<String, Map<String, Integer>> otherDocMap= (Map<String, Map<String, Integer>>) listMap.get(5);
        //System.out.println(allTestSampleMap);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date startDate = new java.util.Date();
		String beginTime = sdf.format(startDate);
		System.out.println("程序开始执行时间:" + beginTime);
		System.out.println("     " + allTestSampleMap.size());
		double totalSim = 0;
        int clusterNum=0;
        Map<Integer, HashSet<String>> KmeansClusterResult2 = new TreeMap<Integer, HashSet<String>>();
		/*
		 * int[] num={100,110,120,140,160,170,180,190}; for(int i = 0; i <
		 * num.length; i++){ System.out.println("开始聚类，聚成" + num[i] + "类");
		 * //String KmeansClusterResultFile =
		 * "F:/DataMiningSample/KmeansClusterResult/"; //KmeansClusterResult2 =
		 * kmeansResult(allTestSampleMap, K[i]); KmeansClusterResult2 =
		 * kmeansResult(allTestSampleMap, num[i]);
		 * System.out.println(KmeansClusterResult2.size());
		 * System.out.println("开始进行聚类结果评估"); double
		 * currentSim=wordSegment.evaluateClusterResult(KmeansClusterResult2,
		 * allTestSampleMap); System.out.println("聚类结果评估结束,结果是  "+currentSim);
		 * if (currentSim > totalSim) { totalSim = currentSim; clusterNum =
		 * num[i]; } Map<String, Integer> mapForEx=new TreeMap<>(); for (Integer
		 * ii:KmeansClusterResult2.keySet()) { mapForEx.put(String.valueOf(ii),
		 * KmeansClusterResult2.get(ii).size()); } Map<String, Integer>
		 * allCluster=wordSegment.sortMap(mapForEx); Map<String,
		 * HashSet<String>> topTenCluster=new LinkedHashMap<>(); //每个蔟，蔟集合 for
		 * (String str:allCluster.keySet()) { int ii = Integer.parseInt(str);
		 * HashSet<String> idSet = KmeansClusterResult2.get(ii);
		 * System.out.println(idSet.size()); topTenCluster.put(str, idSet); }
		 * Map<Integer, Map<String, Double>> MeanMapAll =
		 * computeNewMean(KmeansClusterResult2, allTestSampleMap);
		 * 
		 * HashSet<String> titlesSet=new HashSet<>(); for (String
		 * str:topTenCluster.keySet()) {//对于每个类簇 HashSet<String>
		 * idSet=topTenCluster.get(str); int clusterId=Integer.parseInt(str);
		 * Map<String, Double> meanMapCurrent=MeanMapAll.get(clusterId); double
		 * last_weight=0; String titleName=null; for (String id:idSet) {
		 * //对于每篇文档，计算每个标题的权重 double title_weight=0; Map<String, Integer>
		 * map_temp=titleMap.get(id); for (String word:map_temp.keySet()) { if
		 * (meanMapCurrent.containsKey(word)) {
		 * title_weight=title_weight+meanMapCurrent.get(word); } } if
		 * (last_weight<title_weight) { last_weight=title_weight;
		 * titleName=titleNameMap.get(id); } }
		 * System.out.println("类簇"+str+"的最佳标题是"+titleName);
		 * titlesSet.add(titleName); } }
		 */
        KmeansClusterResult2.clear();
        KmeansClusterResult2=kmeansResult(allTestSampleMap, 110);
        Map<String, Integer> mapForEx=new TreeMap<>();
        for (Integer ii:KmeansClusterResult2.keySet()) {
        	mapForEx.put(String.valueOf(ii), KmeansClusterResult2.get(ii).size());
		}
        Map<String, Integer> allCluster=wordSegment.sortMap(mapForEx);
        Map<Integer, HashSet<String>> topTenCluster=new LinkedHashMap<>(); //每个蔟，蔟集合
        //选择帖子最多的10个作为热门子专题
        int lastNum=0;
        for (String str:allCluster.keySet()) {
        	if (lastNum<10) {
        		int ii = Integer.parseInt(str);
    			HashSet<String> idSet = KmeansClusterResult2.get(ii);
    			System.out.println(idSet.size());
    			topTenCluster.put(ii, idSet);
    			lastNum=lastNum+1;
			}
		}
        
		DataSearch dataSearch = new DataSearch();
		Map<Integer, Map<String, Double>> MeanMapAll = computeNewMean(KmeansClusterResult2, allTestSampleMap);
        //生成每个子专题的标题
        HashSet<String> titlesSet=new HashSet<>();
        String[] themes=new String[10];
        int[] totalTopics=new int[10];
        int[] totalVisits=new int[10];
        int[] totalReplys=new int[10];
        int[] totalPartINs=new int[10];
        Double[] themesHot=new Double[10];
        int currentNum=0;
        for (Integer clusterId:topTenCluster.keySet()) {//对于每个类簇
        	HashSet<String> idSet=topTenCluster.get(clusterId);
        	Map<String, Double> meanMapCurrent=MeanMapAll.get(clusterId);
        	double last_weight=0;
        	String titleName=null;
        	List<DataSource> dataSources=dataSearch.selectSourceDataByIds(idSet);
        	int totalVisit=0;
        	int totalReply=0;
        	int totalPartIn=0;
        	for (int i = 0; i < dataSources.size(); i++) {
				totalVisit=totalVisit+dataSources.get(i).getVisitNum();
				totalReply=totalReply+dataSources.get(i).getReplyNum();
			}
        	totalPartIn=dataSearch.searchPartNum(idSet);
        	System.out.println("该专题的帖子数为"+dataSources.size()+"总访问量"+totalVisit+"总回复量"+totalReply+"总参与人员"+totalPartIn);
        	totalTopics[currentNum]=dataSources.size();
        	totalVisits[currentNum]=totalVisit;
        	totalReplys[currentNum]=totalReply;
        	totalPartINs[currentNum]=totalPartIn;
        	for (String id:idSet) { //对于每篇文档，计算每个标题的权重
        		double title_weight=0;
        		Map<String, Integer> map_temp=titleMap.get(id);
        		for (String word:map_temp.keySet()) {
					if (meanMapCurrent.containsKey(word)) {
						title_weight=title_weight+meanMapCurrent.get(word);
					}
				}
        		if (last_weight<title_weight) {
					last_weight=title_weight;
					titleName=titleNameMap.get(id);
				}
			}
        	System.out.println("类簇"+clusterId+"的最佳标题是"+titleName);
        	titlesSet.add(titleName);
        	themes[currentNum]=titleName;
        	currentNum=currentNum+1;
		}
        //将其他类簇分配到相应的类簇中
       
        //计算每个类的最大半径
        HashSet<String>  allClusterId=new HashSet<>(); //所有Top10类簇中文档Id的集合
        Map<Integer, Double> maxDisdance = new HashMap<>();
        for (Integer ii:topTenCluster.keySet()) { //对于每一个类簇
        	double maxdisdance=1;
        	for (String douId:topTenCluster.get(ii)) {
        		double disdance_temp=getDistance(allTestSampleMap.get(douId), MeanMapAll.get(ii));
        		if (maxdisdance>disdance_temp) {
					maxdisdance=disdance_temp;
				}
        		allClusterId.add(douId);
			}
        	maxDisdance.put(ii, maxdisdance);
		}
        //System.out.println(maxDisdance);
        HashSet<String> otherDocSet=new HashSet<>();
        //将剩下的文档加入到十个热门子专题中
        for (String str:allTestSampleMap2.keySet()) { //对于余下的每一篇文档，计算与每一个类簇中心的相似度
        	if (allTestSampleMap.containsKey(str)==false) { //如果包括就不计算，如果不包括就计算
				double minDisdance=0;
				int clusterId=0;
				for (Integer ii:topTenCluster.keySet()) {  //计算该点到每个类簇中心的距离，选择相似度最大的
					double disdance_temp=getDistance(allTestSampleMap2.get(str), MeanMapAll.get(ii));
					if (minDisdance<=disdance_temp) {
						minDisdance=disdance_temp;
						clusterId=ii;
					}
				}
				if (minDisdance>maxDisdance.get(clusterId)) { //如果这个相似度最大的点大于这个蔟的半径
					HashSet<String> strIdSet=topTenCluster.get(clusterId);
					strIdSet.add(str);
					topTenCluster.put(clusterId, strIdSet);
				}else {
					otherDocSet.add(str);
				}
			}
		}
        dataSearch.titleInsert(themes,totalTopics,totalVisits,totalReplys,totalPartINs,topTenCluster);
        java.util.Date endDate=new java.util.Date();
		String endTime = sdf.format(endDate);    
        System.out.println("程序结束执行时间:"+endTime); 
        int day=(int) ((endDate.getTime()-startDate.getTime())/1000);
        System.out.println("用时"+day+"秒");
    }
	
	@Test
	public void themeProcess() throws Exception{
		DataSearch dataSearch=new DataSearch();
		List<Theme> themes=dataSearch.selectTheme();
		double maxN4=0,maxAN=0;
		double g1=0.4,g2=0.2,g3=0.4,g4=0.4,g5=0.6;
		int totalVisit=0,totalReply=0,totalPartIn=0,totalTopicNum=0;
		for (int i = 0; i < themes.size(); i++) {  //第一次迭代，得到两个最大的值
			totalVisit=themes.get(i).getTotalVisit();
			totalReply=themes.get(i).getTotalReply();
			totalPartIn=themes.get(i).getTotalPart();
			totalTopicNum=themes.get(i).getTotalTopicNum();
			double AN=g1*totalTopicNum+g2*totalVisit+g3*totalReply;
			if (maxAN<AN) {
				maxAN=AN;
			}
			if (maxN4<totalPartIn) {
				maxN4=totalPartIn;
			}
		}
		Map<String, Double> chMap=new HashMap<>();
		for (int i = 0; i < themes.size(); i++) {  //第二次迭代，计算每个theme的热度
			String id=themes.get(i).getThemeId();
			totalVisit=themes.get(i).getTotalVisit();
			totalReply=themes.get(i).getTotalReply();
			totalPartIn=themes.get(i).getTotalPart();
			totalTopicNum=themes.get(i).getTotalTopicNum();
			double AN=g1*totalTopicNum+g2*totalVisit+g3*totalReply;
			double CH=g4*totalPartIn/maxN4+g5*AN/maxAN;
			chMap.put(id, CH);
			dataSearch.updatethemeHot(id, CH);
		}
	}
	@Test
	public void dataSourceProcess() throws Exception{
		DataSearch dataSearch=new DataSearch();
		List<Theme> themes=dataSearch.selectTheme();
		System.out.println();
		HashSet<String> gupiaoSet=new HashSet<>();
		HashSet<String> jingjiSet=new HashSet<>();
		HashSet<String> fangchanSet=new HashSet<>();
		for (int i = 0; i < themes.size(); i++) {  //第一次迭代，得到两个最大的值
			String theme_id=themes.get(i).getThemeId();
			String idset=themes.get(i).getTopicId();
			String[] idStrings=idset.split(",");
			if (theme_id.contains("类簇102主题")||theme_id.contains("类簇47主题")) {
				for (int j = 0; j < idStrings.length; j++) {
					gupiaoSet.add(idStrings[j]);
				}
			}
			if (theme_id.contains("类簇29主题")||theme_id.contains("类簇77主题")) {
				for (int j = 0; j < idStrings.length; j++) {
					jingjiSet.add(idStrings[j]);
				}
			}
			if (theme_id.contains("类簇18主题")||theme_id.contains("类簇5主题")||theme_id.contains("类簇72主题")||theme_id.contains("类簇78主题")) {
				for (int j = 0; j < idStrings.length; j++) {
					fangchanSet.add(idStrings[j]);
				}
			}
		}
		System.out.println(gupiaoSet.size()+"   "+jingjiSet.size()+"   "+fangchanSet.size());
		List<DataSource> dataSources=dataSearch.selectSourceData();
		Map<String, Double> hotTopic=new TreeMap<>();
		for (int i = 0; i < dataSources.size(); i++) {
			String id=dataSources.get(i).getDataSourceId();
			if (gupiaoSet.contains(id)) {
				//dataSearch.upDatasource(id, "股票");
			} else if (jingjiSet.contains(id)) {
				//dataSearch.upDatasource(id, "发展");
			}else if (fangchanSet.contains(id)) {
				//dataSearch.upDatasource(id, "房地产");
				int visitNum=dataSources.get(i).getVisitNum();
				int replyNum=dataSources.get(i).getReplyNum();
				double g1=0.4,g2=0.6;
				double hotNum=g1*visitNum+g2*replyNum;
				hotTopic.put(id, hotNum);
				dataSearch.updateDataHotnum(id, hotNum);
			}else {
				//dataSearch.upDatasource(id, "其他");
			}
		}
		
		WordSegment wordSegment=new WordSegment();
		Map<String, Double> topNhotTopic=wordSegment.sortDoubleMap(hotTopic);
		System.out.println(topNhotTopic.size());
		int mm=0;
		for (String str:topNhotTopic.keySet()) {
			if (mm<5) {
				DataSource dataSource=dataSearch.oneDatasource(str);
				System.out.println(dataSource.getVisitNum()+"   "+dataSource.getReplyNum());
			}
			mm=mm+1;
		}
	}
	@Test
	public void fangchanHOtTopic() throws Exception{
		/*DataSearch dataSearch=new DataSearch();
		List<DataSource> dataSources=dataSearch.SelectDataHotnum();
		for (DataSource dataSource:dataSources) {
			System.out.println(dataSource.getTitle()+"***"+dataSource.getHotNum());
		}*/
		DataSearch dataSearch=new DataSearch();
		List<DataSource> dataSources=dataSearch.SelectFangdichan();
		System.out.println(dataSources.size());
		String text="";
		for (int i = 0; i < dataSources.size(); i++) {
			text=text+dataSources.get(i).getContent();
		}
		List<String> list=TextRankSentence.getTopSentenceList(text, 500);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	
	@Test
	public void fangdichanSummary() throws Exception{
		DataSearch dataSearch=new DataSearch();
		List<DataSource> dataSources=dataSearch.SelectFangdichan();
		System.out.println(dataSources.size());
		String text="";
		for (int i = 0; i < dataSources.size(); i++) {
			text=text+dataSources.get(i).getContent();
		}
		List<String> list=TextRankSentence.getTopSentenceList(text, 500);
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
}
