package com.meidiandian.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.meidiandian.dao.IRecommendDao;

public class RecommendDao implements IRecommendDao{

	private SqlSessionFactory sqlSessionFactory;
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	@Override
	public List<Map<String, String>> findItemUser() {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Map<String, String>> relation = sqlSession.selectList("recommend.findItemUser");
		sqlSession.close();
		return relation;
	}

}
