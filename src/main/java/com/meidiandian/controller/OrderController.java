package com.meidiandian.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meidiandian.entity.Order;
import com.meidiandian.service.IOrderService;
import com.meidiandian.util.StringUtils;
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
			@RequestParam(value = "userID") String userID,
			@RequestParam(value = "username", defaultValue = "0") String username,
			@RequestParam(value = "userAddress", defaultValue = "0") String userAddress,
			@RequestParam(value = "totalCost", defaultValue = "0") String totalCost,
			@RequestParam(value = "storeID", defaultValue = "0") String storeID,
			@RequestParam(value = "storeName", defaultValue = "0") String storeName) {
		
		JSONObject json = new JSONObject();
		
		if (!StringUtils.isEmpty(userID)) {
			json.put("status", 200);
			
			Order order = new Order();
			Date date = new Date();
			String orderID = String.valueOf(date.getTime());
			
			order.setOrderID(orderID);
			order.setOrderTime(date);
			order.setPayStatus(1);
			order.setTotalCost(Double.parseDouble(totalCost));
			order.setUserAddress(userAddress);
			order.setUsername(username);
			order.setStoreID(Integer.parseInt(storeID));
			order.setStoreName(storeName);
			order.setUserID(Integer.parseInt(userID));
			
			orderService.saveOrder(order);
			
			json.put("orderID", orderID);
			json.put("orderID", orderID);
		} else {
			json.put("status", -1);
		}
		
		return json.toString();
	}
	
	/**
	 * 保存订单的详细信息
	 * @param userID
	 * @return
	 */
	@RequestMapping(value="/saveorderdetail", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String saveOrderDetail(
			@RequestParam(value = "orderID") String orderID,
			@RequestParam(value = "goodsData") String goodsData) {
		
		JSONObject json = new JSONObject();
		
		if (!StringUtils.isEmpty(orderID)) {
			
			json.put("status", 200);
			Map<String, String> map = new HashMap<String, String>();
			String[] data = goodsData.split(";");
			for (String string : data) {
				map.put("orderID", orderID);
				map.put("goodsID", string.split(",")[0]);
				map.put("goodsName", string.split(",")[1]);
				map.put("goodsNumber", string.split(",")[2]);
				orderService.saveOrderDetail(map);
			}
			
		} else {
			json.put("status", -1);
		}
		
		return json.toString();
	}
	
	/**
	 * 查询出本店铺的相信订单信息
	 * @param storeID
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/findorderdetail", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findOrderDetail(
			@RequestParam(value = "storeID") String storeID) throws ParseException {
		
		JSONObject json = new JSONObject();
		
		if (!StringUtils.isEmpty(storeID)) {
			
			json.put("status", 200);
			List<Map<String, String>> orderList = orderService.findOrderDetail(Integer.parseInt(storeID));
			
			JSONArray array = new JSONArray();
			JSONObject temp = null;
			
			Map<String, JSONArray> orderMap = new HashMap<>();
			
			for (Map<String, String> map : orderList) {
				
				String orderID = map.get("orderID");
				if (orderMap.containsKey(orderID)) {   //如果orderMap里面存在此订单
					JSONArray arr = orderMap.get(orderID);
					JSONObject j = new JSONObject();
					j.put("goodsName", map.get("goodsName"));
					j.put("goodsNumber", map.get("goodsNumber"));
					j.put("username", map.get("username"));
					j.put("userAddress", map.get("userAddress"));
					
					String time = String.valueOf(map.get("orderTime"));
					j.put("orderTime", time);
					arr.add(j);
				} else {  //如果不存在里面
					JSONArray arr = new JSONArray();
					JSONObject j = new JSONObject();
					j.put("goodsName", map.get("goodsName"));
					j.put("goodsNumber", map.get("goodsNumber"));
					j.put("username", map.get("username"));
					j.put("userAddress", map.get("userAddress"));

					String time = String.valueOf(map.get("orderTime"));
					j.put("orderTime", time);
					arr.add(j);
					
					orderMap.put(orderID, arr);
				}
			
			}
			
			for (Map.Entry<String, JSONArray> entry : orderMap.entrySet()) {
				temp = new JSONObject();
				temp.put("orderID", entry.getKey());
				temp.put("username", ((JSONObject)entry.getValue().get(0)).get("username"));
				temp.put("userAddress", ((JSONObject)entry.getValue().get(0)).get("userAddress"));
				temp.put("orderTime", ((JSONObject)entry.getValue().get(0)).get("orderTime"));
				temp.put("orderList", entry.getValue());
				array.add(temp);
			}
			json.put("orderDetail", array);
			
			
		} else {
			json.put("status", -1);
		}
		
		return json.toString();
	}
	
	/**
	 * 更新订单送达状态
	 * @param orderID
	 * @return
	 */
	@RequestMapping(value="/updateorderstatus", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updateOrderStatus(
			@RequestParam(value = "orderID") String orderID) {
		
		JSONObject json = new JSONObject();
		
		if (!StringUtils.isEmpty(orderID)) {
			
			json.put("status", 200);
			Map<String, String> map  = new HashMap<>();
			map.put("orderID", orderID);
			map.put("status", "1");
			orderService.updateOrderStatus(map);
			
		} else {
			
			json.put("status", -1);
		}
		
		return json.toString();
	}
	
	/**
	 * 查询出用户的订单信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/userorder", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findUserOrder(
			@RequestParam(value = "id") String id) {
		
		JSONObject json = new JSONObject();
		
		if (!StringUtils.isEmpty(id)) {
			
			json.put("status", 200);
			
			int userID = Integer.parseInt(id);
			List<Map<String, String>> orderList = orderService.findUserOrder(userID);
			
			JSONArray array = new JSONArray();
			JSONObject temp = null;
			
			Map<String, JSONArray> orderMap = new HashMap<>();
			
			for (Map<String, String> map : orderList) {
				
				String orderID = map.get("orderID");
				if (orderMap.containsKey(orderID)) {   //如果orderMap里面存在此订单
					JSONArray arr = orderMap.get(orderID);
					JSONObject j = new JSONObject();
					j.put("goodsName", map.get("goodsName"));
					j.put("goodsNumber", map.get("goodsNumber"));
					j.put("username", map.get("username"));
					j.put("userAddress", map.get("userAddress"));
					
					String time = String.valueOf(map.get("orderTime"));
					j.put("orderTime", time);
					arr.add(j);
				} else {  //如果不存在里面
					JSONArray arr = new JSONArray();
					JSONObject j = new JSONObject();
					j.put("goodsName", map.get("goodsName"));
					j.put("goodsNumber", map.get("goodsNumber"));
					j.put("username", map.get("username"));
					j.put("userAddress", map.get("userAddress"));

					String time = String.valueOf(map.get("orderTime"));
					j.put("orderTime", time);
					arr.add(j);
					
					orderMap.put(orderID, arr);
				}
			
			}
			
			for (Map.Entry<String, JSONArray> entry : orderMap.entrySet()) {
				temp = new JSONObject();
				temp.put("orderID", entry.getKey());
				temp.put("username", ((JSONObject)entry.getValue().get(0)).get("username"));
				temp.put("userAddress", ((JSONObject)entry.getValue().get(0)).get("userAddress"));
				temp.put("orderTime", ((JSONObject)entry.getValue().get(0)).get("orderTime"));
				temp.put("orderList", entry.getValue());
				array.add(temp);
			}
			json.put("orderDetail", array);
			
			
		} else {
			json.put("status", -1);
		}
		
		return json.toString();
	}
	
	
}
