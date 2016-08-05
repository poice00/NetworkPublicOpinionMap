package com.om.ik;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.om.domain.ClassifyType;
import com.om.service.ClassifyTypeService;

/**
* 训练集管理器
*/
public class TrainingDataManager 
{
    
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
	 * 
	 * @return 类别的集合
	 */
	public static String[] getTraningClassifications() {
		String []classify = {"政治","生态","经济","军事","社会"}; 
//		for (int i = 0;i<dataList.size();i++) {
//			classify[i] = dataList.get(i).getClassifyName();
//		}
		return classify;
	}
	/**
	 * 
	 * @return 训练集所有的分词数目不重复
	 */
	public static int getTraningClassificationsWords() {
		ClassifyTypeService classifyTypeService = (ClassifyTypeService) ctx.getBean("classifyTypeServiceImpl");
		List<ClassifyType> dataList = classifyTypeService.findAll();
		double num = dataList.get(0).getSumWordFre();
		int n = (int)num;
		return n;
	}
	
	
    /**
    * 返回训练文本集中所有的分词数目
    * @return 训练文本集中所有的分词数目
    */
    public static int getTrainingFileCount()
    {
    	ClassifyTypeService classifyTypeService = (ClassifyTypeService) ctx.getBean("classifyTypeServiceImpl");
    	List<ClassifyType> dataList = classifyTypeService.findAll();
		int num = 0 ;
		for (ClassifyType classifyType : dataList) {
			num += classifyType.getSumText();
		}
        return num;
    }
    /**
    * 返回训练文本集中在给定分类下的分词数目
    * @param classification 给定的分类
    * @return 训练文本集中在给定分类下的分词数目
    */
    public static int getTrainingFileCountOfClassification(String classification)
    {
    	ClassifyTypeService classifyTypeService = (ClassifyTypeService) ctx.getBean("classifyTypeServiceImpl");
		ClassifyType classifyType = classifyTypeService.getByName(classification);	
		double x= classifyType.getSumText();
		int num = (int) x;
        return num;
    }
    /**
    * 返回给定分类中包含关键字／词的训练文本的数目
    * @param classification 给定的分类
    * @param key 给定的关键字／词
    * @return 给定分类中包含关键字／词的训练文本的数目
    */
    //返回一个Map函数，词和概率的对应关系
  //定义一个ArrayList数组，第一个存放Map函数，第二个存放概率，第三个存放单词总数+不同单词数量
    public static int getCountContainKeyOfClassification(String key,String classification) 
    {
		Map<String, Integer> resultMap  =TrainingDataManager.getCountOfClassification(classification);
		if(resultMap.get(key)!=null){
			int ret = resultMap.get(key);
	        return ret;
		}else{
			return 0;
		}
    }
    
    public static Map<String, Integer> getCountOfClassification(String classification) 
    {
    	ClassifyTypeService classifyTypeService = (ClassifyTypeService) ctx.getBean("classifyTypeServiceImpl");
		ClassifyType classifyType = classifyTypeService.getByName(classification);	
		Map<String, Integer> resultMap  = new HashMap<String, Integer>();
		String pre[] = classifyType.getClassifyContent().split(",");
		for (String ss : pre) {
			resultMap.put(ss.split(":")[0], Integer.parseInt(ss.split(":")[1]));
		}
		return resultMap;
    }
//    public static void main(String[] args) {
//    	new TrainingDataManager().getTrainingFileCount();
//    	new TrainingDataManager().getTrainingFileCountOfClassification("政治");
//    	new TrainingDataManager().getCountContainKeyOfClassification("政治","美国");
//    	new TrainingDataManager().getTraningClassifications();
//    	new TrainingDataManager().getTraningClassifications();
//	}
	
	
}