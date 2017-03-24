package com.meidiandian.controller;

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

import com.meidiandian.entity.OrderComment;
import com.meidiandian.service.IOrderCommentService;
import com.meidiandian.util.DateUtils;
import com.meidiandian.util.StringUtils;

/**
 * 订单评论控制层
 * @author zys
 *
 */
@Controller
@RequestMapping("/ordercomment")
public class OrderCommentController {
	
	@Autowired
	private IOrderCommentService orderCommentService;
	
	/**
	 * 保存用户对订单的评价
	 * @param userID
	 * @param username
	 * @param orderID
	 * @param storeName
	 * @param score
	 * @param comment
	 * @return
	 */
	@RequestMapping(value="/saveordercomment", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String saveOrder(
			@RequestParam(value = "userID") String userID,
			@RequestParam(value = "username", defaultValue = "0") String username,
			@RequestParam(value = "orderID", defaultValue = "0") String orderID,
			@RequestParam(value = "score", defaultValue = "0") String score,
			@RequestParam(value = "comment", defaultValue = "0") String comment) {
		
		JSONObject json = new JSONObject();
		
		if (!StringUtils.isEmpty(userID)) {
			
			json.put("status", 200);
			OrderComment orderComment = new OrderComment();
			
			orderComment.setUserName(username);
			orderComment.setOrderID(orderID);
			orderComment.setCommnet(comment);
			orderComment.setScore(Double.parseDouble(score));
			orderComment.setUserID(Integer.parseInt(userID));
			Date date = new Date();
			orderComment.setCommentTime(date);
			//保存订单的评论
			orderCommentService.saveOrderComment(orderComment);
			
			//更新订单的评论状态
			Map<String, String> map = new HashMap<String, String>();
			map.put("orderID", orderID);
			map.put("status", "1");
			orderCommentService.updateOrderCommentStatus(map);

		} else {

			json.put("status", -1);
		}
		
		return json.toString();
	}
	
	/**
	 * 根据用户id查询出用户评论过的订单
	 * @param userID
	 * @return
	 */
	@RequestMapping(value="/findcommentorder", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findCommentOrder(
			@RequestParam(value = "userID") String userID){
		
		JSONObject json = new JSONObject();
		
		if (!StringUtils.isEmpty(userID)) {
			
			json.put("status", 200);
			List<OrderComment> list = orderCommentService.findCommentOrder(Integer.parseInt(userID));
			JSONArray array = new JSONArray();
			
			for (OrderComment orderComment : list) {
				JSONObject temp = new JSONObject();
				temp.put("comment", orderComment.getCommnet());
				temp.put("score", orderComment.getScore());
				Date time = orderComment.getCommentTime();
				temp.put("commentTime", DateUtils.format(time, "yyyy-MM-dd HH:MM:SS"));
				array.add(temp);
			}
			
			json.put("orderDetail", array);
		} else {
			
			json.put("status", -1);
		}
		
		return json.toString();
	}
}
