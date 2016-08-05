package com.om.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.lucene.util.fst.PositiveIntOutputs;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hankcs.hanlp.dependency.nnparser.util.math;
import com.om.domain.Comment;
import com.om.domain.EmotionAnalysis;
import com.om.domain.EventSpread;
import com.om.domain.WriterFactor;
import com.om.service.CalEmotionService;

@RequestMapping("/emotion")
@Controller
public class EmotionController {

	@Resource
	CalEmotionService calEmotionService;

	@SuppressWarnings("unchecked")
	@RequestMapping("/calEmotion")
	public String calEmotion(Model model) {
		List<EmotionAnalysis> eaList = new ArrayList<>();
		List<WriterFactor> list = calEmotionService.getSession().createCriteria(WriterFactor.class).setMaxResults(300)
				.list();
		List<Comment> list2 = new ArrayList<>();
		for (Iterator<WriterFactor> iterator = list.iterator(); iterator.hasNext();) {
			WriterFactor writerFactor = (WriterFactor) iterator.next();
			list2 = calEmotionService.getSession().createCriteria(Comment.class).createAlias("writerByWriterId", "w")
					.add(Restrictions.eq("w.id", writerFactor.getWriterFactorId())).list();
			if (list2.size() > 0) {
				eaList.add(new EmotionAnalysis(writerFactor, list2));
			}
		}
		model.addAttribute("eaList", eaList);
		return "/home/calemotion";

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/caution")
	public String caution(Model model) {
		List<Double> positive = new ArrayList<>();
		List<Double> normal = new ArrayList<>();
		List<Double> negative = new ArrayList<>();
		SQLQuery query = calEmotionService.getSession().createSQLQuery(
				"SELECT SUM(positive_num),SUM(normal_num),SUM(negtive_num) from event_spread WHERE spread_date<? AND spread_date>=?;");
		List list = new ArrayList<>();
		Object[] next;
		for (int i = 1; i <= 11; i++) {
			list = query.setString(0, "2014-0" + (i + 1) + "-01").setString(1, "2014-0" + i + "-01").list();
			next = (Object[]) list.iterator().next();
			positive.add((Double) next[0]);
			normal.add((Double) next[1]);
			negative.add((Double) next[2]);
		}
		list = query.setString(0, "2015-01-01").setString(1, "2014-12-01").list();
		next = (Object[]) list.iterator().next();
		positive.add((Double) next[0]);
		normal.add((Double) next[1]);
		negative.add((Double) next[2]);
		for (int i = 1; i <= 2; i++) {
			list = query.setString(0, "2015-0" + (i + 1) + "-01").setString(1, "2015-0" + i + "-01").list();
			next = (Object[]) list.iterator().next();
			positive.add((Double) next[0]);
			normal.add((Double) next[1]);
			negative.add((Double) next[2]);
		}
		model.addAttribute("positive", positive);
		model.addAttribute("normal", normal);
		model.addAttribute("negative", negative);
		return "/home/caution";

	}
}
