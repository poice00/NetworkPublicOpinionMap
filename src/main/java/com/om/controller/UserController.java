package com.om.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.om.domain.User;
import com.om.service.UserService;


@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/user")
public class UserController  {
	
	/** 列表 */
	@RequestMapping("/list")
	public String list(Model model) throws Exception {
		return "/user/list";
	}

	/** 删除 */
	@RequestMapping(value = "/delete")
	public String delete(Long id) throws Exception {
//		System.out.println("=================" + id);
//		userService.delete(id);
		return "redirect:/user/list";
	}

	/** 添加页面 */
	@RequestMapping("/addUI")
	public String addUI(Model model) throws Exception {
		return "/user/addUI";
	}

	/** 添加 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(User user,Long roleId) throws Exception {
//		this.userService.save(user);
		return "redirect:list";	
	}

	/** 修改页面 */
	@RequestMapping("/editUI")
	public String editUI(Long id,Model model) throws Exception {
//		User user = userService.getById(id);
//		model.addAttribute("user", user);
		return "/user/editUI";
	}

	/** 修改 */
	@RequestMapping(value = "/edit")
	public String edit(Long id,User user,Long roleId) throws Exception {
//		User u = userService.getById(id);
//		u.setName(user.getName());
//		u.setDescription(user.getDescription());
//		userService.update(u);
		return "redirect:/user/list";
	}	
	
}



