package com.kashyap.reg.service;

import java.util.List;

import com.kashyap.reg.dto.UserDto;
import com.kashyap.reg.entity.User;

public interface UserService {
	void saveUser(UserDto dto);
	User findUserByEmail(String email);
	List<UserDto> findAll();
}
