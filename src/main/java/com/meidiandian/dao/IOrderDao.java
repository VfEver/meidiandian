package com.meidiandian.dao;

import com.meidiandian.entity.Order;

public interface IOrderDao {
	
	/**
	 * 保存订单信息
	 * @param order
	 */
	public void saveOrder(Order order);
}
