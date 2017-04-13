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
		User user = sqlSession.selectOne(User.class.getName() + ".findUser",
				map);
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

	@Override
	public int findMaxID() {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		int id = sqlSession.selectOne(User.class.getName() + ".findMaxID");
		sqlSession.close();
		return id;
	}

	@Override
	public User findUserByID(int id) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		User user = sqlSession.selectOne(
				User.class.getName() + ".findUserByID", id);
		sqlSession.close();

		return user;
	}

	@Override
	public void updateUserInfo(Map<String, String> map) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.update(User.class.getName() + ".updateUserInfo", map);
		sqlSession.commit();
		sqlSession.close();
	}

	@Override
	public User findUserByAccount(String account) {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		User user = sqlSession.selectOne(User.class.getName()
				+ ".findUserByAccount", account);
		sqlSession.close();

		return user;
	}
}
