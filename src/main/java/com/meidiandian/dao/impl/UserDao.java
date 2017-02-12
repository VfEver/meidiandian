package com.meidiandian.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.meidiandian.dao.IUserDao;
import com.meidiandian.entity.User;

public class UserDao implements IUserDao {
	
	private SqlSessionFactory sqlSessionFactory;
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	@Override
	public User findUser(String account, String password) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Map<String, String> map = new HashMap<>();
		map.put("account", account);
		map.put("password", password);
		User user = sqlSession.selectOne(User.class.getName() + ".findUser", map);
		if (user != null) {
			return user;
		}
		return null;
	}
	@Override
	public void saveUser(User user) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.insert(User.class.getName() + ".saveUser", user);
		sqlSession.commit();
		sqlSession.close();
	}
}
