package com.meidiandian.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Decoder;

import com.meidiandian.entity.Store;
import com.meidiandian.service.IStoreService;
import com.meidiandian.util.StringUtils;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/store")
public class StoreController {

	@Autowired
	private IStoreService storeService;

	/**
	 * 根据用户id查询出当前用户的店铺信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/storeinfo", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
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
				json.put("beginPost", store.getBeginPost());
				json.put("postTime", store.getPostTime());
				json.put("storeNotice", store.getStoreNotice());
				json.put("storeImage", store.getStoreImage());
			}

		} else {
			json.put("status", -1);
			json.put("reason", "出现问题，请重试");
		}

		return json.toString();
	}

	/**
	 * 根据店铺id查询店铺信息
	 * 
	 * @param storeID
	 * @return
	 */
	@RequestMapping(value = "/storemsg", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findStoreByStoreID(
			@RequestParam(value = "storeID", defaultValue = "0") String storeID) {

		JSONObject json = new JSONObject();

		if (!StringUtils.isEmpty(storeID)) {

			int storeIDIng = Integer.parseInt(storeID);
			Store store = storeService.selectStoreByID(storeIDIng);

			if (store != null) {
				json.put("status", 200);
				json.put("store", store);
			} else {
				json.put("status", -1);
				json.put("reason", "店铺不存在");
			}

		} else {
			json.put("status", -1);
			json.put("reason", "出现问题，请重试");
		}

		return json.toString();
	}

	/**
	 * 保存新创建的店铺信息
	 * 
	 * @param id
	 * @param storeName
	 * @param storeAddress
	 * @param storeHours
	 * @param cost
	 * @return
	 */
	@RequestMapping(value = "/savestore", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveStore(
			@RequestParam(value = "id", defaultValue = "") String id,
			@RequestParam(value = "storeName", defaultValue = "") String storeName,
			@RequestParam(value = "storeAddress", defaultValue = "") String storeAddress,
			@RequestParam(value = "storeHours", defaultValue = "") String storeHours,
			@RequestParam(value = "cost", defaultValue = "0") String cost,
			@RequestParam(value = "beginPost", defaultValue = "0") String beginPost,
			@RequestParam(value = "postTime", defaultValue = "0") String postTime) {

		JSONObject json = new JSONObject();
		if (!StringUtils.isEmpty(id)) {

			Store store = new Store();

			store.setCost(Double.parseDouble(cost));
			store.setUserID(Integer.parseInt(id));

			if (StringUtils.isEmpty(storeName)) {
				storeName = "国记豆腐脑";
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

			store.setBeginPost(Integer.parseInt(beginPost));
			store.setPostTime(Integer.parseInt(postTime));

			storeService.saveStore(store);
			json.put("status", 200);

		} else {
			json.put("status", -1);
			json.put("reason", "创建店铺失败，请重试！");
		}

		return json.toString();
	}

	/**
	 * 更新店铺信息
	 * 
	 * @param id
	 * @param userID
	 * @param storeName
	 * @param storeAddress
	 * @param storeHours
	 * @param cost
	 * @return
	 */
	@RequestMapping(value = "/updatestore", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateStore(
			@RequestParam(value = "id", defaultValue = "") String id,
			@RequestParam(value = "userID", defaultValue = "") String userID,
			@RequestParam(value = "storeName", defaultValue = "") String storeName,
			@RequestParam(value = "storeAddress", defaultValue = "") String storeAddress,
			@RequestParam(value = "storeHours", defaultValue = "") String storeHours,
			@RequestParam(value = "cost", defaultValue = "0") String cost,
			@RequestParam(value = "beginPost", defaultValue = "0") String beginPost,
			@RequestParam(value = "postTime", defaultValue = "0") String postTime,
			@RequestParam(value = "storeNotice", defaultValue = "0") String storeNotice) {

		JSONObject json = new JSONObject();

		if (!StringUtils.isEmpty(id)) {

			json.put("status", 200);
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("userID", userID);
			map.put("storeName", storeName);
			map.put("storeAddress", storeAddress);
			map.put("storeHours", storeHours);
			map.put("cost", cost);
			map.put("beginPost", beginPost);
			map.put("postTime", postTime);
			map.put("storeNotice", storeNotice);

			storeService.updateStore(map);

		} else {
			json.put("status", -1);
			json.put("reason", "创建店铺失败，请重试！");
		}

		return json.toString();
	}

	/**
	 * 更新店铺图片
	 * 
	 * @param id
	 * @param img
	 * @return
	 */
	@RequestMapping(value = "/storeimg", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateStore(
			@RequestParam(value = "id", defaultValue = "") String id,
			@RequestParam(value = "img", defaultValue = "") String img,
			HttpServletRequest request) {

		JSONObject json = new JSONObject();

		if (!StringUtils.isEmpty(id)) {
			json.put("status", 200);
			String suffix = img.split(",")[0].split("/")[1].split(";")[0];
			String fileName = null;
			String imageIO = img.split(",")[1];
			if (imageIO != null) {
				BASE64Decoder decoder = new BASE64Decoder();
				try {
					// Base64解码
					byte[] b = decoder.decodeBuffer(imageIO);
					for (int i = 0; i < b.length; ++i) {
						if (b[i] < 0) {// 调整异常数据
							b[i] += 256;
						}
					}
					String fatherPath = request.getSession()
							.getServletContext().getRealPath("")
							+ "storeImgs";// tomcat下
					File f = new File(fatherPath);
					fileName = System.currentTimeMillis() + "." + suffix;
					File file = new File(f, fileName);
					if (!file.exists()) {
						file.createNewFile();
					}
					OutputStream os = new FileOutputStream(file);
					os.write(b);
					os.flush();
					os.close();
					f = new File(
							"D:\\MyEclipse\\meidiandian\\src\\main\\webapp\\storeImgs");
					if (!f.exists()) {
						f.mkdir();
					}
					file = new File(f, fileName);
					if (!file.exists()) {
						file.createNewFile();
					}
					os = new FileOutputStream(file);
					os.write(b);
					os.flush();
					os.close();
					fileName = "storeImgs/" + fileName;

					Map<String, String> map = new HashMap<>();
					map.put("id", id);
					map.put("imgURL", fileName);

					storeService.updateStoreImg(map);
				} catch (Exception e) {
					fileName = "storeImgs/beerduck.jpg";
					e.printStackTrace();
				}
			} else {
				fileName = "storeImgs/beerduck.jpg";
			}
			json.put("imgURL", fileName);

		} else {
			json.put("status", -1);
		}

		return json.toString();
	}

	/**
	 * 根据城市查询店铺信息
	 * 
	 * @param city
	 * @return
	 */
	@RequestMapping(value = "/findstore", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findStoreByCity(
			@RequestParam(value = "city", defaultValue = "聊城") String city) {

		JSONObject json = new JSONObject();

		if (!StringUtils.isEmpty(city)) {

			json.put("status", 200);
			city = "%" + city + "%";
			List<Store> storeList = storeService.findStoreByCity(city);
			json.put("storeList", storeList);

		} else {
			json.put("status", -1);
			json.put("reason", "出现问题，请重试");
		}

		return json.toString();
	}

	/**
	 * 判断是否是自己的店铺，是的话禁止购买
	 * 
	 * @param id
	 * @param storeID
	 * @return
	 */
	@RequestMapping(value = "/buyself", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String buySelf(
			@RequestParam(value = "id", defaultValue = "0") String id,
			@RequestParam(value = "storeID", defaultValue = "0") String storeID) {

		JSONObject json = new JSONObject();

		if (!StringUtils.isEmpty(id)) {

			json.put("status", 200);
			Store store = storeService.selectStoreByID(Integer
					.parseInt(storeID));
			if (store.getUserID() == Integer.parseInt(id)) {
				json.put("can", 0);
			} else {
				json.put("can", 1);
			}
		} else {
			json.put("status", -1);
		}

		return json.toString();
	}
}
