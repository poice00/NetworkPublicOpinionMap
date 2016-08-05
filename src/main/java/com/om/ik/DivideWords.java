package com.om.ik;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
* 中文分词器
*/

public class DivideWords {
		//判断是不是两个汉字组成的纯汉字
		public static boolean isChinese(char a) { 
		     int v = (int)a; 
		     return (v >=19968 && v <= 171941); 
		}

		public static boolean containsChinese(String s){
			  if (null == s || "".equals(s.trim()) || s.length() <= 1) return false;
			  for (int i = 0; i < s.length(); i++) {
			    if (!isChinese(s.charAt(i))) return false;
			  }
			  return true;
			}

		/**
	    * 对给定的文本进行中文分词
	    * @param text 给定的文本
	    * @param splitToken 用于分割的标记,如"|"
	    * @return 分词完毕的文本
	    */
	    public static List<String> split(String text)
	    {
	        List<String> resultList = new ArrayList<String>();
	        StringReader sr = new StringReader(text);
	        IKSegmenter ik = new IKSegmenter(sr, true);     
	        Lexeme lex=null; 
			try {
				while((lex=ik.next())!=null){  
					String result= lex.getLexemeText();
					if(containsChinese(result))
						resultList.add(result);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	        return resultList;
	    }
}
