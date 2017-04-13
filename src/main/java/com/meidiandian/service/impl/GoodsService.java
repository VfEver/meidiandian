package com.meidiandian.service.impl;

import java.util.List;
import java.util.Map;

import com.meidiandian.dao.IGoodsDao;
import com.meidiandian.entity.Goods;
import com.meidiandian.service.IGoodsService;

public class GoodsService implements IGoodsService {

	private IGoodsDao goodsDao;

	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	@Override
	public List<Goods> findGoodsByStoreID(int storeID) {

		return goodsDao.findGoodsByStoreID(storeID);
	}

	@Override
	public Goods findGoodsByID(int id) {

		return goodsDao.findGoodsByID(id);
	}

	@Override
	public void updateGoodsByID(Map<String, String> map) {

		goodsDao.updateGoodsByID(map);
	}

	@Override
	public void deleteGoodsByID(int goodsID) {

		goodsDao.deleteGoodsByID(goodsID);
	}

	@Override
	public void saveGoods(Goods goods) {
		goodsDao.saveGoods(goods);
	}

	@Override
	public void addGoodsNum(Map<String, Integer> map) {

		goodsDao.addGoodsNum(map);
	}

}
