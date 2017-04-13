package com.meidiandian.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.meidiandian.dao.IGoodsDao;
import com.meidiandian.entity.Goods;

public class GoodsDao implements IGoodsDao {

	private SqlSessionFactory sqlSessionFactory;

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public List<Goods> findGoodsByStoreID(int storeID) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Goods> goodsList = sqlSession.selectList(Goods.class.getName()
				+ ".findGoodsByStoreID", storeID);
		sqlSession.close();
		return goodsList;
	}

	@Override
	public Goods findGoodsByID(int id) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		Goods goods = sqlSession.selectOne(Goods.class.getName()
				+ ".findGoodsByID", id);
		sqlSession.close();
		return goods;
	}

	@Override
	public void updateGoodsByID(Map<String, String> map) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.update(Goods.class.getName() + ".updateGoodsByID", map);
		sqlSession.commit();
		sqlSession.close();
	}

	@Override
	public void deleteGoodsByID(int goodsID) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.delete(Goods.class.getName() + ".deleteGoodsByID", goodsID);
		sqlSession.commit();
		sqlSession.close();
	}

	@Override
	public void saveGoods(Goods goods) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.insert(Goods.class.getName() + ".saveGoods", goods);
		sqlSession.commit();
		sqlSession.close();
	}

	@Override
	public void addGoodsNum(Map<String, Integer> map) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.update(Goods.class.getName() + ".addGoodsNum", map);
		sqlSession.commit();
		sqlSession.close();
	}

}
