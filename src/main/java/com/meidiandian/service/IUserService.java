package com.meidiandian.service;

import com.meidiandian.entity.User;

public interface IUserService {

	public User findUser(String username, String password);
}
