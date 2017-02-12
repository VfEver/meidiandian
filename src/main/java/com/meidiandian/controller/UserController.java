package com.meidiandian.controller;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meidiandian.entity.User;
import com.meidiandian.service.IUserService;
import com.meidiandian.util.StringUtils;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	/**
	 * user login
	 * @param account
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String login(
			@RequestParam(value = "account", defaultValue = "") String account,
			@RequestParam(value = "password", defaultValue = "") String password,
			@RequestParam(value = "remMe", defaultValue = "0") String remMe,
			HttpServletRequest request, HttpServletResponse response) {
		
		JSONObject json = new JSONObject();
		
		if (!StringUtils.isEmpty(account) && !StringUtils.isEmpty(password)) {
			
			User user = userService.findUser(account, password);
			if (user != null) {
				HttpSession session = request.getSession();
				session.setAttribute("username", account);
				
				if (remMe.equals("1")) {
					
					Cookie cookie = new Cookie("username", account);
					cookie.setMaxAge(24*60*7);
					response.addCookie(cookie);
				}
				
				json.put("status", 200);
				json.put("username", user.getName());
				
			} else {
				json.put("status", -1);
				json.put("reason", "账号或者密码错误，请重试！");
			}

		} else {

			json.put("status", -1);
		}
		
		return json.toString();
	}
	
	/**
	 * user register
	 * @param account
	 * @param password
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/register", method=RequestMethod.POST)
	@ResponseBody
	public String register(
			@RequestParam(value = "account", defaultValue = "") String account,
			@RequestParam(value = "password", defaultValue = "") String password,
			@RequestParam(value = "type", defaultValue = "0") String type,
			HttpServletRequest request, HttpServletResponse response) {

		JSONObject json = new JSONObject();
		if (!StringUtils.isEmpty(account) && !StringUtils.isEmpty(password)) {
			
			User user =  new User();
			user.setAccount(account);
			user.setPassword(password);
			user.setName(account);
			Date date = new Date();
			user.setCreateTime(date);
			user.setType(Integer.parseInt(type));
			user.setAddress("陕西省西安市长安区西沣路西安电子科技大学");
			
			userService.saveUser(user);
			
			json.put("username", account);
			json.put("status", 200);
			
			HttpSession session = request.getSession();
			session.setAttribute("username", account);
			
			Cookie cookie = new Cookie("username", account);
			cookie.setMaxAge(30*60);
			response.addCookie(cookie);
			
		} else {
			json.put("status", -1);
			json.put("reason", "account or the password can not be null");
		}
		
		return json.toString();
	}
	
}
