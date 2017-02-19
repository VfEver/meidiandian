package com.meidiandian.service;

import com.meidiandian.entity.Store;

public interface IStoreService {
	/**
	 * 根据用户id查询他的店铺
	 * @param id
	 * @return
	 */
	public Store selectStoreByUserID(int id);
	
	/**
	 * 保存店铺
	 * @param store
	 */
	public void saveStore(Store store);
}
