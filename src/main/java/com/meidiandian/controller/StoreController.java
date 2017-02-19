package com.meidiandian.controller;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meidiandian.entity.Store;
import com.meidiandian.service.IStoreService;
import com.meidiandian.util.StringUtils;

@Controller
@RequestMapping("/store")
public class StoreController {
	
	@Autowired
	private IStoreService storeService;
	
	/**
	 * 根据用户id查询出当前用户的店铺信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/storeinfo", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findStore(
			@RequestParam(value = "id", defaultValue = "0") String id) {
		
		JSONObject json = new JSONObject();
		
		if (!StringUtils.isEmpty(id)) {
			
			int userID = Integer.parseInt(id);
			Store store = storeService.selectStoreByUserID(userID);
			
			json.put("status", 200);
			if (store == null) {
				json.put("hasStore", "no");
			} else {
				json.put("hasStore", "yes");
				json.put("storeID", store.getId());
				json.put("storeName", store.getStoreName());
				json.put("storeHours", store.getStoreHours());
				json.put("storeAddress", store.getStoreAddress());
				json.put("cost", store.getCost());
			}
			
		} else {
			json.put("status", -1);
			json.put("reason", "出现问题，请重试");
		}
		
		return json.toString();
	}
	
	/**
	 * 保存新创建的店铺信息
	 * @param id
	 * @param storeName
	 * @param storeAddress
	 * @param storeHours
	 * @param cost
	 * @return
	 */
	@RequestMapping(value="/savestore", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String saveStore(
			@RequestParam(value = "id", defaultValue = "") String id,
			@RequestParam(value = "storeName", defaultValue = "") String storeName,
			@RequestParam(value = "storeAddress", defaultValue = "") String storeAddress,
			@RequestParam(value = "storeHours", defaultValue = "") String storeHours,
			@RequestParam(value = "cost", defaultValue = "0") String cost
			) {
		
		JSONObject json = new JSONObject();
		if (!StringUtils.isEmpty(id)) {
			
			Store store = new Store();
			
			store.setCost(Double.parseDouble(cost));
			store.setUserID(Integer.parseInt(id));
			
			if (StringUtils.isEmpty(storeName)) {
				storeName="国记豆腐脑";
			}
			store.setStoreName(storeName);
			
			if (StringUtils.isEmpty(storeAddress)) {
				storeAddress = "陕西省西安市长安区西沣路西安电子科技大学";
			}
			store.setStoreAddress(storeAddress);
			
			if (StringUtils.isEmpty(storeHours)) {
				storeHours = "0-24";
			}
			store.setStoreHours(storeHours);
			
			storeService.saveStore(store);
			json.put("status", 200);
			
		} else {
			json.put("status", -1);
			json.put("reason", "创建店铺失败，请重试！");
		}
		
		return json.toString();
	}
}
