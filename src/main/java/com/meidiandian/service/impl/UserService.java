package com.meidiandian.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.meidiandian.entity.User;
import com.meidiandian.service.IUserService;

public class UserService implements IUserService{
	
	private SqlSessionFactory sqlSessionFactory;
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public String login() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", "123");
		map.put("password", "123");
		User user = sqlSession.selectOne(User.class.getName() + ".findUser", map);
		if (user != null) {
			return user.getId() + ":" + user.getPasswrod();
		}
		return "login failed!";
	}
}
