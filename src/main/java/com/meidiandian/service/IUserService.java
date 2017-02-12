package com.meidiandian.service;

import com.meidiandian.entity.User;

public interface IUserService {

	/**
	 * login
	 * @param username
	 * @param password
	 * @return
	 */
	public User findUser(String account, String password);
	
	/**
	 * user register and save user
	 * @param user
	 */
	public void saveUser(User user);
}
