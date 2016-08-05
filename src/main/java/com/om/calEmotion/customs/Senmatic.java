package com.om.calEmotion.customs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Senmatic {
	public static List<String> getFromText(String src) throws Exception {
		List<String> list = new ArrayList<String>();
		String s;
		File file = new File(src);
		BufferedReader bReader = new BufferedReader(new FileReader(file));
		while (!(s = bReader.readLine()).equals("$")) {
			list.add(s);
		}
		System.out.println(list.size());
		bReader.close();
		return list;

	}

	public static List<String> writeToText(List<String> list) throws Exception {
		CustomSegmentation api = new CustomSegmentation();

		BufferedWriter brWriter = new BufferedWriter(new FileWriter(
				"C:\\Users\\Administrator\\Desktop\\sysy\\intent_userful.txt"));
		BufferedWriter brWriter1 = new BufferedWriter(
				new FileWriter(
						"C:\\Users\\Administrator\\Desktop\\sysy\\intent_nouserfu1.txt"));
		String s;
		int i = 1;
		int j = 1;
		int k = 1;
		for (int k2 = 0; k2 < list.size(); k2++) {
			s = list.get(k2);
			System.out.println(s + "," + i++);
			String[] strs = s.split("[ ]+");
			if (api.analyzeGetResult(strs[0]).length() <= strs[0].length() + 9) {
				System.out.println("userful" + j++);
				brWriter.write(s);
				brWriter.newLine();
			} else {
				System.out.println("nouserfule" + k++);
				brWriter1.write(s);
				brWriter1.newLine();
			}
          
			// System.out.println(strs[0].trim());
			// System.out.println(strs[1].trim());
		}
		  brWriter.close();
          brWriter1.close();

		return null;
	}

	public static void readToWrite(String src, String desc) throws Exception {
		BufferedReader bReader = new BufferedReader(new FileReader(
				new File(src)));
		BufferedWriter bWriter = new BufferedWriter(new FileWriter(desc));
		String s = "";
		while (!(s = bReader.readLine()).equals("$")) {
			String[] strs = s.split("[ ]+");
			System.out.println(s);
			if (!(Float.valueOf(strs[1].trim()) == 0.0)) {
				bWriter.write(s);
				bWriter.newLine();
			}
		}
		bReader.close();
		bWriter.close();
	}

	public static void main(String[] args) throws Exception {
		/*
		 * CustomSegmentation api = new CustomSegmentation(); //
		 * api.analyze("file.txt"); String result = api.analyzeGetResult("漂亮");
		 * System.out.println(result);
		 */
		// getText("C:\\Users\\Administrator\\Desktop\\sen3.txt");
		List<String> list = getFromText("C:\\Users\\Administrator\\Desktop\\sysy\\intent.txt");
		//System.out.println(list);
		writeToText(list);
		// readToWrite("C:\\Users\\Administrator\\Desktop\\sysy\\sentiment_userful.txt",
		// "C:\\Users\\Administrator\\Desktop\\sysy\\userful.txt");

	}

}
