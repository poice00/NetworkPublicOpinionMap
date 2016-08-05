package com.om.calEmotionFn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;



import com.om.calEmotion.customs.CustomSegmentation;
import com.om.calEmotion.customs.Test;
import com.om.domain.Comment;

public class CalculateEmotion {
	
	private Queue<Comment> crawlUrls = new ConcurrentLinkedQueue<Comment>();
	public String path="C:\\Users\\fenny\\Desktop\\dic\\";
	public Map<String, Double> mapPos;
	public Map<String, Double> mapInten ;

	List<String> fou = new ArrayList<String>();
	
	public Queue<Comment> getCrawlUrls() {
		return crawlUrls;
	}

	public void setCrawlUrls(Queue<Comment> crawlUrls) {
		this.crawlUrls = crawlUrls;
	}

	public CalculateEmotion(List<Comment> list) {
		initQueue(list);
		try {
			mapPos = getMapFromText(path+"/userful.txt");
			mapInten = getMapFromText(path+"/intent_userful.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fou = new ArrayList<String>();
		fou.add("不");
	}

	private void initQueue(List<Comment> list) {
		System.out.println("Add a list of comments to map.");
		crawlUrls.addAll(list);
		System.out.println("final current crawler list size " + getCrawlUrls().size());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Comment> list=DBManager.getCommentList();
		System.out.println(list.size()+"................1");
		startCrawlWriterUrl(list);
	}
	public static void startCrawlWriterUrl(List<Comment> list){
		CalculateEmotion mycrawler= new CalculateEmotion(list);
		for (int i = 0; i < 8; i++) {
			new Thread(new Task(mycrawler)).start();
		}
		System.out.println("情感处理结束——————");
	}
	
	public static class Task implements Runnable {
		private CalculateEmotion tianyacrawler;

		public Task(CalculateEmotion crawler) {
			this.tianyacrawler = crawler;
		}

		@Override
		public void run() {
			Comment ds = null;
			ds = tianyacrawler.getCrawlUrls().poll();
			while (ds != null) {
				try {
					tianyacrawler.function(ds);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("########## current list size : "
						+ tianyacrawler.getCrawlUrls().size());
				ds = tianyacrawler.getCrawlUrls().poll();
			}

		}

	}

	public void function(Comment comment) {
/*		System.out.println("处理："+comment.getCommentId()+"--------------");
		System.out.println(comment.getCommentContent());*/
		double result;
		try {
			result = getEmotionValueOneComm(
					comment.getCommentContent(), mapInten, mapPos, fou);
			comment.setEmotionValue(result);
			DBManager.updateCom(comment);
		} catch (Exception e) {
			DBManager.updateCom1(comment);
			//e.printStackTrace();
		}

	}
	@SuppressWarnings("resource")
	public static  Map<String, Double> getMapFromText(String src)
			throws Exception {
		Map<String, Double> map = new HashMap<String, Double>();
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

	public  static double getEmotionValueOneComm(String content,
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
}
