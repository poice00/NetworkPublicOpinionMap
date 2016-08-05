package com.om.opinionleader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.om.domain.WriterFactor;
import com.om.service.WriterFactorService;

public class Test {
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
	public List<String> getLive(){
		WriterFactorService writerFactorService = (WriterFactorService) ctx.getBean("writerFactorServiceImpl");
		List<WriterFactor> writerFactorList = writerFactorService.findAll();
		List<String> dataList = new ArrayList<String>();
		Map<WriterFactor,Double> maps = new HashMap<WriterFactor,Double>();
		for (WriterFactor writerFactor : writerFactorList) {
			double finalIn = writerFactor.getAuthorInfluence()*0.6+ writerFactor.getAuthorActiveDegree()*0.4 ;
			maps.put(writerFactor, finalIn);
		}
		Map<WriterFactor, Double> newMap = sortMap(maps);
		int num = 0;
		for (WriterFactor x : newMap.keySet()) {
			while(num>=500){
				return dataList;
			}
			if(x.getWriter().getWriterArea()!=null){
//				System.out.println(x.getWriter().getWriterName() + "-" + x.getWriter().getWriterArea() +": "+newMap.get(x));
				dataList.add(x.getWriter().getWriterArea());
			}
			num ++;
//			System.out.println("num : " +num);
		}
		return dataList;
		
	}
	/**
	 * 对MAP中的键值对按VAlue值进行排序 
	 * @param oldMap 排序前的map
	 * @return 排序后的map
	 */
	public static Map<WriterFactor, Double> sortMap(Map<WriterFactor, Double> oldMap) {   //对MAP中的键值对按VAlue值进行排序 
        ArrayList<Entry<WriterFactor, Double>> list = new ArrayList<Entry<WriterFactor, Double>>(oldMap.entrySet());  
        Collections.sort(list, new Comparator<Map.Entry<WriterFactor, Double>>() {  //排序
            public int compare(Entry<WriterFactor, Double> map1,  
                    Entry<WriterFactor, Double> map2) {  
                return map1.getValue().compareTo(map2.getValue());  
            }  
        });  
        //一般情况下，我们用的最多的是HashMap,在Map 中插入、删除和定位元素，HashMap 是最好的选择。
        //但如果您要按自然顺序或自定义顺序遍历键，那么TreeMap会更好。
        //如果需要输出的顺序和输入的相同,那么用LinkedHashMap 可以实现,它还可以按读取顺序来排列.
        Map<WriterFactor, Double> newMap = new LinkedHashMap<WriterFactor, Double>();  
        for (int i = list.size()-1; i >=0 ; i--) {  
            newMap.put(list.get(i).getKey(), list.get(i).getValue());  
        }  
        return newMap;  
    }
	public static void main(String[] args) {
		List<String> dataList = new Test().getLive();
		System.out.println(dataList.size());
		Map<String,Integer> Fremaps = new HashMap<String,Integer>();
		Map<String,Double> mapsPrec = new HashMap<String,Double>();
		for (String s : dataList) {
			if(Fremaps.get(s.substring(0,2))!=null){
				int num = Fremaps.get(s.substring(0,2));
				Fremaps.put(s.substring(0,2), ++num);
			}else{
				Fremaps.put(s.substring(0,2), 1);
			}
		}
		for (String s : Fremaps.keySet()) {
			double result = (double)Fremaps.get(s)/dataList.size(); 
			double x = result*100;
			BigDecimal b = new BigDecimal(x);  
			double ret = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			mapsPrec.put(s, ret);
		}
		
		System.out.println(Fremaps);
		System.out.println(mapsPrec);
	}
}
