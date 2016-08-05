package com.om.serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.om.base.DaoSupportImpl;
import com.om.calEmotion.CalEmotion;
import com.om.calEmotion.customs.CustomSegmentation;
import com.om.calEmotion.customs.Test;
import com.om.service.CalEmotionService;
import com.om.domain.Comment;
import com.om.domain.DataSource;
@Component
public class CalEmotionServiceImpl extends DaoSupportImpl<CalEmotion> implements CalEmotionService {

	@Override
	public List<Comment> getComments(int begin) {
		@SuppressWarnings("unchecked")
		List<Comment> list = getSession().createCriteria(Comment.class)
				.add(Restrictions.eq("state","Preprocess"))
				.add(Restrictions.isNull("parentId"))
				.setMaxResults(100).list();
		return list;
	}
	@Override
	public List<Comment> getComments1() {
		@SuppressWarnings("unchecked")
		List<Comment> list = getSession().createCriteria(Comment.class)
				.add(Restrictions.eq("state","Preprocess"))
				.add(Restrictions.isNull("parentId"))
				.addOrder(Order.desc("commentId"))
				.setMaxResults(100).list();
		return list;
	}
	@Override
	public List<DataSource> getDataSources() {
		@SuppressWarnings("unchecked")
		List<DataSource> list = (List<DataSource>)getSession().createCriteria(DataSource.class)
				.add(Restrictions.isNull("emotionState")).setMaxResults(100).list();
		return list;
	}
	public  Map<String, Double> getMapFromText(String src)
			throws Exception {
		Map<String, Double> map = new HashMap<String, Double>();
		@SuppressWarnings("resource")
		BufferedReader bReader = new BufferedReader(new FileReader(
				new File(src)));
		String s;
		String[] strs = new String[2];
		while (!(s = bReader.readLine()).equals("$")) {
			System.out.println(s);
			strs = s.split("[ ]+");
			map.put(strs[0].trim(), Double.valueOf(strs[1]));

		}

		return map;

	}

	public  double getEmotionValueOneComm(String content,
			Map<String, Double> intenMap, Map<String, Double> posMap,
			List<String> fou) throws Exception {
         if(content.contains("%"))
        	 content=content.replaceAll("%", "");
		String[] strs = content.split("[，,.!?:。！~？：  ]+");
		double result = 0.0;
		 int k=0;
		 double temp=0.0;
		CustomSegmentation api = new CustomSegmentation();
		for (int i = 0; i < strs.length; i++) {
			//System.out.println(strs[i]);
			//System.out.println(strs[i].length());
			if (strs[i].replaceAll("\\s", "").length() > 0&&strs[i].length()<200){
				temp= Test.cal1(Test.getListBeans(api.analyzeGetResult(strs[i])),
						intenMap, posMap, fou);
				if(temp!=0)
					k++;
	
			}
			else if(strs[i].length()>50){
				int x=strs[i].length()/10;
				String str2= strs[i];
				while(strs[i].length()>50){
					
					str2=strs[i].substring(0,50);
					//System.out.println(str2+"   -----");
					temp+=Test.cal1(Test.getListBeans(api.analyzeGetResult(str2)),
							intenMap, posMap, fou);
				    strs[i]=strs[i].substring(50);
				    //System.out.println(temp);
				}
				temp+=Test.cal1(Test.getListBeans(api.analyzeGetResult(strs[i])),
						intenMap, posMap, fou);
				k+=x;
			}
			
			result+=temp;	
		}
		if(k==0) return 0;
		return result/k;
	}
	public String [] getString(String str){
		return null;
	}
	public static void main(String[] args) throws Exception{
	CalEmotionServiceImpl calEmotionServiceImpl=new CalEmotionServiceImpl();
		Map<String, Double> mapPos = 
				calEmotionServiceImpl.getMapFromText("C:\\Users\\fenny\\Desktop\\dic\\userful.txt");
		Map<String, Double> mapInten = 
				calEmotionServiceImpl.getMapFromText("C:\\Users\\fenny\\Desktop\\dic\\intent_userful.txt");
		List<String> fou = new ArrayList<String>();
		fou.add("不");
		double s=calEmotionServiceImpl.getEmotionValueOneComm("多谢你的回复。   楼主还真没有一颗贪婪的心。对于自己想要什么，楼主一直很清楚，能够把持住自己。   回答你的问题。   1，没有核心优势，但有点小优势。楼主不是要做大事业，有点资金，不需要技术，回去的话，人脉是弱点。优势在于，有亲戚带我们上路，不是自己瞎闯瞎撞，他的产品资源啥的，可以跟我们共享，老公也可以在他那里先打工半年，积累经验。   2，我暂不辞职，就是为了抗击风险，老公在外闯，我可以支撑房贷和生活费用。我们做的项目，不至于三年没有任何收入。若一年没任何收入，就等于失败，老公从武汉回江浙沪，继续做生意，但是，有亲戚拉着，会好很多。   3，变动影响的只是孩子。对双方父母影响很小。我父母本来就在老家，我们出来闯，他们可能心理上会担心，经济和生活没任何影响。公婆以后也是要回老家的，可以自食其力。只是孩子，可能要从住的好好的大房子里，换到一个陌生的环境。但他还小，我认为可以接受和适应。", mapInten, mapPos, fou);
	System.out.println(s);
	}
  
}
