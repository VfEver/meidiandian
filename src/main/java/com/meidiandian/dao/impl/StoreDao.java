package com.meidiandian.dao.impl;

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

}
