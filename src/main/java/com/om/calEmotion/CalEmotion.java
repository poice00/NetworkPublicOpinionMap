package com.om.calEmotion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.om.service.CalEmotionService;
import com.om.domain.Comment;
import com.om.domain.DataSource;

@Controller
public class CalEmotion {
	@Autowired
	private CalEmotionService service;

	@RequestMapping("calEmotion.htm")
	public void calEmotion(HttpServletRequest request) throws Exception {
		String path=getClass().getResource("").getPath();
		Map<String, Double> mapPos = service
				.getMapFromText(path+"/userful.txt");
		Map<String, Double> mapInten = service
				.getMapFromText(path+"/intent_userful.txt");
		List<String> fou = new ArrayList<String>();
		fou.add("不");
		int k = 1;
		double result = 0;
		int xx=0;
		while (true) {
			ArrayList<Comment> list = (ArrayList<Comment>) service
					.getComments(xx);
			if (list.size() == 0)
				break;
			for (int i = 0; i < list.size(); i++) {
				Comment comment = list.get(i);
				// System.out.println("-------------------");
				System.out.println(comment.getCommentId());
				result = service.getEmotionValueOneComm(
						comment.getCommentContent(), mapInten, mapPos, fou);
				comment.setEmotionValue(result);
				comment.setState("EmotionProcessed");
				service.getSession().update(comment);
				// System.out.println(result);
				// System.out.println("-------------------");
				System.out.println("[CalEmotion_0]--------------"+k++);
			}

		}
		System.out.println("情感处理结束——————");
	}
	@RequestMapping("calEmotion1.htm")
	public void calEmotion1(HttpServletRequest request) throws Exception {
		String path=getClass().getResource("").getPath();
		Map<String, Double> mapPos = service
				.getMapFromText(path+"/userful.txt");
		Map<String, Double> mapInten = service
				.getMapFromText(path+"/intent_userful.txt");
		List<String> fou = new ArrayList<String>();
		fou.add("不");
		int k = 1;
		double result = 0;
		while (true) {
			ArrayList<Comment> list = (ArrayList<Comment>) service
					.getComments1();
			if (list.size() == 0)
				break;
			for (int i = 0; i < list.size(); i++) {
				Comment comment = list.get(i);
				// System.out.println("-------------------");
				System.out.println(comment.getCommentId());
				result = service.getEmotionValueOneComm(
						comment.getCommentContent(), mapInten, mapPos, fou);
				comment.setEmotionValue(result);
				comment.setState("EmotionProcessed");
				service.getSession().update(comment);
				// System.out.println(result);
				// System.out.println("-------------------");
				System.out.println("[CalEmotion_1]--------------"+k++);
			}

		}
		System.out.println("情感处理结束——————");
	}

	@RequestMapping("calEmotion2.htm")
	public void calEmotion2(HttpServletRequest request) throws Exception {
		String path=getClass().getResource("").getPath();
		Map<String, Double> mapPos = service
				.getMapFromText(path+"/userful.txt");
		Map<String, Double> mapInten = service
				.getMapFromText(path+"/intent_userful.txt");
		List<String> fou = new ArrayList<String>();
		fou.add("不");
		int k = 1;
		double result = 0;
		int xx=30000;
		while (true) {
			ArrayList<Comment> list = (ArrayList<Comment>) service
					.getComments(xx);
			if (list.size() == 0)
				break;
			for (int i = 0; i < list.size(); i++) {
				Comment comment = list.get(i);
				// System.out.println("-------------------");
				System.out.println(comment.getCommentId());
				result = service.getEmotionValueOneComm(
						comment.getCommentContent(), mapInten, mapPos, fou);
				comment.setEmotionValue(result);
				comment.setState("EmotionProcessed");
				service.getSession().update(comment);
				// System.out.println(result);
				// System.out.println("-------------------");
				System.out.println("[CalEmotion_2]--------------"+k++);
			}

		}
		System.out.println("情感处理结束——————");
	}

	@RequestMapping("calEmotion3.htm")
	public void calEmotion3(HttpServletRequest request) throws Exception {
		String path=getClass().getResource("").getPath();
		Map<String, Double> mapPos = service
				.getMapFromText(path+"/userful.txt");
		Map<String, Double> mapInten = service
				.getMapFromText(path+"/intent_userful.txt");
		List<String> fou = new ArrayList<String>();
		fou.add("不");
		int k = 1;
		double result = 0;
		int xx=40000;
		while (true) {
			ArrayList<Comment> list = (ArrayList<Comment>) service
					.getComments(xx);
			if (list.size() == 0)
				break;
			for (int i = 0; i < list.size(); i++) {
				Comment comment = list.get(i);
				// System.out.println("-------------------");
				System.out.println(comment.getCommentId());
				result = service.getEmotionValueOneComm(
						comment.getCommentContent(), mapInten, mapPos, fou);
				comment.setEmotionValue(result);
				comment.setState("EmotionProcessed");
				service.getSession().update(comment);
				// System.out.println(result);
				// System.out.println("-------------------");
				System.out.println("[CalEmotion_3]--------------"+k++);
			}

		}
		System.out.println("情感处理结束——————");
	}

