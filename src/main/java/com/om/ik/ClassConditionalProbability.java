package com.om.ik;

import java.util.LinkedHashMap;
import java.util.Map;

/**
	* 类条件概率计算
	*
	* 类条件概率
	* P(xj|cj)=( N(X=xi, C=cj)+1 ) / ( N(C=cj)+M+V ) 
	* 其中，N(X=xi, C=cj）表示类别cj中包含属性xi的训练文本数量；
	* N(C=cj)表示类别cj中的训练文本数量；M值用于避免N(X=xi, C=cj）过小所引发的问题；V表示类别的总数。
	*
	* 条件概率
	* 
	* 定义 设A, B是两个事件，且P(A)>0 称
	* P(B∣A)=P(AB)/P(A)
	* 为在条件A下发生的条件事件B发生的条件概率。
*/

public class ClassConditionalProbability 
{
//    private static TrainingDataManager tdm = new TrainingDataManager();
    //private static final float M = 0F;
    
    /**
     * 先验概率P(c)= 类c下单词总数/整个训练样本的单词总数
		类条件概率P(tk|c)=(类c下单词tk在各个训练集文档中出现过的次数之和+1)/(类c下单词总数+|V|)
    * 计算类条件概率
    * @param x 给定的文本属性
    * @param c 给定的分类
    * @return 给定条件下的类条件概率
    */
    //分词后的文档String[]
    //for 对于每一个词，ifMpa，P=0;
   //p=P+logPst 1/List[2]
//    public static float calculatePxc(String x, String c) 
//    {
//        float ret = 0F;
//        float Nxc = TrainingDataManager.getCountContainKeyOfClassification(x, c);
//        float Nc = TrainingDataManager.getTrainingFileCountOfClassification(c);
//        float V = TrainingDataManager.getTraningClassificationsWords();
////        System.out.println("NXC : " + Nxc);
////        System.out.println("Nc : " + Nc);
////        System.out.println(" V : " + V);
//        ret = (Nxc + 1) / (Nc + V); //为了避免出现0这样极端情况，进行加权处理
////        System.out.println(c+"的类条件概率 : " +ret);
//        return ret;
//    }

	public static Map<String, Float> calculateC(String c) {
		float ret = 0F;
		Map<String, Float> resultMap = new LinkedHashMap<String, Float>();
        Map<String, Integer> NxcMap = TrainingDataManager.getCountOfClassification(c);
        float Nc = TrainingDataManager.getTrainingFileCountOfClassification(c);
        float V = TrainingDataManager.getTraningClassificationsWords();
//        System.out.println("NXC : " + Nxc);
//        System.out.println("Nc : " + Nc);
//        System.out.println(" V : " + V);
        for (String x : NxcMap.keySet()) {
        	ret = (NxcMap.get(x) + 1) / (Nc + V); //为了避免出现0这样极端情况，进行加权处理
        	resultMap.put(x, ret);
		}
//        System.out.println(c+"的类条件概率 : " +ret);
        return resultMap;
	}

	public static float calculatePxc(String xi, Map<String, Map<String, Float>> cp, String cj, Map<String, Float> uniqueResult) {
		float ret = 0;
//		for (String x : cp.get(cj).keySet()) {
//			if(x.equals(xi)){
//				//System.out.println("--------------------！！！包含了！！-------------------");
//				//System.out.println("--------------------"+xi+"=="+cp.get(cj).get(x)+"-------------------");
//				ret =  cp.get(cj).get(x);
//				
//				return ret;
//			}else{
//				ret = uniqueResult.get(cj);
//			}
//		}
		
//		if(cp.get(cj).keySet().contains(xi)){
//			System.out.println("--------------------！！！包含了！！-------------------");
//			System.out.println("--------------------"+xi+"=="+cp.get(cj).get(xi)+"-------------------");
//			ret =  cp.get(cj).get(xi);
//		}
//		else
//			ret = uniqueResult.get(cj);
		
		
		if(cp.get(cj).get(xi)!=null){
			ret =  cp.get(cj).get(xi);
		}
		else
			ret = uniqueResult.get(cj);
		return ret;
        
	}

	public static Map<String, Float> calculateUnique() {
		float ret = 0F;
		Map<String, Float> resultMap = new LinkedHashMap<String, Float>();
		String[] ss = TrainingDataManager.getTraningClassifications();
        float V = TrainingDataManager.getTraningClassificationsWords();
        for (String s : ss) {
        	float Nc = TrainingDataManager.getTrainingFileCountOfClassification(s);
        	ret = 1 / (Nc + V); //为了避免出现0这样极端情况，进行加权处理
        	resultMap.put(s, ret);
		}
//    	System.out.println("result : " +  resultMap);
        return resultMap;
	}
}
