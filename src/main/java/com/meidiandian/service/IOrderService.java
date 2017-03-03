package com.meidiandian.service;

import com.meidiandian.entity.Order;

public interface IOrderService {

	/**
	 * 保存订单信息
	 * @param order
	 */
	public void saveOrder(Order order);
}
