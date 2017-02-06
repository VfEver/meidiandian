package com.meidiandian.dao;

import com.meidiandian.entity.User;

/**
 * user dao class, manage the user data
 * @author zys
 *
 */
public interface IUserDao {
	public User findUser(String username, String password);
}
