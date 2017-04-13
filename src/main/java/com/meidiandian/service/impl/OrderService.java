package com.meidiandian.service.impl;

import java.util.List;
import java.util.Map;

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

	@Override
	public void saveOrderDetail(Map<String, String> map) {

		orderDao.saveOrderDetail(map);
	}

	@Override
	public List<Map<String, String>> findOrderDetail(int storeID) {

		return orderDao.findOrderDetail(storeID);
	}

	@Override
	public void updateOrderStatus(Map<String, String> map) {

		orderDao.updateOrderStatus(map);
	}

	@Override
	public List<Map<String, String>> findUserOrder(int userID) {

		return orderDao.findUserOrder(userID);
	}

}
