package com.om.opinionleader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dependency.CRFDependencyParser;
import com.om.domain.DataSource;
import com.om.domain.WriterFactor;
import com.om.ik.DivideWords;
import com.om.service.DataSourceNoTagService;
import com.om.service.WriterFactorService;

public class leaderRank {
	static ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	static SessionFactory sf = (SessionFactory) ctx.getBean("sessionFactory");
	static Session session=null;
	static Transaction tx=null;
	public void open(){
		session = sf.openSession();
		tx = session.beginTransaction();
	}
	public void close(){
		tx.commit();
		session.close();
		ctx.close();
	}
	public void calRank(){
		WriterFactorService writerFactorService = (WriterFactorService) ctx.getBean("writerFactorServiceImpl");
		DataSourceNoTagService dataSourceNoTagService = (DataSourceNoTagService) ctx.getBean("dataSourceNoTagServiceImpl");
		List<WriterFactor> writerList = new ArrayList<WriterFactor>();
		List<String> wordSplitList = new ArrayList<String>();
		List<WriterFactor> writerFactorsDegree = writerFactorService.findByActiveDegree(10);
		List<WriterFactor> writerFactorsInfluence = writerFactorService.findByInfluence(10);
		writerList.addAll(writerFactorsDegree);
		writerList.addAll(writerFactorsInfluence);
		Set<DataSource> resultSets = new HashSet<DataSource>();
		for (WriterFactor writerFactor : writerList) {
			System.out.println(writerFactor.getWriter().getWriterName());
			List<DataSource> dsList = dataSourceNoTagService.getByWriterId("经济",writerFactor.getWriterFactorId());
			resultSets.addAll(new HashSet<>(dsList));
		}
		for (DataSource dataSource : resultSets) {
			List<String> dataList = DivideWords.split(dataSource.getContent());
			wordSplitList.addAll(dataList);
		}
		Map<String, Integer> freqMap = new TreeMap<String, Integer>();//词频
		for (String word : wordSplitList) {
			//统计每篇文章的词频
			int count= 1;
			if(freqMap.containsKey(word)){
				count  = freqMap.get(word);
				freqMap.put(word, ++ count);
			}else{
				freqMap.put(word, 1);
			}
		}
		Set<String> stopWordSet = getStopWordSet();  
		System.out.println(stopWordSet);
		Set<String> set = freqMap.keySet();
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
		     String m = it.next();
	    	 if(stopWordSet.contains(m)){
	    		 it.remove();
	    	 }
		}
		Map<String, Integer> newMap = sortMap(freqMap);
		Map<String, Integer> resultMap = new LinkedHashMap<String, Integer>();
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(newMap.entrySet());
		for (int i=0;i<25;i++) {
			resultMap.put(list.get(i).getKey(), list.get(i).getValue());
		}
		String str = convertdetail(resultMap);
		writeToPath(str,"F:\\eclipse_workspace_J2EE\\NetworkPublicOpinionMap\\src\\main\\webapp\\data\\rank.txt");
		System.out.println(resultMap);
		System.out.println(newMap.size());
		System.out.println(resultSets.size());
	}
	private void writeToPath(String str, String path) {
		BufferedWriter bw = null;
	    System.out.println("开始写入JSon");
	    
		File sourceFile = new File(path);//创建文件
		try {
			sourceFile.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			FileWriter fr = new FileWriter(sourceFile);
			bw = new BufferedWriter(fr);
			fr.write(str);
			bw.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("写入完毕！");
		
	}
	private static Set<String> getStopWordSet() {
		System.out.println("加载自定义停用词库：stop2.dic");
		String path = "src/main/resources/stop2.dic";
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
	/**
	 * 对MAP中的键值对按VAlue值进行排序 
	 * @param oldMap 排序前的map
	 * @return 排序后的map
	 */
	public static Map<String, Integer> sortMap(Map<String, Integer> oldMap) {   //对MAP中的键值对按VAlue值进行排序 
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(oldMap.entrySet());
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		Map<String, Integer> newMap = new LinkedHashMap<String, Integer>();  
        for (int i = list.size()-1; i >=0 ; i--) {  
            newMap.put(list.get(i).getKey(), list.get(i).getValue());  
        }  
        return newMap;  
	}
	
	private String convertdetail(Map<String, Integer> maps) {
		String str = "";
		String str2 = "";
		str="[";
		str2="[";
		int length=maps.size();
		for (String word:maps.keySet()) {
			if (length>1) {
				str+="\'"+word+"\',";
				length=length-1;
			} else {
				str+="\'"+word+"\'";
			}
		}
		str+="]";
		int length2=maps.size();
		for (String word:maps.keySet()) {
			if (length2>1) {
				str2+=maps.get(word)+",";
				length=length-1;
			} else {
				str2+=maps.get(word);
			}
		}
		str2+="]";
		return str+ ":" + str2;
	}
	public static void main(String[] args) {
//		new leaderRank().calRank();
//		['经济','美元','市场','房价','人民币','投资','土地','资金','发展','社会','房地产','货币','股市','资本','企业','人口','城市','房子','开发商','房屋','危机','金融','资源','国企','战略']
//		[330,247,231,177,173,164,153,152,133,128,125,119,117,116,116,111,107,98,86,83,80,79,77,77,75]
		int total = 330+247+231+177+173+164+133+128+125+119+117+116+116+111+80+79+77+77+75+74+65+64+58+55+55;
		double x = (double)total;
		System.out.println(total);
		System.out.println(330/x);
		System.out.println(247/x);
		System.out.println(231/x);
		System.out.println(177/x);
		System.out.println(173/x);
	}
}
