package com.meidiandian.dao.impl;

import org.apache.ibatis.session.SqlSessionFactory;

import com.meidiandian.dao.IUserDao;

public class UserDao implements IUserDao {
	
	private SqlSessionFactory sqlSessionFactory;
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
}
