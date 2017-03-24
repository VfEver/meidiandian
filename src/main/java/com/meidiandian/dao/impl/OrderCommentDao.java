package com.meidiandian.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.meidiandian.dao.IOrderCommentDao;
import com.meidiandian.entity.OrderComment;

public class OrderCommentDao implements IOrderCommentDao {

	private SqlSessionFactory sqlSessionFactory;
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	@Override
	
	public void saveOrderComment(OrderComment orderComment) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.insert(OrderComment.class.getName() + ".saveOrderComment", orderComment);
		sqlSession.commit();
		sqlSession.close();
	}
	
	@Override
	public void updateOrderCommentStatus(Map<String, String> map) {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.update(OrderComment.class.getName() + ".updateOrderCommentStatus", map);
		sqlSession.commit();
		sqlSession.close();
	}
	
	@Override
	public List<OrderComment> findCommentOrder(int userID) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<OrderComment> commentOrderList = sqlSession.selectList(OrderComment.class.getName() + ".findCommentOrder", userID);
		sqlSession.close();
		
		return commentOrderList;
	}

}
