package com.meidiandian.dao;

import java.util.List;
import java.util.Map;

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
	
	/**
	 * 更新店铺信息
	 * @param map
	 */
	public void updateStore(Map<String, String> map);
	
	/**
	 * 更新店铺图片
	 * @param map
	 */
	public void updateStoreImg(Map<String, String> map);
	
	/**
	 * 根据城市名称查询店铺
	 * @param city
	 * @return
	 */
	public List<Store> findStoreByCity(String city);
}
