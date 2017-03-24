package com.meidiandian.service.impl;

import java.util.List;
import java.util.Map;

import com.meidiandian.dao.IRecommendDao;
import com.meidiandian.service.IRecommendService;

public class RecommendService implements IRecommendService {

	private IRecommendDao recommendDao;
	public void setRecommendDao(IRecommendDao recommendDao) {
		this.recommendDao = recommendDao;
	}
	
	@Override
	public List<Map<String, String>> findItemUser() {
		
		return recommendDao.findItemUser();
	}

}
