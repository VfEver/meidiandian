package com.meidiandian.service.impl;

import java.util.Map;

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
	@Override
	public int findMaxID() {

		return userDao.findMaxID();
	}
	@Override
	public User findUserByID(int id) {

		return userDao.findUserByID(id);
	}
	@Override
	public void updateUserInfo(Map<String, String> map) {
		
		userDao.updateUserInfo(map);
	}
	@Override
	public User findUserByAccount(String account) {

		return userDao.findUserByAccount(account);
	}
	
}
