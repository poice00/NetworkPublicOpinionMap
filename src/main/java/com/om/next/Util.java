package com.om.next;

import java.util.Map;
import java.util.TreeMap;

public class Util {
	/**
	 * 余弦相似度
	 * @param map1 词-权重1
	 * @param map2 词-权重2
	 * @return 
	 */
	public static double getDistance(Map<String, Double> map1,Map<String, Double> map2){
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
	}
	public static void main(String[] args) {
		Map<String, Double> map1 = new TreeMap<>();
		map1.put("你好", 3.0);
		map1.put("我好", 4.0);
		map1.put("他好", 5.0);
		map1.put("都好", 6.0);
		Map<String, Double> map2 = new TreeMap<>();
		map2.put("w", 4.0);
		map2.put("x", 5.0);
		map2.put("都好", 6.0);
		System.out.println(getDistance(map1, map2));
	}
}
