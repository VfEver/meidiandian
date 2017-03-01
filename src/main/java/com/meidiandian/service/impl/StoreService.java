package com.meidiandian.service.impl;

import java.util.List;
import java.util.Map;

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

	@Override
	public void updateStore(Map<String, String> map) {
		
		storeDao.updateStore(map);
	}

	@Override
	public void updateStoreImg(Map<String, String> map) {

		storeDao.updateStoreImg(map);
	}

	@Override
	public List<Store> findStoreByCity(String city) {

		return storeDao.findStoreByCity(city);
	}
	
}
