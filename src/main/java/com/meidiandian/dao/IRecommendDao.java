package com.meidiandian.dao;

import java.util.List;
import java.util.Map;

public interface IRecommendDao {

	/**
	 * 查询出用户和商品关系
	 * 
	 * @return
	 */
	public List<Map<String, String>> findItemUser();
	
	/**
	 * 查询出该商品的详细信息
	 * @param goodsID
	 * @return
	 */
	public Map<String, String> findGoodsDetail(int goodsID);
	
	/**
	 * 查询出销量第？高的商品的详细信息
	 * @param topID
	 * @return
	 */
	public Map<String, String> findTopGoods(int topID);
}
