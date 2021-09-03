package com.bins.springboot.dubbo.provider.service.impl;

import com.bins.springboot.dubbo.dto.UserDto;
import com.bins.springboot.dubbo.provider.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public List<UserDto> getUserList(String name) {
		List<UserDto> userList = new ArrayList<UserDto>();
		UserDto userDto = new UserDto();
		userDto.setPassword("1234");
		userDto.setUsername("root");
		userList.add(userDto);
		return userList;
	}

}
