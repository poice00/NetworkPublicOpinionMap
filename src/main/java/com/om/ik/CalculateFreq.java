package com.om.ik;

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

import javax.swing.plaf.synth.SynthSpinnerUI;

import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mysql.jdbc.Buffer;
import com.om.domain.ClassifyType;
import com.om.domain.Datasource1;
import com.om.service.ClassifyTypeService;
import com.om.service.DataSourceService;
import com.sun.org.apache.bcel.internal.generic.FREM;

/**
 * 计算词频
 * 分词
 * @author ssy
 *
 */
public class CalculateFreq {
	
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
	 * 分词：标题和内容
	 */
	public static void content_fenci(){
		open();
		DataSourceService dataSourceService = (DataSourceService) ctx.getBean("dataSourceServiceImpl");
		List<Datasource1> dataList = dataSourceService.findAll();
		for (Datasource1 datasource1 : dataList) {
			//标题分词
			String DataTitle = datasource1.getDataTitle();
			List<String> titleList = DivideWords.split(DataTitle);
			//内容分词
			String DataContent = datasource1.getDataContent();
			List<String> contentList = DivideWords.split(DataContent);
			//结合
			List<String> content_fenci = new ArrayList<String>();
			content_fenci.addAll(titleList);
			content_fenci.addAll(contentList);
			
			//标题分词结果存入数据库
			System.out.println("标题 ： " + DataTitle);
			System.out.println("分词结果 ： " +titleList.toString());
			System.out.println("==============================");
			datasource1.setTitleFenciResult(titleList.toString());
			//内容分词结果存入数据库
			System.out.println("内容 ： " + DataContent);
			System.out.println("分词结果 ： " +contentList.toString());
			System.out.println("==============================");
			datasource1.setContentFenciResult(contentList.toString());
			//总和结果存入数据库
			datasource1.setContentFenci(content_fenci.toString());
			//更新状态
			datasource1.setIsFenci("已分词");
			dataSourceService.update(datasource1);
		}
		close();
	}
	/**
	 * 统计每个分类的词频
	 * 政治 环保  经济 军事 社会
	 */
	public static void calFre(){
		open();
		String zz = "政治";
		String hb = "环保";
		String jj = "经济";
		String js = "军事";
		String sh = "社会";
		List<String> typeList = new ArrayList<String>();
		typeList.add(zz);
		typeList.add(hb);
		typeList.add(jj);
		typeList.add(js);
		typeList.add(sh);
		
		ClassifyTypeService classifyTypeService = (ClassifyTypeService) ctx.getBean("classifyTypeServiceImpl");
		DataSourceService dataSourceService = (DataSourceService) ctx.getBean("dataSourceServiceImpl");
		Set<String> totalSet = new HashSet<String>();//
		for (String type : typeList) {
			List<Datasource1> dataList = dataSourceService.getByName(type);
			ClassifyType classifyType = classifyTypeService.getByName(type);
			StringBuilder sb = new StringBuilder();
			for (Datasource1 ds : dataList) {
				String result = ds.getContentFenci().replace("[", "").replace("]", "");
				sb.append(result);
			}
			String words[] = sb.toString().split(",");
//			System.out.println("总词数： " + words.length);
			Map<String, Integer> freqMap = new TreeMap<String, Integer>();//词频
			//统计每篇文章的词频
			for (String word : words) {
				int count= 1;
				if(freqMap.containsKey(word)){
					count  = freqMap.get(word);
					freqMap.put(word, ++ count);
				}else{
					freqMap.put(word, 1);
				}
			}
			System.out.println("----------------"+ type +"--------------");
			System.out.println("总词数： " + words.length);
			System.out.println("去除重复： " + freqMap.size());
			//建立数组 词频 取前2000个
			String result = getNeddWord(freqMap);
			String a[] = result.split(",");
			int n = 0 ;
			for (String s : a) {
				String x = s.split(":")[1];
				int num = Integer.parseInt(x);
				n += num;
				//统计总的不重复单词
				totalSet.add(s.split(":")[0]);
			}
			System.out.println("前2000总词数： " + n);
			classifyType.setSumWord((double) 2000);
			classifyType.setClassifyContent(result);
			//System.out.println(getNeddWord(freqMap));
			classifyType.setSumText((double) n);
			classifyTypeService.update(classifyType);
		}
		List<ClassifyType> classifyTypeList = classifyTypeService.findAll();
		for (ClassifyType ct : classifyTypeList) {
			ct.setSumWordFre((double) totalSet.size());
			classifyTypeService.update(ct);
		}
		System.out.println("前2000总的不重复单词： " + totalSet.size());
		close();
	}
	/**
	 * 
	 * @param freqMap 待排序的map集合
	 * @return 词频前2000的map集合
	 */
	private static String getNeddWord(Map<String, Integer> freqMap) {
		Map<String, Integer> sortResult = sortMap(freqMap);
		ArrayList<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(sortResult.entrySet());  
		ArrayList<Object> presultList = new ArrayList<Object>();  
		for (int i=0;i<2000;i++) {
			presultList.add(list.get(i));
		}
		String result = presultList.toString()
						.replace("[", "")
						.replace("]", "")
						.replace(" ", "")
						.replace("=", ":");
		System.out.println("--" + result);
		return result;
	}
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
	private static Map<String, Map<String,Integer>> removeTog() {
		ClassifyTypeService classifyTypeService = (ClassifyTypeService) ctx.getBean("classifyTypeServiceImpl");
		Map<String, Map<String,Integer>> maps = new LinkedHashMap<String, Map<String,Integer>>();
		List<ClassifyType> classifyTypeList = classifyTypeService.findAll();
		for (ClassifyType classifyType : classifyTypeList) {
			Map<String,Integer> m = new LinkedHashMap<String, Integer>();
			String ss[] = classifyType.getClassifyContent().split(",");
			for (String s : ss) {
				String d = s.split(":")[1];
				m.put(s.split(":")[0],Integer.parseInt(d));
			}
			maps.put(classifyType.getClassifyName(), m);
		}
		for (String x : maps.keySet()) {
			System.out.println(x+" : " +maps.get(x));
			System.out.println();
		}
		
		Set<String> stopWordSet = getStopWordSet();  
		System.out.println(stopWordSet);
		for (String x : maps.keySet()) {
			Set<String> set = maps.get(x).keySet();
			Iterator<String> it = set.iterator();
			while(it.hasNext()){
			     String m = it.next();
		    	 if(stopWordSet.contains(m)){
		    		 it.remove();
		    	 }
			}
		}
		return maps;
	}
	
