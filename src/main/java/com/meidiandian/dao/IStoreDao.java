package com.meidiandian.dao;

import com.meidiandian.entity.Store;

public interface IStoreDao {
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
