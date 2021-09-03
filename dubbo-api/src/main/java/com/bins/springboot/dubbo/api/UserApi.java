package com.bins.springboot.dubbo.api;

import java.util.List;

import com.bins.springboot.dubbo.dto.UserDto;

public interface UserApi {
	
	List<UserDto> getUserList(String name);
	
	String sayHello(String name);

}
