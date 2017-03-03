package com.meidiandian.service.impl;

import com.meidiandian.dao.IOrderDao;
import com.meidiandian.entity.Order;
import com.meidiandian.service.IOrderService;

public class OrderService implements IOrderService {

	private IOrderDao orderDao;
	public void setOrderDao(IOrderDao orderDao) {
		this.orderDao = orderDao;
	}
	@Override
	public void saveOrder(Order order) {

		orderDao.saveOrder(order);
	}

}
