package com.meidiandian.service;

import java.util.List;
import java.util.Map;

public interface IRecommendService {

	/**
	 * 查询出用户和商品关系
	 * @return
	 */
	public List<Map<String, String>> findItemUser();
}
