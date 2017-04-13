package com.meidiandian.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.meidiandian.util.DateUtils;
import com.meidiandian.util.StringUtils;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	/**
	 * user login
	 * 
	 * @param account
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
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
				session.setAttribute("id", user.getId());

				if (remMe.equals("1")) {

					Cookie cookie = new Cookie("username", account);
					cookie.setMaxAge(24 * 60 * 7);
					response.addCookie(cookie);
				}

				json.put("status", 200);
				json.put("username", user.getName());
				json.put("id", user.getId());

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
	 * 
	 * @param account
	 * @param password
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public String register(
			@RequestParam(value = "account", defaultValue = "") String account,
			@RequestParam(value = "password", defaultValue = "") String password,
			@RequestParam(value = "type", defaultValue = "0") String type,
			HttpServletRequest request, HttpServletResponse response) {

		JSONObject json = new JSONObject();
		if (!StringUtils.isEmpty(account) && !StringUtils.isEmpty(password)) {

			User user = new User();
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

			int curID = userService.findMaxID();
			json.put("id", curID);

			HttpSession session = request.getSession();
			session.setAttribute("id", curID);

			Cookie cookie = new Cookie("username", account);
			cookie.setMaxAge(30 * 60);
			response.addCookie(cookie);

		} else {
			json.put("status", -1);
			json.put("reason", "account or the password can not be null");
		}

		return json.toString();
	}

	/**
	 * 用户退出
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String logout(
			@RequestParam(value = "id", defaultValue = "") String id,
			HttpServletRequest request, HttpServletResponse response) {

		JSONObject json = new JSONObject();

		if (!StringUtils.isEmpty(id)) {
			HttpSession session = request.getSession();
			session.removeAttribute("id");
			json.put("status", 200);
		} else {
			json.put("status", -1);
			json.put("reason", "出现问题，请稍等...");
		}

		return json.toString();
	}

	/**
	 * 查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/userinfo", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getUserInfo(
			@RequestParam(value = "id", defaultValue = "") String id) {

		JSONObject json = new JSONObject();

		if (!StringUtils.isEmpty(id)) {

			int userID = Integer.parseInt(id);
			User user = userService.findUserByID(userID);
			json.put("account", user.getAccount());
			json.put("username", user.getName());
			json.put("createTime", DateUtils.format(user.getCreateTime(),
					"yyyy-MM-dd HH:MM:SS"));
			json.put("address", user.getAddress());
			json.put("type", user.getType());
			json.put("password", user.getPassword());
			json.put("status", 200);
		} else {

			json.put("status", -1);
			json.put("reason", "出现问题，请重试");
		}

		return json.toString();
	}

	/**
	 * 更新用户信息
	 * 
	 * @param id
	 * @param account
	 * @param username
	 * @param address
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/updateuserinfo.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateUserInfo(
			@RequestParam(value = "id", defaultValue = "") String id,
			@RequestParam(value = "account", defaultValue = "") String account,
			@RequestParam(value = "username", defaultValue = "") String username,
			@RequestParam(value = "address", defaultValue = "") String address,
			@RequestParam(value = "password", defaultValue = "") String password) {

		JSONObject json = new JSONObject();

		if (!StringUtils.isEmpty(id)) {

			Map<String, String> map = new HashMap<>();
			map.put("id", id);
			map.put("account", account);
			map.put("username", username);
			map.put("address", address);
			map.put("password", password);
			userService.updateUserInfo(map);

			json.put("status", 200);
		} else {

			json.put("status", -1);
			json.put("reason", "更新失败，请稍后重试");

		}

		return json.toString();
	}

	/**
	 * 判断账号是否存在
	 * 
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "/accountexist.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String accountExist(
			@RequestParam(value = "account", defaultValue = "") String account) {

		JSONObject json = new JSONObject();

		if (!StringUtils.isEmpty(account)) {
			json.put("status", 200);

			User user = userService.findUserByAccount(account);

			if (user == null) {// 不存在
				json.put("exist", 1);
			} else {// 存在
				json.put("exist", 0);
			}
		} else {
			json.put("status", -1);
		}

		return json.toString();
	}

	/**
	 * 判断用户是否是商家
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/isseller.do", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String isSeller(
			@RequestParam(value = "id", defaultValue = "") String id) {

		JSONObject json = new JSONObject();

		if (!StringUtils.isEmpty(id)) {
			json.put("status", 200);

			User user = userService.findUserByID(Integer.parseInt(id));
			if (user.getType() == 0) {
				json.put("isSeller", 0);
			} else {
				json.put("isSeller", 1);
			}

		} else {
			json.put("status", -1);
			json.put("reason", "出现问题，请稍等...");
		}

		return json.toString();
	}
}
