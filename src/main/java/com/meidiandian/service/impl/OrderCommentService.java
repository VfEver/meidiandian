package com.meidiandian.service.impl;

import java.util.List;
import java.util.Map;

import com.meidiandian.dao.IOrderCommentDao;
import com.meidiandian.entity.OrderComment;
import com.meidiandian.service.IOrderCommentService;

public class OrderCommentService implements IOrderCommentService {

	private IOrderCommentDao orderCommentDao;

	public void setOrderCommentDao(IOrderCommentDao orderCommentDao) {
		this.orderCommentDao = orderCommentDao;
	}

	@Override
	public void saveOrderComment(OrderComment orderComment) {

		orderCommentDao.saveOrderComment(orderComment);
	}

	@Override
	public void updateOrderCommentStatus(Map<String, String> map) {

		orderCommentDao.updateOrderCommentStatus(map);
	}

	@Override
	public List<OrderComment> findCommentOrder(int userID) {

		return orderCommentDao.findCommentOrder(userID);
	}

}
