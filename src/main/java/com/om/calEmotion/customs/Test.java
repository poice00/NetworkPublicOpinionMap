package com.om.calEmotion.customs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
	public static double calEmotion(String str) {
		String[] strs = str.split("[ ]+");
		for (int i = 0; i < strs.length; i++) {
			System.out.println(strs[i]);
		}
		return 0.0;
	}

	public static double cal1(List<DataBean> list,
			Map<String, Double> intenMap, Map<String, Double> posMap,
			List<String> fou) {
		double result = 0.0;
		//printList(list);
		//System.out.println("-----------------------");
		for (int i = 0; i < list.size(); i++) {
			// System.out.println(result);
			DataBean dataBean = list.get(i);
			if (posMap.containsKey(dataBean.getFirstWord())
					&& dataBean.isState()) {
				result += cal2(i, dataBean.getFirstPositon(),
						dataBean.getFirstWord(), list, intenMap, posMap, fou);
				//System.out.println(dataBean.getFirstWord() + "," + result);
			}
			if (posMap.containsKey(dataBean.getSecondWord())
					&& dataBean.isState()) {
				result += cal2(i, dataBean.getSecondPositon(),
						dataBean.getSecondWord(), list, intenMap, posMap, fou);
				//System.out.println(dataBean.getSecondWord() + "." + result);
			}
			//printList(list);
			//System.out.println("-----------------------");
		}
		return result;
	}

	public static double cal2(int j, int pos, String posWord,
			List<DataBean> list, Map<String, Double> intenMap,
			Map<String, Double> posMap, List<String> fou) {
		double result = posMap.get(posWord);

		//System.out.println("极性词" + posWord);
		result *= cal4(pos, pos, list, intenMap);
		/*
		 * for (int i = pos; i < list.size(); i++) { DataBean dataBean =
		 * list.get(i); double length = Math.abs(dataBean.getFirstPositon() -
		 * dataBean.getSecondPositon()); double mul = 0;
		 */
		/*
		 * if (posWord.equals(dataBean.getFirstWord()) && dataBean.isState()) {
		 * result += cal3(i, list, intenMap, posMap, fou);
		 * System.out.println(dataBean.getFirstWord()+result);
		 * dataBean.setState(false); } if
		 * (posWord.equals(dataBean.getSecondWord()) && dataBean.isState()) {
		 * result += cal3(i, list, intenMap, posMap, fou);
		 * System.out.println(dataBean.getSecondWord()+result);
		 * dataBean.setState(false); }
		 */
		/*
		 * if (dataBean.isState() && posWord.equals(dataBean.getFirstWord())) {
		 * if (intenMap.containsKey(dataBean.getSecondWord())) { mul =
		 * intenMap.get(dataBean.getSecondWord()) > 0 ? intenMap
		 * .get(dataBean.getSecondWord()) : intenMap
		 * .get(dataBean.getSecondWord()) / length; } result *= mul;
		 * dataBean.setState(false); }
		 * 
		 * if (dataBean.isState() && posWord.equals(dataBean.getSecondWord())) {
		 * if (intenMap.containsKey(dataBean.getFirstWord())) result *=
		 * intenMap.get(dataBean.getFirstWord()) > 0 ? intenMap
		 * .get(dataBean.getFirstWord()) : intenMap
		 * .get(dataBean.getFirstWord()) / length; dataBean.setState(false); } }
		 */
		double flag = getPos(j, list, fou);
		//System.out.println(flag + "flag");
		setStringPos(pos, list);
		double intent = getStaticIntent(pos, list, intenMap);
		return flag * result * intent;
	}

	public static double cal3(int xx, List<DataBean> list,
			Map<String, Double> intenMap, Map<String, Double> posMap,
			List<String> fou) {
		DataBean dataBean = list.get(xx);

		double x = 0;
		double length = Math.abs(dataBean.getFirstPositon()
				- dataBean.getSecondPositon());
		if (posMap.containsKey(dataBean.getFirstWord()) && dataBean.isState()) {
			// System.out.println(dataBean.getFirstWord());
			double a = intenMap.containsKey(dataBean.getSecondWord()) ? intenMap
					.get(dataBean.getSecondWord()) : posMap
					.containsKey(dataBean.getSecondWord()) ? posMap
					.get(dataBean.getSecondWord()) : 0;
			x = a * posMap.get(dataBean.getFirstWord()) / length;

		}
		if (posMap.containsKey(dataBean.getSecondWord()) && dataBean.isState()) {
			double a = intenMap.containsKey(dataBean.getFirstWord()) ? intenMap
					.get(dataBean.getFirstWord()) : posMap.containsKey(dataBean
					.getFirstWord()) ? posMap.get(dataBean.getFirstWord()) : 0;
			x = a * posMap.get(dataBean.getSecondWord()) / length;

		}

		return x;
	}

	public static double cal4(int center, int pos, List<DataBean> list,
			Map<String, Double> intenMap) {
		double result = 1.0;
		double length = 1.0;
		for (int i = 0; i < list.size(); i++) {
			DataBean dataBean = list.get(i);
			if (dataBean.isState()) {
				if (dataBean.getFirstPositon() == pos) {
					if (intenMap.containsKey(dataBean.getSecondWord())) {
						length = Math.abs(dataBean.getSecondPositon() - center) > 1 ? 2
								: 1;
						result *= intenMap.get(dataBean.getSecondWord()) > 0 ? intenMap
								.get(dataBean.getSecondWord()) : intenMap
								.get(dataBean.getSecondWord()) / length;

						dataBean.setState(false);
						result *= cal4(center, dataBean.getSecondPositon(),
								list, intenMap);
					}
					//System.out.println(result + "--");
				}
				if (dataBean.getSecondPositon() == pos) {
					if (intenMap.containsKey(dataBean.getFirstWord())) {
						length = Math.abs(dataBean.getFirstPositon() - center) > 1 ? 2
								: 1;
						//System.out
							//	.println(intenMap.get(dataBean.getFirstWord()));
						result *= intenMap.get(dataBean.getFirstWord()) > 0 ? intenMap
								.get(dataBean.getFirstWord()) : intenMap
								.get(dataBean.getFirstWord()) / length;
						//System.out.println(result + "22" + length);
						dataBean.setState(false);
						result *= cal4(center, dataBean.getFirstPositon(),
								list, intenMap);
					}
				}
			}
		}
		return result;
	}

	// 获得未使用的全局极性
	public static double getPos(int xx, List<DataBean> list, List<String> fou) {
		double flag = 1;
		for (int i = 0; i < xx; i++) {
			DataBean dataBean = list.get(i);
			if (fou.contains(dataBean.getFirstWord()) && dataBean.isState()) {
				if (isNotUsed(dataBean.getFirstPositon(), list))
					flag = -flag;
				// System.out.println(flag+"--");
			}

			if (fou.contains(dataBean.getSecondWord()) && dataBean.isState()) {
				// System.out.println(isNotUsed(dataBean.getSecondPositon(),
				// list));
				if (isNotUsed(dataBean.getSecondPositon(), list))
					flag = -flag;
				// System.out.println(flag+"++");
			}

		}

		return flag > 0 ? flag : flag / 2;
	}

	public static boolean isNotUsed(int pos, List<DataBean> list) {
		//System.out.println(pos + ">>>>>>>");
		for (int i = 0; i < list.size(); i++) {
			DataBean dataBean = list.get(i);
			if ((dataBean.getFirstPositon() == pos || dataBean
					.getSecondPositon() == pos) && !dataBean.isState())
				return false;
		}
		return true;
	}

	public static DataBean getDataBean(String str) {

		// String s = "我_0 是_1 SBV";
		//System.out.println(str+">>>>>>>>>>>>>>>>>>>>>>");
		if(str.trim().length()==0){
			return null;
		}
		String[] strs = str.split("[ ]+");
		//System.out.println(strs[0]);
		String firstWord = strs[0].substring(0, strs[0].lastIndexOf("_"));
		// System.out.println(firstWord);
		int firstPositon = Integer.valueOf(strs[0].substring(strs[0]
				.lastIndexOf("_") + 1));
		String secondWord = "";
		int secondPosition = 0;
		if (strs[1].equals("-1")) {
			secondWord = "_";
			secondPosition = -1;
		} else {
			secondWord = strs[1].substring(0, strs[1].lastIndexOf("_"));
			secondPosition = Integer.valueOf(strs[1].substring(strs[1]
					.lastIndexOf("_") + 1));
		}
		return new DataBean(firstWord, firstPositon, secondWord,
				secondPosition, strs[2], 0, true);
	}

	public static List<DataBean> getListBeans(String str) {
		List<DataBean> dataBeans = new ArrayList<DataBean>();
		String[] strs = str.split("[#]{4}");
		for (int i = 0; i < strs.length; i++) {
			// System.out.println("," + strs[i] + ",");
			DataBean dataBean = getDataBean(strs[i]);
			// System.out.println(dataBean);
			if(dataBean!=null)
			dataBeans.add(dataBean);
		}
		return dataBeans;
	}

	// 把当前计算过的情感词置为false
	public static void setStringPos(int pos, List<DataBean> list) {
		for (int i = 0; i < list.size(); i++) {
			DataBean dataBean = list.get(i);
			if (dataBean.getFirstPositon() == pos
					|| dataBean.getSecondPositon() == pos)
				dataBean.setState(false);

		}
	}

	// 把剩余的程度副词考虑在内
	public static double getStaticIntent(int center, List<DataBean> list,
			Map<String, Double> intenMap) {
		double result = 1.0;
		for (int i = 0; i < list.size(); i++) {
			DataBean dataBean = list.get(i);
			if (dataBean.isState()) {
				if (intenMap.containsKey(dataBean.getFirstWord())
						&& dataBean.getFirstPositon() < center)
					result *= intenMap.get(dataBean.getFirstWord());
				if (intenMap.containsKey(dataBean.getSecondWord())
						&& dataBean.getSecondPositon() < center)
					result *= intenMap.get(dataBean.getSecondWord());
			}
		}
		return result;
	}

	public static void printList(List<DataBean> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

	public static Map<String, Double> getMapFromText(String src)
			throws Exception {
		Map<String, Double> map = new HashMap<String, Double>();
		BufferedReader bReader = new BufferedReader(new FileReader(
				new File(src)));
		String s;
		String[] strs = new String[2];
		while (!(s = bReader.readLine()).equals("$")) {
			strs = s.split("[ ]+");
			map.put(strs[0].trim(), Double.valueOf(strs[1]));

		}
		bReader.close();
		return map;

	}

	public static void main(String[] args) throws Exception {
		/*
		 * String str = "我_0 是_1 SBV" + "是_1 -1 HED" + "中国_2 人_3 ATT" +
		 * "人_3 是_1 VOB"; calEmotion(str);
		 */
		/*
		 * System.out.println(getDataBean("我_0 是_1 SBV"));
		 * 
		 * List<DataBean> list = new ArrayList<DataBean>(); list.add(new
		 * DataBean("他", 0, "是", 2, "SBV", 0, true)); list.add(new DataBean("不",
		 * 1, "是", 2, "ADV", 0, true)); list.add(new DataBean("是", 2, "-", -1,
		 * "HED", 0, true)); list.add(new DataBean("一个", 3, "人", 7, "ATT", 0,
		 * true)); list.add(new DataBean("很", 4, "骄傲", 5, "ADV", 0, true));
		 * list.add(new DataBean("骄傲", 5, "人", 7, "ATT", 0, true)); list.add(new
		 * DataBean("的", 6, "骄傲", 5, "RAD", 0, true)); list.add(new
		 * DataBean("人", 7, "是", 2, "VOB", 0, true)); list.add(new DataBean(",",
		 * 8, "是", 2, "WP", 0, true)); System.out.println(cal1(list, mapInten,
		 * mapPos, fou));
		 */
		//Map<String, Double> mapPos = getMapFromText("C:\\Users\\Administrator\\Desktop\\dic\\userful.txt");
		//mapPos.put("漂亮", 0.8);
		//mapPos.put("美丽", 0.7);
		//Map<String, Double> mapInten = getMapFromText("C:\\Users\\Administrator\\Desktop\\dic\\intent_userful.txt");
		//mapInten.put("很", 1.5);
		//mapInten.put("不", -1.0);
		//List<String> fou = new ArrayList<String>();
		//fou.add("不");
		//CustomSegmentation api = new CustomSegmentation();
		// api.analyze("file.txt");
		//String result = api.analyzeGetResult("不很漂亮");
		//List<DataBean> list = getListBeans(result);
		//System.out.println(cal1(list, mapInten, mapPos, fou));
		System.out.println("http://www.xzlza.gov.cn/qx_show.aspx_0".lastIndexOf('_'));
		getDataBean("http://www.xzlza.gov.cn/qx_show.aspx_0 -1 HED");
		

	}
}
