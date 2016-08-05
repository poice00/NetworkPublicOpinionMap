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
import com.om.domain.Theme;
import com.om.domain.User;
import com.om.service.DataSourceNoTagService;
import com.om.service.UserService;
import com.om.util.DataSearch;


@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/theme")
public class ThemeController  {
	
	@Resource
	private DataSourceNoTagService dataSourceNoTagService;
	
	/** 分类 */
	@RequestMapping("/tophot")
	public String list(Model model,HttpServletRequest request) throws Exception {
		DataSearch dataSearch=new DataSearch();
		List<Theme> themes=dataSearch.searchthemeHot();
		model.addAttribute("themes", themes);
		return "/home/tophot";
	}
	/** 分类 */
	@RequestMapping("/guandian")
	public String guandian(Model model,HttpServletRequest request) throws Exception {
		return "/home/guandian";
	}
}



