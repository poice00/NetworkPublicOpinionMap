package com.om.util;

import java.awt.image.TileObserver;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import org.junit.Test;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.collection.dartsclone.details.DoubleArrayBuilder;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;
import com.om.domain.DataSource;

public class WordSegment {

	public static void main(String[] args) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date startDate=new java.util.Date();
		String beginTime = sdf.format(startDate);
		System.out.println("程序开始执行时间:" + beginTime);
		DataSearch dataSearch=new DataSearch();
		List<DataSource> dataSources=dataSearch.selectSourceData();
		int total=0;
		int total2=0;
		HanLP.Config.ShowTermNature=false;
		for (int i = 0; i < dataSources.size(); i++) {
			String str=dataSources.get(i).getContent();
			//List<Term> words=(List<Term>) SpeedTokenizer.segment(str);
			List<Term> words=HanLP.segment(str);
			total=total+words.size();
			List<Term> words2=(List<Term>) NShortSegment.parse(str);
			total2=total2+words2.size();
		}
		java.util.Date endDate=new java.util.Date();
		String endTime = sdf.format(endDate);    
        System.out.println("程序结束执行时间:"+endTime); 
        int day=(int) ((endDate.getTime()-startDate.getTime())/1000);
        System.out.println("共对"+dataSources.size()+"个帖子的内容进行了分词，用时  "+day+"  秒钟,共分词 "+total+"  个");  
        System.out.println("共对"+dataSources.size()+"个帖子的内容进行了分词，用时  "+day+"  秒钟,共分词 "+total2+"  个"); 
	}
	@Test
	public void array() throws Exception{
		float[][] aa=new float[12000][38000];
		
		System.out.println("OK");
	}
	
	/*生成文档向量模型*/
	public ArrayList computeTFMultiIDF(int n) throws Exception{
		BufferedReader StopWordFileBr = new BufferedReader(new InputStreamReader(new FileInputStream(
				new File("E:\\workspace\\NetworkPublicOpinionMap\\src\\main\\webapp\\data\\dictionary\\stopwords.txt"))));
		HashSet<String> stopWordSet = new HashSet<String>();
		String stopWord = null;
		for (; (stopWord = StopWordFileBr.readLine()) != null;) {
			stopWordSet.add(stopWord);
		}
		System.out.println(stopWordSet.size());
		DataSearch dataSearch=new DataSearch();
		List<DataSource> dataSources=dataSearch.selectSourceData();
		String textToSeg="";		
		//统计每个词出现的次数 
		Map<String, Integer> map = new TreeMap<String, Integer>(); 		
		//统计每个文档的总词语数
		Map<String, Integer> map2 = new TreeMap<String, Integer>(); 	
		//定义嵌套map函数，
		//String为文档ID，其中的MAP为文档的词及词频数目
		Map<String, Map<String, Integer>> map4=new TreeMap<String, Map<String,Integer>>();
		//String为文档ID，其中的MAP为文档的词及词频数目
		Map<String, Map<String, Integer>> mapOther=new TreeMap<String, Map<String,Integer>>(); 
		//倒排序文档集，统计每个词出现的文档集合
		Map<String, HashSet<String>> map5=new TreeMap<String, HashSet<String>>(); 
		//文档标题对应的Map
		Map<String,Map<String, Integer>> mapTitle=new HashMap<>();
		//文档标题名称对应的Map
		Map<String,String> mapTitleName=new HashMap<>();
		HashSet<String> highQuDocSet=new HashSet<>();
		System.out.println(dataSources.size());
		for (int i = 0; i < dataSources.size(); i++) {
			//textToSeg=	dataSources.get(i).getContent().replaceAll("[^\u4E00-\u9FA5]", "");
			textToSeg=	dataSources.get(i).getContent();
			//textToSeg=textToSeg.replaceAll("[^a-zA-Z_\u4e00-\u9fa5]", "");
			textToSeg=textToSeg.replaceAll("[^\u4E00-\u9FA5]", "");
			textToSeg=textToSeg.replaceAll(" ", "");
			String title=dataSources.get(i).getTitle();
			title=title.replaceAll("[^\u4E00-\u9FA5]", "");
			title=title.replaceAll(" ", "");
			HanLP.Config.ShowTermNature = false;
			SpeedTokenizer.SEGMENT.enableCustomDictionary(true);
			List<Term> initwords=(List<Term>) SpeedTokenizer.segment(textToSeg);
			List<Term> titlewords=(List<Term>) SpeedTokenizer.segment(title);
			//List<Term> words = HanLP.segment(textToSeg);
			//List<Term> words = NShortSegment.parse(textToSeg);
			//List<Term> words = NLPTokenizer.segment(textToSeg);
			List<String> words=new LinkedList<>();
			for (int j = 0; j < initwords.size(); j++) {
				String str=initwords.get(j).toString();
				if (stopWordSet.contains(str)||str.length()<2) {  //去掉停用词和单个词
				}else{
					words.add(str);
				}
			}
			Map<String, Integer> map3 = new TreeMap<String, Integer>(); // 每个文档自身的词及词频
			Map<String, Integer> Titlemap_temp = new TreeMap<String, Integer>(); // 每个标题自身的词及词频
			int topicTotalWords=words.size();
			int titleWordSize=titlewords.size();
			if (words.size() > n) { //去掉停用词和单词后，再进行后续计算
				for (int j = 0; j < titleWordSize; j++) {
					if (Titlemap_temp.containsKey(titlewords.get(j).toString())) {
						int tempCount = Titlemap_temp.get(titlewords.get(j).toString());
						Titlemap_temp.put(titlewords.get(j).toString(), ++tempCount);
					} else {
						Titlemap_temp.put(titlewords.get(j).toString(), 1);
					}
				}
				mapTitle.put(dataSources.get(i).getDataSourceId(), Titlemap_temp);
				mapTitleName.put(dataSources.get(i).getDataSourceId(), dataSources.get(i).getTitle());
				for (int j = 0; j < topicTotalWords; j++) {
					if (map3.containsKey(words.get(j).toString())) {
						int tempCount = map3.get(words.get(j).toString());
						map3.put(words.get(j).toString(), ++tempCount);
					} else {
						map3.put(words.get(j).toString(), 1);
					}
					if (map.containsKey(words.get(j).toString())) {
						int tempCount = map.get(words.get(j).toString());
						map.put(words.get(j).toString(), ++tempCount);
					} else {
						map.put(words.get(j).toString(), 1);
					}
					if (map5.containsKey(words.get(j).toString())) {
						HashSet<String> docmentset = map5.get(words.get(j).toString());
						docmentset.add(dataSources.get(i).getDataSourceId());
						map5.put(words.get(j).toString(), docmentset);
					} else {
						HashSet<String> docmentset = new HashSet<String>();
						docmentset.add(dataSources.get(i).getDataSourceId());
						map5.put(words.get(j).toString(), docmentset);
					}
				}
				map2.put(dataSources.get(i).getDataSourceId(), topicTotalWords);
				map4.put(dataSources.get(i).getDataSourceId(), map3);
			}
			if (words.size()>300) {
				highQuDocSet.add(dataSources.get(i).getDataSourceId());
			}
		}
		System.out.println(highQuDocSet.size());
		//String为文档ID，其中的MAP为文档的词及词频数目,排序后
		Map<String, Map<String, Integer>> map7 = new TreeMap<String, Map<String, Integer>>();
		Map<String, Integer> sampleMap=sortMap(map2);
		for (String str:sampleMap.keySet()) {
			Map<String, Integer> map_temp=map4.get(str);
			map7.put(str, map_temp);
		}
		//定义嵌套map函数，
		//String为文档ID，其中的MAP为所有的词及tf*idf权重
		Map<String, Map<String, Double>> map6=new TreeMap<String, Map<String, Double>>(); 
		Map<String, Map<String, Double>> highQuTfIdfmap=new TreeMap<String, Map<String, Double>>(); 
		float total=map7.size();
		int num=1;
		for (String documentId:map7.keySet()) {
			Map<String, Double> map3 = new TreeMap<String, Double>(); //每个文档的tf及idf计算
			for (String wordId:map5.keySet()) {
				if (map7.get(documentId).containsKey(wordId)) {
					float tf=map7.get(documentId).get(wordId).floatValue()/map2.get(documentId).floatValue();
					float idf=(float) Math.log(total/((float)map5.get(wordId).size()+1.0));
					float weight=tf*idf;
					map3.put(wordId, (double) weight);
				}
			}
			if (highQuDocSet.contains(documentId)) {
				highQuTfIdfmap.put(documentId, map3);
			}
			map6.put(documentId, map3);
			num=num+1;
		}
		ArrayList listMap=new ArrayList<>();
		System.out.println(mapOther.size());
		System.out.println(map4.size());
		listMap.add(map7);
		listMap.add(map5);
		listMap.add(map6);
		listMap.add(mapTitle);
		listMap.add(mapTitleName);
		listMap.add(highQuTfIdfmap);
		return listMap;
	}
	@Test
	public void ShowSegmentResult() throws Exception{
		computeTFMultiIDF(300);
	}
	//新的评估函数，适应于用余弦相似度进行计算的情况
	public double evaluateClusterResult(Map<Integer, HashSet<String>> kmeansClusterResult2,
			Map<String, Map<String, Double>> allTestSampleMap) throws Exception {
		// TODO Auto-generated method stub
		Map<Integer, Map<String, Double>> MeanMapAll = computeNewMean(kmeansClusterResult2, allTestSampleMap);
		double totalSim = 0;
		for (Integer mm : MeanMapAll.keySet()) {// 取每一个类
			Map<String, Double> currentMeanMap = MeanMapAll.get(mm); // 当前的类中心
			for (String docstr : kmeansClusterResult2.get(mm)) {
				double nn = getDistance(allTestSampleMap.get(docstr),currentMeanMap);
				totalSim += nn;
			}
		}
		return totalSim;
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
    /**计算两个文本的相似度 ，使用余弦相似度的方法计算
     * @param testWordTFMap 文本1的<单词,词频>向量 ，在此使用的是单词，权重，即tf*idf
     * @param trainWordTFMap 文本2<单词,词频>向量  ，在此使用的是单词，权重，即tf*idf
     * @return Double 向量之间的相似度 以向量夹角余弦计算或者向量内积计算（效果相当而速度更快） 
     * @throws IOException  
     */  
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
        double result=mul/( xAbs * yAbs);
        return result;/// (testAbs * trainAbs);  
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
				currentMemMap=allTestSampleMap.get(str); 
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
    
	//IntegerMap排序算法
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Integer> sortMap(Map<String, Integer> oldMap) { // 对MAP中的键值对按VAlue值进行排序
		ArrayList<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(oldMap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

			@Override
			public int compare(Entry<java.lang.String, Integer> arg0, Entry<java.lang.String, Integer> arg1) {
				return arg0.getValue().compareTo(arg1.getValue());
			}
		});
		Map newMap = new LinkedHashMap();
		for (int i = list.size() - 1; i >= 0; i--) {
			newMap.put(list.get(i).getKey(), list.get(i).getValue());
		}
		return newMap;
	}
	//DoubleMap排序算法
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Double> sortDoubleMap(Map<String, Double> oldMap) { // 对MAP中的键值对按VAlue值进行排序
		ArrayList<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(oldMap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {

			@Override
			public int compare(Entry<java.lang.String, Double> arg0, Entry<java.lang.String, Double> arg1) {
				return arg0.getValue().compareTo(arg1.getValue());
			}
		});
		Map newMap = new LinkedHashMap();
		for (int i = list.size() - 1; i >= 0; i--) {
			newMap.put(list.get(i).getKey(), list.get(i).getValue());
		}
		return newMap;
	}
}
