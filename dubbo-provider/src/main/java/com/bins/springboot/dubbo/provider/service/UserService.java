package com.bins.springboot.dubbo.provider.service;

import java.util.List;

import com.bins.springboot.dubbo.dto.UserDto;

public interface UserService {

	List<UserDto> getUserList(String name);

}
