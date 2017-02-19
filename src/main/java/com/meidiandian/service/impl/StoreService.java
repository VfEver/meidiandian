package com.meidiandian.service.impl;

import com.meidiandian.dao.IStoreDao;
import com.meidiandian.entity.Store;
import com.meidiandian.service.IStoreService;

public class StoreService implements IStoreService{

	private IStoreDao storeDao;
	public void setStoreDao(IStoreDao storeDao) {
		this.storeDao = storeDao;
	}
	
	@Override
	public Store selectStoreByUserID(int id) {

		return storeDao.selectStoreByUserID(id);
	}

	@Override
	public void saveStore(Store store) {

		storeDao.saveStore(store);
	}
	
}
