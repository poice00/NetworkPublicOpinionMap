package com.om.service;

import java.util.List;
import java.util.Map;

import com.om.base.DaoSupport;
import com.om.calEmotion.CalEmotion;
import com.om.domain.Comment;
import com.om.domain.DataSource;

public interface CalEmotionService extends DaoSupport<CalEmotion> {
	public List<Comment> getComments(int begin);

	public Map<String, Double> getMapFromText(String src) throws Exception;

	public double getEmotionValueOneComm(String content,
			Map<String, Double> intenMap, Map<String, Double> posMap,
			List<String> fou) throws Exception;
	public List<DataSource> getDataSources() ;

	public List<Comment> getComments1();
}
