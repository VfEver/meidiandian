package com.meidiandian.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.meidiandian.dao.IStoreDao;
import com.meidiandian.entity.Store;

public class StoreDao implements IStoreDao{
	
	private SqlSessionFactory sqlSessionFactory;
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public Store selectStoreByUserID(int id) {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Store store = sqlSession.selectOne(Store.class.getName() + ".selectStoreByUserID", id);
		sqlSession.close();
		
		return store;
	}

	@Override
	public void saveStore(Store store) {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.insert(Store.class.getName() + ".saveStore", store);
		sqlSession.commit();
		sqlSession.close();
		
	}

	@Override
	public void updateStore(Map<String, String> map) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.update(Store.class.getName() + ".updateStoreInfo", map);
		sqlSession.commit();
		sqlSession.close();
		
	}

	@Override
	public void updateStoreImg(Map<String, String> map) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.update(Store.class.getName() + ".updateStoreImg", map);
		sqlSession.commit();
		sqlSession.close();
	}

	@Override
	public List<Store> findStoreByCity(String city) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Store> storeList = sqlSession.selectList(Store.class.getName() + ".findStoreByCity", city);
		sqlSession.close();
		return storeList;
	}

	@Override
	public Store selectStoreByID(int storeID) {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Store store = sqlSession.selectOne(Store.class.getName() + ".selectStoreByID", storeID);
		sqlSession.close();
		return store;
	}
}
