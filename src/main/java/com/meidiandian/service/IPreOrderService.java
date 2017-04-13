package com.meidiandian.service;

import java.util.Map;

import com.meidiandian.entity.PreOrder;

public interface IPreOrderService {
	
	/**
	 * 保存预定订单
	 * @param preOrder
	 */
	public void savePreOrder(PreOrder preOrder);
	
	/**
	 * 查询最大的订单单号id
	 * @return
	 */
	public int findMaxID();
	
	/**
	 * 保存预订订单的详细信息
	 * @param map
	 */
	public void savePreOrderDetail(Map<String, String> map);
}
