package com.om.ik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 朴素贝叶斯分类器
*/
public class BayesClassifier 
{
//    private TrainingDataManager tdm;//训练集管理器
//    private static double zoomFactor = 1.0f;
//    /**
//    * 默认的构造器，初始化训练集
//    */
	private Map<String, Float> pp = new HashMap<String,Float>();
	private Map<String, Map<String,Float>> cp = new HashMap<String, Map<String,Float>>();
	private  Map<String, Float> uniqueResult=new HashMap<String,Float>();
  

	public BayesClassifier() 
    {
        String[] Classes = TrainingDataManager.getTraningClassifications();
        for (String c : Classes) {
//        	Map<String, Float> p1 = new HashMap<String, Float>(); ;
        	Map<String, Float> p2 = ClassConditionalProbability.calculateC(c);
        	float p = PriorProbability.calculatePc(c);
        	setUniqueResult(ClassConditionalProbability.calculateUnique());
        	pp.put(c, p);
        	cp.put(c, p2);
		}
    }
	
    /**
    * 计算给定的文本属性向量X在给定的分类Cj中的类条件概率
    * ClassConditionalProbability连乘值
    * @param X 给定的文本属性向量
    * @param Cj 给定的类别
    * @return 分类条件概率连乘值，即<br>
    */
    float calcProd(String[] X, String Cj) 
    {
//    	ArrayList list=d=（Cj）；
//    	Map =
//    	
        float ret = 0.0f;
        // 类条件概率连乘
        String Xi = null;
        for (int i = 0; i <X.length; i++)
        {
            Xi = X[i];
//            System.out.println(Xi + ":" + ClassConditionalProbability.calculatePxc(Xi, cp, Cj,uniqueResult));
            //因为结果过小，因此在连乘之前放大10倍，这对最终结果并无影响，因为我们只是比较概率大小而已
//            ret *= ClassConditionalProbability.calculatePxc(Xi, cp, Cj,uniqueResult);
//            float p =(float) Math.log10(ClassConditionalProbability.calculatePxc(Xi, cp, Cj,uniqueResult));//先验概率
            ret += (float) Math.log10(ClassConditionalProbability.calculatePxc(Xi, cp, Cj,uniqueResult));
        }
//        System.out.println("ret0 : " + ret);
//        System.out.println(Cj +"的类条件概率： " +ret);
        // 再乘以先验概率
        ret += (float) Math.log10(PriorProbability.calculateP(pp,Cj)); //类条件概率
//        ret *= PriorProbability.calculateP(pp,Cj);
//        System.out.println("ret1 : " + ret);
        return ret;
    }
    
    /**
     * 
     * @param textList list集合
     * @return 数组
     */
    private String[] convertnumGruop(List<String> textList) {
    	
    	String []s = new String[textList.size()];
    	for (int i = 0;i<textList.size();i++) {
    		s[i] = textList.get(i);
    	}
    	return s;
	}
    
    /**
    * 对给定的文本进行分类
    * @param text 给定的文本
    * @return 分类结果
    */
    @SuppressWarnings("unchecked")
    public String classify(String text) 
    {
        List<String> textList = DivideWords.split(text);
        String[] terms = convertnumGruop(textList);//转化为数组
        
        String[] Classes = TrainingDataManager.getTraningClassifications();//分类
        float probility = 0.0F;
        List<ClassifyResult> crs = new ArrayList<ClassifyResult>();//分类结果
        for (int i = 0; i <Classes.length; i++) 
        {
            String Ci = Classes[i];//第i个分类
            probility = calcProd(terms, Ci);//计算给定的文本属性向量terms在给定的分类Ci中的分类条件概率
//            System.out.println(Ci + "的最终概率 : " + probility);
            //保存分类结果
            ClassifyResult cr = new ClassifyResult();
            cr.classification = Ci;//分类
            cr.probility = probility;//关键字在分类的条件概率
//            System.out.println("In process.");
//          System.out.println(Ci + "：" + probility);
            crs.add(cr);
//            break;
        }
        //对最后概率结果进行排序
        Collections.sort(crs,new Comparator<Object>() 
        {
            public int compare(final Object o1,final Object o2) 
            {
                final ClassifyResult m1 = (ClassifyResult) o1;
                final ClassifyResult m2 = (ClassifyResult) o2;
                final double ret = m1.probility - m2.probility;
                if (ret < 0) 
                {
                    return 1;
                } 
                else 
                {
                    return -1;
                }
            }
        });
        System.out.println(crs.toString());
        //返回概率最大的分类
        return crs.get(0).classification;
    }

	

	public Map<String, Float> getPp() {
		return pp;
	}

	public void setPp(Map<String, Float> pp) {
		this.pp = pp;
	}

	public Map<String, Map<String, Float>> getCp() {
		return cp;
	}

	public void setCp(Map<String, Map<String, Float>> cp) {
		this.cp = cp;
	}

	public Map<String, Float> getUniqueResult() {
		return uniqueResult;
	}

	public void setUniqueResult(Map<String, Float> uniqueResult) {
		this.uniqueResult = uniqueResult;
	}

   

//	public static void main(String[] args)
//    {
//        String text = "污染治理";
//        BayesClassifier classifier = new BayesClassifier();//构造Bayes分类器
//        String result = classifier.classify(text);//进行分类
//        System.out.println("此项属于["+result+"]");
//    }
}