package com.meidiandian.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.meidiandian.dao.IOrderDao;
import com.meidiandian.entity.Order;

public class OrderDao implements IOrderDao {

	private SqlSessionFactory sqlSessionFactory;
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	@Override
	public void saveOrder(Order order) {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.insert(Order.class.getName() + ".saveOrder", order);
		sqlSession.commit();
		sqlSession.close();
	}

}
