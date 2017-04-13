package com.meidiandian.dao.impl;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.meidiandian.dao.IPreOrderDao;
import com.meidiandian.entity.PreOrder;

public class PreOrderDao implements IPreOrderDao {

	private SqlSessionFactory sqlSessionFactory;
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	@Override
	public void savePreOrder(PreOrder preOrder) {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.insert(PreOrder.class.getName() + ".savePreOrder", preOrder);
		sqlSession.commit();
		sqlSession.close();
	}

	@Override
	public int findMaxID() {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int maxID = sqlSession.selectOne(PreOrder.class.getName() + ".findMaxID");
		sqlSession.close();
		return maxID;
	}

	@Override
	public void savePreOrderDetail(Map<String, String> map) {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.insert(PreOrder.class.getName() + ".savePreOrderDetail", map);
		sqlSession.commit();
		sqlSession.close();
	}

}
