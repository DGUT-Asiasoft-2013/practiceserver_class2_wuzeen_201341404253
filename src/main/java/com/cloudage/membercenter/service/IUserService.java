package com.cloudage.membercenter.service;

import com.cloudage.membercenter.entity.User;

public interface IUserService {
	User save(User user);
	User login(String account);
	User findUserById(int id);
	User findUserByEmail(String email);
}
