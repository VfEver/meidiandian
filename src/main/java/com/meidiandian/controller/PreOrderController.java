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

import com.meidiandian.entity.PreOrder;
import com.meidiandian.service.IPreOrderService;
import com.meidiandian.util.DateUtils;
import com.meidiandian.util.StringUtils;

/**
 * 预订订单控制层
 * @author zys
 *
 */
@Controller
@RequestMapping("/preorder")
public class PreOrderController {
	
	@Autowired
	private IPreOrderService preOrderService;
	
	/**
	 * 保存预订订单到数据库
	 * @param userID
	 * @param username
	 * @param storeID
	 * @param beginTime
	 * @param number
	 * @param totalCost
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/savepreorderdetail", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveOrderDetail(
			@RequestParam(value = "userID") String userID,
			@RequestParam(value = "username", defaultValue = "0") String username,
			@RequestParam(value = "storeID", defaultValue = "0") String storeID,
			@RequestParam(value = "beginTime", defaultValue = "2017-4-12 12:12:12") String beginTime,
			@RequestParam(value = "number", defaultValue = "0") String number,
			@RequestParam(value = "totalCost", defaultValue = "0") String totalCost
			) throws ParseException {
		
		JSONObject json = new JSONObject();
		
		if (!StringUtils.isEmpty(userID)) {
			
			json.put("status", 200);
			PreOrder preOrder = new PreOrder();
			
			preOrder.setUserID(Integer.parseInt(userID));
			preOrder.setStoreID(Integer.parseInt(storeID));
			preOrder.setUsername(username);
			preOrder.setPreCost(Double.parseDouble(totalCost));
			preOrder.setNeedSeats(Integer.parseInt(number));
			preOrder.setBeginTime(DateUtils.formatToDate(beginTime));
			
			Date preOrderTime = new Date();
			preOrder.setPreOrderTime(preOrderTime);
			
			preOrder.setManageStatus(0);
			
			preOrderService.savePreOrder(preOrder);
			
			//查询当前插入的预定订单的id
			int curID = preOrderService.findMaxID();
			
			json.put("curID", curID);
		} else {
			json.put("status", -1);
		}
		
		return json.toString();
	}
	
	/**
	 * 存储当前预定订单商品的数量等信息
	 * @param orderID
	 * @param goodsData
	 * @return
	 */
	@RequestMapping(value = "/savegoodsdetail", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findStore(
			@RequestParam(value = "orderID") String orderID,
			@RequestParam(value = "goodsData") String goodsData) {
		
		JSONObject json = new JSONObject();
		
		if (!StringUtils.isEmpty(orderID)) {
			json.put("status", 200);
			
			Map<String, String> map = new HashMap<String, String>();
			String[] data = goodsData.split(";");
			for (String string : data) {
				map.put("preOrderID", orderID);
				map.put("goodsID", string.split(",")[0]);
				map.put("goodsName", string.split(",")[1]);
				map.put("goodsNumber", string.split(",")[2]);
				preOrderService.savePreOrderDetail(map);
			}
		} else {
			json.put("status", -1);
		}
		
		return json.toString();
	}
	
	/**
	 * 查询出预订订单的详细信息
	 * @param preOrderID
	 * @return
	 */
	@RequestMapping(value = "/findpreorderdetail", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findPreOrderDetail(
			@RequestParam(value = "storeID") String storeID) {
		
		JSONObject json = new JSONObject();
		
		if (!StringUtils.isEmpty(storeID)) {
			
			json.put("status", 200);
			
			List<HashMap<String, String>> detail = 
					preOrderService.findPreOrderDetail(Integer.parseInt(storeID));
			
			JSONArray array = new JSONArray();
			JSONObject temp = null;

			Map<String, JSONArray> orderMap = new HashMap<>();

			for (Map<String, String> map : detail) {

				String orderID = map.get("orderID");
				if (orderMap.containsKey(orderID)) { // 如果orderMap里面存在此订单
					JSONArray arr = orderMap.get(orderID);
					JSONObject j = new JSONObject();
					j.put("goodsName", map.get("goodsName"));
					j.put("goodsNumber", map.get("goodsNumber"));
					j.put("username", map.get("username"));

					String time = String.valueOf(map.get("preOrderTime"));
					j.put("orderTime", time);
					String beginTime = String.valueOf(map.get("beginTime"));
					String endTime = String.valueOf(map.get("endTime"));
					j.put("beginTime", beginTime);
					j.put("endTime", endTime);
					j.put("needSeats", map.get("needSeats"));
					
					arr.add(j);
				} else { // 如果不存在里面
					JSONArray arr = new JSONArray();
					JSONObject j = new JSONObject();
					j.put("goodsName", map.get("goodsName"));
					j.put("goodsNumber", map.get("goodsNumber"));
					j.put("username", map.get("username"));

					String time = String.valueOf(map.get("preOrderTime"));
					j.put("orderTime", time);
					String beginTime = String.valueOf(map.get("beginTime"));
					String endTime = String.valueOf(map.get("endTime"));
					j.put("beginTime", beginTime);
					j.put("endTime", endTime);
					j.put("needSeats", map.get("needSeats"));
					
					arr.add(j);
					
					orderMap.put(orderID, arr);
				}

			}
			
			for (Map.Entry<String, JSONArray> entry : orderMap.entrySet()) {
				temp = new JSONObject();
				temp.put("orderID", entry.getKey());
				temp.put("username",
						((JSONObject) entry.getValue().get(0)).get("username"));
				temp.put("orderTime",
						((JSONObject) entry.getValue().get(0)).get("orderTime"));
				temp.put("orderList", entry.getValue());
				temp.put("needSeats",
						((JSONObject) entry.getValue().get(0)).get("needSeats"));
				temp.put("beginTime",
						((JSONObject) entry.getValue().get(0)).get("beginTime"));
				
				array.add(temp);
			}
			json.put("orderDetail", array);
			
		} else {
			json.put("status", -1);
		}
		
		return json.toString();
	}
}
