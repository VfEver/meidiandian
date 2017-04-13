package com.meidiandian.service;

import java.util.Map;

import com.meidiandian.entity.User;

public interface IUserService {

	/**
	 * login
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public User findUser(String account, String password);

	/**
	 * user register and save user
	 * 
	 * @param user
	 */
	public void saveUser(User user);

	/**
	 * 查询最大id，也就是当前新注册用户的id
	 * 
	 * @return
	 */
	public int findMaxID();

	/**
	 * 根据id查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	public User findUserByID(int id);

	/**
	 * 更新用户信息
	 * 
	 * @param map
	 */
	public void updateUserInfo(Map<String, String> map);

	/**
	 * 根据account查询用户
	 * 
	 * @param account
	 * @return
	 */
	public User findUserByAccount(String account);
}
