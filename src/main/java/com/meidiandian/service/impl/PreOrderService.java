package com.meidiandian.service.impl;

import java.util.Map;

import com.meidiandian.dao.IPreOrderDao;
import com.meidiandian.entity.PreOrder;
import com.meidiandian.service.IPreOrderService;

public class PreOrderService implements IPreOrderService {

	private IPreOrderDao preOrderDao;
	public void setPreOrderDao(IPreOrderDao preOrderDao) {
		this.preOrderDao = preOrderDao;
	}
	
	@Override
	public void savePreOrder(PreOrder preOrder) {
		
		preOrderDao.savePreOrder(preOrder);
	}

	@Override
	public int findMaxID() {

		return preOrderDao.findMaxID();
	}

	@Override
	public void savePreOrderDetail(Map<String, String> map) {

		preOrderDao.savePreOrderDetail(map);
	}

}
