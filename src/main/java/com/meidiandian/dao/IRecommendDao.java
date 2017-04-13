package com.meidiandian.dao;

import java.util.List;
import java.util.Map;

public interface IRecommendDao {

	/**
	 * 查询出用户和商品关系
	 * 
	 * @return
	 */
	public List<Map<String, String>> findItemUser();
}
