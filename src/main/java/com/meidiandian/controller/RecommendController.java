package com.meidiandian.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meidiandian.service.IRecommendService;
import com.meidiandian.util.StringUtils;

/**
 * 推荐控制层
 * 
 * @author zys
 *
 */
@Controller
@RequestMapping("/recommend")
public class RecommendController {

	@Autowired
	private IRecommendService recommendService;

	/**
	 * 查询用户推荐商品
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/recommendgoods", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String recommendGoods(
			@RequestParam(value = "id", defaultValue = "18") String id) {

		JSONObject json = new JSONObject();

		if (!StringUtils.isEmpty(id)) {

			json.put("status", 200);

			// 查询出用户和物品之间的具体信息
			List<Map<String, String>> relations = recommendService
					.findItemUser();

			// 存储物品到用户的关系
			Map<String, Set<String>> item_user = new HashMap<>();

			// 存储用户购买过的物品
			Map<String, Set<String>> user_item = new HashMap<>();

			for (Map<String, String> map : relations) {

				String goodsID = String.valueOf(map.get("goodsID"));
				String userID = String.valueOf(map.get("userID"));

				// 存储物品到用户关系表
				if (item_user.containsKey(goodsID)) {

					item_user.get(goodsID).add(userID);
				} else {

					Set<String> set = new HashSet<>();
					set.add(userID);
					item_user.put(goodsID, set);
				}

				// 存储用户到物品信息表
				if (user_item.containsKey(userID)) {

					user_item.get(userID).add(goodsID);
				} else {

					Set<String> set = new HashSet<>();
					set.add(goodsID);
					user_item.put(userID, set);
				}
			}

			// 存储用户买过商品个数
			Map<String, Integer> userNum = new HashMap<>();
			// 存储用户之间的关系矩阵
			Map<String, Integer> userRel = new HashMap<>();

			for (Map.Entry<String, Set<String>> entry : item_user.entrySet()) {
				Set<String> set = entry.getValue();
				for (String string : set) {
					if (userNum.containsKey(string)) {
						userNum.put(string, userNum.get(string) + 1);
					} else {
						userNum.put(string, 1);
					}

					for (String s : set) {
						if (s.equals(string)) {
							continue;
						}

						String oneKey = string + "," + s;
						String anotherKey = s + "," + string;

						if (userRel.containsKey(oneKey)) {
							userRel.put(oneKey, userRel.get(oneKey) + 1);
						} else {
							userRel.put(oneKey, 1);
						}

						if (userRel.containsKey(anotherKey)) {
							userRel.put(anotherKey, userRel.get(anotherKey) + 1);
						} else {
							userRel.put(anotherKey, 1);
						}
					}

				}
			}

			// 保存用户之间的相关度
			Map<String, Double> relationRate = new HashMap<>();
			for (Map.Entry<String, Integer> entry : userRel.entrySet()) {

				String key = entry.getKey();
				int num = entry.getValue();
				String u = key.split(",")[0];
				String v = key.split(",")[1];
				double rate = num / Math.sqrt(userNum.get(u) * userNum.get(v));
				relationRate.put(key, rate);

			}

			// 选取与目标用户相关度最高的N个用户(暂时取N=2)
			String user1 = "";
			double rate1 = 0;
			String user2 = "";
			double rate2 = 0;

			for (Map.Entry<String, Double> entry : relationRate.entrySet()) {

				if (entry.getKey().contains(id)) {

					if (entry.getValue() > rate1) {

						if (!user1.equals(user2)) {
							rate2 = rate1;
							user2 = user1;
						}

						rate1 = entry.getValue();
						String temp = entry.getKey();

						if (id.equals(temp.split(",")[0])) {
							user1 = temp.split(",")[1];
						} else {
							user1 = temp.split(",")[0];
						}

					} else if (entry.getValue() > rate2) {

						if (entry.getKey().contains(user1)) {
							continue;
						}

						rate2 = entry.getValue();
						String temp = entry.getKey();

						if (id.equals(temp.split(",")[0])) {
							user2 = temp.split(",")[1];
						} else {
							user2 = temp.split(",")[0];
						}
					}
				}
			}

			// 存储商品对当前用户的推荐度
			Map<String, Double> recommendRate = new HashMap<String, Double>();
			
			// 找出推荐商品的id,找出M个商品,M=4
			String[] arr = new String[4];
			int index = 0;
			
			if (!StringUtils.isEmpty(user1) && !StringUtils.isEmpty(user2)) {
				
				// 针对user1的行为推荐商品
				for (String itemID : user_item.get(user1)) {
					if (!user_item.get(id).contains(itemID)) {
						recommendRate.put(itemID,
								relationRate.get(id + "," + user1));
					}
				}
				
				// 针对user2的行为进行推荐
				for (String itemID : user_item.get(user2)) {
					if (!user_item.get(id).contains(itemID)) {
						recommendRate.put(itemID,
								relationRate.get(id + "," + user2));
					}
				}
				
				for (Map.Entry<String, Double> entry : recommendRate.entrySet()) {
					arr[index++] = entry.getKey();
					if (index == 3) {
						break;
					}
				}
			}
			
			JSONArray array = new JSONArray();
			JSONObject temp = null;
			int j = 0;

			for (String goodsID : arr) {
				
				if (StringUtils.isEmpty(goodsID)) {

					Map<String, String> goodsDetail = recommendService.findTopGoods(j);
					temp = new JSONObject();
					temp.put("goodsID", goodsDetail.get("goodsID"));
					temp.put("goodsName", goodsDetail.get("goodsName"));
					temp.put("goodsPrice", goodsDetail.get("goodsPrice"));
					temp.put("goodsImage", goodsDetail.get("goodsImage"));
					temp.put("soldNumber", goodsDetail.get("soldNumber"));
					temp.put("storeID", goodsDetail.get("storeID"));
					temp.put("storeName", goodsDetail.get("storeName"));
					array.add(temp);
					++j;
				} else {
					
					Map<String, String> goodsDetail = recommendService.findGoodsDetail(Integer.parseInt(goodsID));
					temp = new JSONObject();
					temp.put("goodsID", goodsDetail.get("goodsID"));
					temp.put("goodsName", goodsDetail.get("goodsName"));
					temp.put("goodsPrice", goodsDetail.get("goodsPrice"));
					temp.put("goodsImage", goodsDetail.get("goodsImage"));
					temp.put("soldNumber", goodsDetail.get("soldNumber"));
					temp.put("storeID", goodsDetail.get("storeID"));
					temp.put("storeName", goodsDetail.get("storeName"));
					array.add(temp);
				}
			}
			
			json.put("itemsList", array);

		} else {

			json.put("status", -1);
		}

		return json.toString();
	}

}
