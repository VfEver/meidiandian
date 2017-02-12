package com.meidiandian.service.impl;

import com.meidiandian.dao.IUserDao;
import com.meidiandian.entity.User;
import com.meidiandian.service.IUserService;

public class UserService implements IUserService{

	private IUserDao userDao;
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	@Override
	public User findUser(String account, String password) {
		return userDao.findUser(account, password);
	}
	@Override
	public void saveUser(User user) {

		userDao.saveUser(user);
	}
	
}
