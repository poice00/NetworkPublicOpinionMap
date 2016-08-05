package com.om.next;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.plaf.synth.SynthSpinnerUI;

import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mysql.jdbc.Buffer;
import com.om.domain.ClassifyType;
import com.om.domain.DataSource;
import com.om.domain.Datasource1;
import com.om.service.ClassifyTypeService;
import com.om.service.DataSourceNoTagService;
import com.om.service.DataSourceService;
import com.sun.org.apache.bcel.internal.generic.FREM;

/**
 * 计算词频
 * 分词
 * @author ssy
 *
 */
public class ContentDW {
	
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
	 * 分词：内容
	 */
	public static void content_fenci(){
//		open();
		DataSourceNoTagService dataSourceNoTagService = (DataSourceNoTagService) ctx.getBean("dataSourceNoTagServiceImpl");
		List<DataSource> dataList = dataSourceNoTagService.getByName("经济");
		for (DataSource datasource1 : dataList) {
					List<String> totalList = new ArrayList<>();
					//标题分词
					String DataTitle = datasource1.getTitle();
					List<String> titleList = DivideWords.split(DataTitle);
					//内容分词
					String DataContent = datasource1.getContent();
					List<String> contentList = DivideWords.split(DataContent);
					totalList.addAll(titleList);
					totalList.addAll(contentList);
					if(totalList.size()==0){
						datasource1.setContentFenci(null);
						//更新状态
						datasource1.setCalculateState("已分词");
						dataSourceNoTagService.update(datasource1);
					}else{
						datasource1.setContentFenci(totalList.toString());
						//更新状态
						datasource1.setCalculateState("已分词");
						dataSourceNoTagService.update(datasource1);
					}
		}
//		close();
	}
	/**
	 * 
	 * @return Map<String, Map<String,Double>>  String为文章ID，Map为文章的词对应的权重 
	 */
	public static void calFre(){
		DataSourceNoTagService dataSourceNoTagService = (DataSourceNoTagService) ctx.getBean("dataSourceNoTagServiceImpl");
		List<DataSource> dataList = dataSourceNoTagService.getByName("经济");
		Set<String> totalSet = new TreeSet<String>();//
//		//n维向量
//		System.out.println("建立向量开始...");
//		for (DataSource ds : dataList) {
//			if(ds.getContentFenci()!=null){
//				String result = ds.getContentFenci().replace("[", "").replace("]", "");
//				String words[] = result.toString().split(",");
//				for (String word:words) {
//					totalSet.add(word);
//				}
//			}
//		}
//		System.out.println("建立向量结束： "+ totalSet.size() +"维");
//		Map<String, Map<String,Double>> resultMap = new TreeMap<>();
		
		Map<String, HashSet<String>> idfMap = new TreeMap<String, HashSet<String>>();//在几篇文章出现过
		Map<String, Map<String, Integer>> totalfreqMap = new TreeMap<String, Map<String, Integer>>();//词频
		Map<String, Integer> totalMap = new TreeMap<String, Integer>();//总词数
		int size = dataList.size();//文章总篇数
		for (DataSource ds : dataList) {
			if(ds.getContentFenci()!=null){
				String result = ds.getContentFenci().replace("[", "").replace("]", "");
				String words[] = result.toString().split(",");
				totalMap.put(ds.getDataSourceId(), words.length);
				Map<String, Integer> freqMap = new TreeMap<String, Integer>();//每篇文章的词频
				for (String word:words) {
					//统计每个词在每篇文章的词频
					if(freqMap.containsKey(word)){
						int count  = freqMap.get(word);
						freqMap.put(word, ++ count);
					}else{
						freqMap.put(word, 1);
					}
	//				//统计每个词在几篇文章中出现过
					if(idfMap.containsKey(word)){
						HashSet<String> set = idfMap.get(word);
						set.add(ds.getDataSourceId());
						idfMap.put(word, set);
					}else{
						HashSet<String> set = new HashSet<>();
						set.add(ds.getDataSourceId());
						idfMap.put(word,set);
					}
				}
				totalfreqMap.put(ds.getDataSourceId(), freqMap);
			}
		}
		//计算tf-idf值
		for (DataSource ds : dataList) {
//			Map<String, Double> map = new TreeMap<>();
			StringBuilder sb = new StringBuilder();
			if(ds.getContentFenci()!=null){
				String result = ds.getContentFenci().replace("[", "").replace("]", "");
				String words[] = result.toString().split(",");
				int length=words.length;
				if(words.length!=0){
					for (String word : words) {
						double tf = totalfreqMap.get(ds.getDataSourceId()).get(word).doubleValue()/totalMap.get(ds.getDataSourceId());
						double idf = Math.log(size/(double)idfMap.get(word).size()+1.0);
						double weight=tf*idf;
						if (length>1) {
						//if(totalfreqMap.get(ds.getDataSourceId()).get(word) != null){
	//						map.put(word, weight);
							sb.append(word+":"+weight + ",");
							length=length-1;
						}else{
							sb.append(word+":" + weight);
						}
						//}
					}
					System.out.println(ds.getDataSourceId());
	//				resultMap.put(ds.getDataSourceId(), map);
					try {
						ds.setWordWeight(sb.toString());
						dataSourceNoTagService.update(ds);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					ds.setWordWeight(null);
					dataSourceNoTagService.update(ds);
				}
			}
		}
//		return resultMap;
	}
	/**
	/**
	 * 对MAP中的键值对按VAlue值进行排序 
	 * @param oldMap 排序前的map
	 * @return 排序后的map
	 */
	public static Map<String, Integer> sortMap(Map<String, Integer> oldMap) {   //对MAP中的键值对按VAlue值进行排序 
        ArrayList<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(oldMap.entrySet());  
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {  //排序
            public int compare(Entry<String, Integer> map1,  
                    Entry<String, Integer> map2) {  
                return map1.getValue().compareTo(map2.getValue());  
            }  
        });  
        //一般情况下，我们用的最多的是HashMap,在Map 中插入、删除和定位元素，HashMap 是最好的选择。
        //但如果您要按自然顺序或自定义顺序遍历键，那么TreeMap会更好。
        //如果需要输出的顺序和输入的相同,那么用LinkedHashMap 可以实现,它还可以按读取顺序来排列.
        Map<String, Integer> newMap = new LinkedHashMap<String, Integer>();  
        for (int i = list.size()-1; i >=0 ; i--) {  
            newMap.put(list.get(i).getKey(), list.get(i).getValue());  
        }  
        return newMap;  
    }
	public static void main(String[] args) {
//		content_fenci();
		calFre();
//		System.out.println(resultMap);
//		update(removeTog());
	}
	
	
}
