package com.meidiandian.service;

import java.util.List;
import java.util.Map;

import com.meidiandian.entity.Goods;

/**
 * 商品service接口
 * 
 * @author zys
 *
 */
public interface IGoodsService {

	/**
	 * 根据店铺id查询其中的
	 * 
	 * @param id
	 * @return
	 */
	public List<Goods> findGoodsByStoreID(int storeID);

	/**
	 * 查询id商品的相信信息
	 * 
	 * @param id
	 * @return
	 */
	public Goods findGoodsByID(int id);

	/**
	 * 更新商品信息
	 * 
	 * @param map
	 */
	public void updateGoodsByID(Map<String, String> map);

	/**
	 * 根据id删除相应商品
	 * 
	 * @param goodsID
	 */
	public void deleteGoodsByID(int goodsID);

	/**
	 * 添加新的商品
	 * 
	 * @param goods
	 */
	public void saveGoods(Goods goods);

	/**
	 * 订单完成后更新商品卖出数量
	 * 
	 * @param map
	 */
	public void addGoodsNum(Map<String, Integer> map);
}
