package com.meidiandian.dao.impl;

import java.util.List;
import java.util.Map;

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

	@Override
	public void saveOrderDetail(Map<String, String> map) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.insert(Order.class.getName() + ".saveOrderDetail", map);
		sqlSession.commit();
		sqlSession.close();
	}

	@Override
	public List<Map<String, String>> findOrderDetail(int storeID) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Map<String, String>> list = sqlSession.selectList(
				Order.class.getName() + ".findOrderDetail", storeID);
		sqlSession.close();
		return list;
	}

	@Override
	public void updateOrderStatus(Map<String, String> map) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.update(Order.class.getName() + ".updateOrderStatus", map);
		sqlSession.commit();
		sqlSession.close();
	}

	@Override
	public List<Map<String, String>> findUserOrder(int userID) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Map<String, String>> orderList = sqlSession.selectList(
				Order.class.getName() + ".findUserOrder", userID);
		sqlSession.close();
		return orderList;
	}

}
