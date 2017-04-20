package com.meidiandian.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.meidiandian.dao.IRecommendDao;

public class RecommendDao implements IRecommendDao {

	private SqlSessionFactory sqlSessionFactory;

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public List<Map<String, String>> findItemUser() {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Map<String, String>> relation = sqlSession
				.selectList("recommend.findItemUser");
		sqlSession.close();
		return relation;
	}

	@Override
	public Map<String, String> findGoodsDetail(int goodsID) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		Map<String, String> goodsDetail = sqlSession.selectOne("recommend.findGoodsDetail", goodsID);
		sqlSession.close();
		return goodsDetail;
	}

	@Override
	public Map<String, String> findTopGoods(int topID) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		Map<String, String> goodsDetail = sqlSession.selectOne("recommend.findTopGoods", topID);
		sqlSession.close();
		return goodsDetail;
	}

}
