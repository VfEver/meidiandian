package com.meidiandian.controller;

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
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public String login(
			@RequestParam(value = "username", defaultValue = "") String username,
			@RequestParam(value = "password", defaultValue = "") String password,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {

			session.setAttribute("username", username);
			
			Cookie cookie = new Cookie("username", username);
			cookie.setMaxAge(2*60);
			response.addCookie(cookie);
			
			User user = new User();
			user.setId(username);
			user.setPasswrod(password);
			
			JSONObject json = new JSONObject();
			json.put("status", "200");
			json.put("username", username);
			
			return json.toString();
		} else {
			JSONObject json = new JSONObject();
			json.put("status", "500");
			return json.toString();
		}
		
	}
	
}
