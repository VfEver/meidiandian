package com.meidiandian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meidiandian.service.IOrderService;
/**
 * 订单控制层
 * @author zys
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private IOrderService orderService;
	
	/**
	 * 保存订单信息
	 * @param orderID
	 * @param userID
	 * @param username
	 * @param userAddress
	 * @param totalCost
	 * @param payStatus
	 * @return
	 */
	@RequestMapping(value="/saveorder", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String saveOrder(
			@RequestParam(value = "orderID", defaultValue = "0") String orderID,
			@RequestParam(value = "userID", defaultValue = "0") String userID,
			@RequestParam(value = "username", defaultValue = "0") String username,
			@RequestParam(value = "userAddress", defaultValue = "0") String userAddress,
			@RequestParam(value = "totalCost", defaultValue = "0") String totalCost,
			@RequestParam(value = "payStatus", defaultValue = "0") String payStatus) {
		
		
		
		return "";
	}
}
