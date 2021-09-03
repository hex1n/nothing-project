package com.bins.springboot.dubbo.provider.api.impl;

import com.bins.springboot.dubbo.api.UserApi;
import com.bins.springboot.dubbo.dto.UserDto;
import com.bins.springboot.dubbo.provider.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@DubboService(interfaceClass = UserApi.class)
public class UserApiImpl implements UserApi {
	
	@Autowired
	private UserService userService;

	@Override
    public List<UserDto> getUserList(String name) {
		return userService.getUserList(name);
	}
	
    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public String sayHello(String name) {
        return String.format("[%s] : Hello, %s", serviceName, name+"   66666");
    }

}
