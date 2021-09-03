package com.bins.springboot.dubbo.consumer.controller;

import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {

	/*@DubboReference
	private UserApi userApi;*/
	
	@RequestMapping("/hello")
	public String helloWorld() {
//		return userApi.sayHello("word");
		return null;
	}


	public static void main(String[] args) {
		Integer[] arr  = new Integer[]{1,2,3,5,6,7,8,9,1,5};
		Map<Integer, Integer> data = Maps.newHashMap();
		for (int i = 0; i < arr.length; i++) {
			if (data.get(arr[i])==null){
				data.put(arr[i],1);
			}else {
				data.put(arr[i],data.get(arr[i])+1);
			}
		}
//		data.forEach((o1, o2) -> System.out.println(o2));
		System.out.println(data);
	}

}
