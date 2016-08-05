package com.om.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.om.domain.DataSource;
import com.om.domain.User;
import com.om.domain.WriterFactor;
import com.om.service.DataSourceNoTagService;
import com.om.service.UserService;
import com.om.service.WriterFactorService;


@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/leader")
public class LeaderController  {
	
	@Resource
	private WriterFactorService writerFactorService;
	
	/** 影响力 */
	@RequestMapping("/influence")
	public String influence(Model model) throws Exception {
		List<WriterFactor> resultList = writerFactorService.findByInfluence(10);
		model.addAttribute("resultList", resultList);
		return "/opinionleader/influence";
	}
	/** 活跃度 */
	@RequestMapping("/activedegree")
	public String activedegree(Model model) throws Exception {
		List<WriterFactor> resultList = writerFactorService.findByActiveDegree(10);
		model.addAttribute("resultList", resultList);
		return "/opinionleader/activedegree";
	}
	/** 发布者价值*/
	@RequestMapping("/influence2")
	public String influence2(Model model) throws Exception {
		List<WriterFactor> resultList = writerFactorService.findByValue(10);
		model.addAttribute("resultList", resultList);
		return "/opinionleader/influence2";
	}
	/** 意见领袖排行分析*/
	@RequestMapping("/rank")
	public String rank(Model model,HttpServletRequest request) throws Exception {
		String detailStr = getTByPath(request.getSession().getServletContext().getRealPath("/") + "data/rank.txt");
		System.out.println(detailStr);
		System.out.println(detailStr.split(":")[0]);
		System.out.println(detailStr.split(":")[1]);
		model.addAttribute("word", detailStr.split(":")[0]);
		model.addAttribute("fre", detailStr.split(":")[1]);
		return "/opinionleader/rank";
	}
	private String getTByPath(String path) {
		String str  = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(path)));
			String in;
			while((in=br.readLine())!=null){
				str += in;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(str);
		return str;
	}

}



