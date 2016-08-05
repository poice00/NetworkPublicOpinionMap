package com.om.ik;

import java.util.Map;

/**
* 先验概率计算
* 先验概率计算
* P(cj)=N(C=cj)/N
* 其中，N(C=cj)表示类别cj中的训练文本数量；
* N表示训练文本集总数量。
*/
public class PriorProbability 
{
//   private static TrainingDataManager tdm =new TrainingDataManager();
    /**
    * 先验概率
    * @param c 给定的分类
    * @return  给定条件下的先验概率
    */
    public static float calculatePc(String c)
    {
        float ret = 0F;
        float Nc = TrainingDataManager.getTrainingFileCountOfClassification(c);
        float N = TrainingDataManager.getTrainingFileCount();
//        System.out.println("Nc : " +Nc );
//        System.out.println("N : " +N );
        ret = Nc / N;
        System.out.println(c +"的先验概率： " +ret);
        return ret;
    }

	public static float calculateP(Map<String, Float> pp, String cj) {
		return pp.get(cj);
	}
}