	@RequestMapping("calEmotion4.htm")
	public void calEmotion4(HttpServletRequest request) throws Exception {
		String path=getClass().getResource("").getPath();
		Map<String, Double> mapPos = service
				.getMapFromText(path+"/userful.txt");
		Map<String, Double> mapInten = service
				.getMapFromText(path+"/intent_userful.txt");
		List<String> fou = new ArrayList<String>();
		fou.add("不");
		int k = 1;
		double result = 0;
		int xx=50000;
		while (true) {
			ArrayList<Comment> list = (ArrayList<Comment>) service
					.getComments(xx);
			if (list.size() == 0)
				break;
			for (int i = 0; i < list.size(); i++) {
				Comment comment = list.get(i);
				// System.out.println("-------------------");
				System.out.println(comment.getCommentId());
				result = service.getEmotionValueOneComm(
						comment.getCommentContent(), mapInten, mapPos, fou);
				comment.setEmotionValue(result);
				comment.setState("EmotionProcessed");
				service.getSession().update(comment);
				// System.out.println(result);
				// System.out.println("-------------------");
				System.out.println("[CalEmotion_4]--------------"+k++);
			}

		}
		System.out.println("情感处理结束——————");
	}

	@RequestMapping("calEmotion5.htm")
	public void calEmotion5(HttpServletRequest request) throws Exception {
		String path=getClass().getResource("").getPath();
		Map<String, Double> mapPos = service
				.getMapFromText(path+"/userful.txt");
		Map<String, Double> mapInten = service
				.getMapFromText(path+"/intent_userful.txt");
		List<String> fou = new ArrayList<String>();
		fou.add("不");
		int k = 1;
		double result = 0;
		int xx=60000;
		while (true) {
			ArrayList<Comment> list = (ArrayList<Comment>) service
					.getComments(xx);
			if (list.size() == 0)
				break;
			for (int i = 0; i < list.size(); i++) {
				Comment comment = list.get(i);
				// System.out.println("-------------------");
				System.out.println(comment.getCommentId());
				result = service.getEmotionValueOneComm(
						comment.getCommentContent(), mapInten, mapPos, fou);
				comment.setEmotionValue(result);
				comment.setState("EmotionProcessed");
				service.getSession().update(comment);
				// System.out.println(result);
				// System.out.println("-------------------");
				System.out.println("[CalEmotion_5]--------------"+k++);
			}

		}
		System.out.println("情感处理结束——————");
	}

	@RequestMapping("calDataSourceEmotion.htm")
	public void calDataSourceEmotion() throws Exception {
		Map<String, Double> mapPos = service
				.getMapFromText("C:\\Users\\Administrator\\Desktop\\dic\\userful.txt");
		Map<String, Double> mapInten = service
				.getMapFromText("C:\\Users\\Administrator\\Desktop\\dic\\intent_userful.txt");
		List<String> fou = new ArrayList<String>();
		fou.add("不");
		int k = 1;
		double result = 0;
		while (true) {
			ArrayList<DataSource> list = (ArrayList<DataSource>) service
					.getDataSources();
			if (list.size() == 0)
				break;
			for (int i = 0; i < list.size(); i++) {
				DataSource dataSource = list.get(i);
				// System.out.println("-------------------");
				System.out.println(dataSource.getDataSourceId());
				result = service.getEmotionValueOneComm(
						dataSource.getContent(), mapInten, mapPos, fou);
				dataSource.setEmotionValue(result);
				dataSource.setEmotionState("EmotionProcessed");
				service.getSession().update(dataSource);
				// System.out.println(result);
				// System.out.println("-------------------");
				System.out.println("[CalEmotion1]--------------"+k++);
			}

		}
		System.out.println("情感处理结束——————");
	}

	public static void main(String[] args) throws Exception {
		// Map<String, Double> mapPos =
		// getMapFromText("C:\\Users\\Administrator\\Desktop\\dic\\userful.txt");
		// Map<String, Double> mapInten =
		// getMapFromText("C:\\Users\\Administrator\\Desktop\\dic\\intent_userful.txt");
		// List<String> fou = new ArrayList<String>();
		// fou.add("不");
		/*
		 * CustomSegmentation api = new CustomSegmentation(); String result =
		 * api.analyzeGetResult("不很漂亮"); List<DataBean> list =
		 * Test.getListBeans(result); System.out.println(Test.cal1(list,
		 * mapInten, mapPos, fou));
		 */
		// System.out.println(getEmotionValueOneComm("　许多朋友有这种担忧。", mapInten,
		// mapPos, fou));

	}

}
