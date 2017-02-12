package com.meidiandian.dao;

import com.meidiandian.entity.User;

/**
 * user dao class, manage the user data
 * @author zys
 *
 */
public interface IUserDao {
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
