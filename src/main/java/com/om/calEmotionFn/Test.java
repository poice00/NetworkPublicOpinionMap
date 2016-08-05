package com.om.calEmotionFn;


public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

        String s="http://localhost:8080/SNSAnalyzer/calEmotion.htm";
		java.net.URI uri = new java.net.URI(s);
        java.awt.Desktop.getDesktop().browse(uri);
	}

}
