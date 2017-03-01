package com.meidiandian.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Decoder;

import com.meidiandian.entity.Goods;
import com.meidiandian.service.IGoodsService;
import com.meidiandian.util.StringUtils;

/**
 * 商品控制层
 * @author zys
 *
 */
@SuppressWarnings("restriction")
@Controller
@RequestMapping("/goods")
public class GoodsController {
	
	@Autowired
	private IGoodsService goodsService;
	
	/**
	 * 查询出店铺内的商品并返回
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/goodsdetail", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findStore(
			@RequestParam(value = "id", defaultValue = "0") String id) {
		
		JSONObject json = new JSONObject();
		
		if (!StringUtils.isEmpty(id)) {
			
			json.put("status", 200);
			int storeID = Integer.parseInt(id);
			List<Goods> goodsList = goodsService.findGoodsByStoreID(storeID);
			
			JSONArray array = new JSONArray();
			
			JSONObject temp;
			for (Goods goods : goodsList) {
				temp = new JSONObject();
				temp.put("goodsID", goods.getId());
				temp.put("price", goods.getGoodsPrice());
				temp.put("image", goods.getGoodsImage());
				temp.put("name", goods.getGoodsName());
				array.add(temp);
			}
			
			json.put("goodsList", array);
		} else {
			json.put("status", -1);
			json.put("reason", "出现问题，请重试");
		}
		
		return json.toString();
	}
	
	/**
	 * 查看某件商品的详细信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/goodsinfo", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String goodsinfo(
			@RequestParam(value = "id", defaultValue = "0") String id) {
		
		JSONObject json = new JSONObject();
		
		if (!StringUtils.isEmpty(id)) {
			json.put("status", 200);

			int goodsID = Integer.parseInt(id);
			Goods goods = goodsService.findGoodsByID(goodsID);
			
			json.put("goodsID", goods.getId());
			json.put("goodsName", goods.getGoodsName());
			json.put("goodsPrice", goods.getGoodsPrice());
			json.put("goodsImage", goods.getGoodsImage());
			
		} else {
			json.put("status", -1);
		}
		
		return json.toString();
	}
	
	/**
	 * 更新商品信息
	 * @param id
	 * @param goodsPrice
	 * @param goodsName
	 * @return
	 */
	@RequestMapping(value="/updategoodsinfo", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updategoodsinfo(
			@RequestParam(value = "id", defaultValue = "0") String id,
			@RequestParam(value = "goodsPrice", defaultValue = "0") String goodsPrice,
			@RequestParam(value = "goodsName", defaultValue = "0") String goodsName) {
		
		JSONObject json = new JSONObject();
		
		if (!StringUtils.isEmpty(id)) {
			
			json.put("status", 200);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("goodsID", id);
			map.put("goodsPrice", goodsPrice);
			map.put("goodsName", goodsName);
			
			goodsService.updateGoodsByID(map);
			
		} else {
			json.put("status", -1);
		}
		
		return json.toString();
	}
	
	/**
	 * 删除特定商品
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/deletegoods", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteGoods(
			@RequestParam(value = "id", defaultValue = "0") String id) {
		
		JSONObject json = new JSONObject();
		
		if (!StringUtils.isEmpty(id)) {
			json.put("status", 200);
			
			int goodsID = Integer.parseInt(id);
			goodsService.deleteGoodsByID(goodsID);
		} else {
			json.put("stauts", -1);
		}
		
		return json.toString();
	}
	
	/**
	 * 添加商品
	 * @param storeID
	 * @param goodsName
	 * @param goodsPrice
	 * @param goodsImage
	 * @return
	 */
	@RequestMapping(value="/addgoods", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addGoods(
			@RequestParam(value = "storeID", defaultValue = "") String storeID,
			@RequestParam(value = "goodsName", defaultValue = "") String goodsName,
			@RequestParam(value = "goodsPrice", defaultValue = "0") String goodsPrice,
			@RequestParam(value = "goodsImage", defaultValue = "") String goodsImage) {
		
		JSONObject json = new JSONObject();
		
		if (!StringUtils.isEmpty(storeID)) {
			json.put("status", 200);
			
			Goods goods = new Goods();
			goods.setGoodsImage(goodsImage);
			goods.setGoodsName(goodsName);
			goods.setGoodsPrice(goodsPrice);
			goods.setStoreID(Integer.parseInt(storeID));
			
			goodsService.saveGoods(goods);
		} else {
			json.put("status", -1);
			json.put("reason", "出现问题，请重试");
		}
		
		return json.toString();
	}
	
	/**
	 * 获取图片上传的
	 * @param img
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/goodsimg", method=RequestMethod.POST, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getGoodsImgUrl(
			@RequestParam(value = "img", defaultValue = "") String img,
			HttpServletRequest request) {
		
		JSONObject json = new JSONObject();
		String fileName = null;
		
		if (!StringUtils.isEmpty(img)) {
			json.put("status", 200);
			String suffix = img.split(",")[0].split("/")[1].split(";")[0];
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
					String fatherPath = request.getSession().getServletContext().getRealPath("") + "goodsImgs";//tomcat下
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
					f = new File("D:\\MyEclipse\\meidiandian\\src\\main\\webapp\\goodsImgs");
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
					fileName = "goodsImgs/" + fileName;
					
				} catch (Exception e) {
					fileName = "goodsImgs/1.jpg";
					e.printStackTrace();
				}
			json.put("imgUrl", fileName);
			
			}
		} else {
			json.put("status", -1);
			json.put("reason", "网络问题，请重试");
		}
		return json.toString();
	}
}