	private static Set<String> getStopWordSet() {
		String path = "src/main/resources/stop.dic";
		//用来存放停用词的集合  
        Set<String> stopWordSet = new HashSet<String>();  
		//初如化停用词集  
        String stopWord = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(path)));
			while((stopWord=br.readLine())!=null){
				stopWordSet.add(stopWord);  
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stopWordSet;
	}
	private static void update(Map<String, Map<String, Integer>> maps) {
		ClassifyTypeService classifyTypeService = (ClassifyTypeService) ctx.getBean("classifyTypeServiceImpl");
		Set<String> totalSet = new HashSet<String>();//
		for (String x : maps.keySet()) {
			ClassifyType ct = classifyTypeService.getByName(x);
			
			for (String s : maps.get(x).keySet()) {
				String pre = maps.get(x).toString();
				//System.out.println("pre: "+ pre);
				String result = pre.replace("{", "")
									.replace("}", "")
									.replace(" ", "");
				//System.out.println("result : "+result);
				String a[] = result.split(",");
				int n = 0 ;
				for (String s1 : a) {
					String x1 = s1.split("=")[1];
					int num = Integer.parseInt(x1);
					n += num;
					//统计总的不重复单词
					totalSet.add(s.split("=")[0]);
				}
				ct.setClassifyContent(result.replace("=", ":"));
				ct.setSumWord((double) maps.size());
				ct.setSumText((double) n);
			}
			classifyTypeService.update(ct);
		}
		System.out.println("1.over....");
		List<ClassifyType> classifyTypeList = classifyTypeService.findAll();
		for (ClassifyType ct : classifyTypeList) {
			ct.setSumWordFre((double) totalSet.size());
			classifyTypeService.update(ct);
		}
		System.out.println("over....");
	}
	public static void main(String[] args) {
//		content_fenci();
//		calFre();
		update(removeTog());
	}
	
	
}
