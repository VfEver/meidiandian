package com.meidiandian.service;

import java.util.List;
import java.util.Map;

import com.meidiandian.entity.Order;

public interface IOrderService {

	/**
	 * 保存订单信息
	 * 
	 * @param order
	 */
	public void saveOrder(Order order);

	/**
	 * 保存订单中商品的详细信息
	 * 
	 * @param map
	 */
	public void saveOrderDetail(Map<String, String> map);

	/**
	 * 查询订单内的详细信息
	 * 
	 * @param storeID
	 * @return
	 */
	public List<Map<String, String>> findOrderDetail(int storeID);

	/**
	 * 更新订单的配送状态
	 * 
	 * @param map
	 */
	public void updateOrderStatus(Map<String, String> map);

	/**
	 * 查询出用户个人订单信息
	 * 
	 * @param userID
	 * @return
	 */
	public List<Map<String, String>> findUserOrder(int userID);
}
