package com.hexin.demo.controller;

import com.google.common.collect.Lists;
import com.hexin.demo.util.GsonUtils;
import org.redisson.connection.CRC16;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @Author hex1n @Date 2022/7/4 16:41 @Description
 */
@RequestMapping("/requestLimit")
@RestController
public class RequestLimitController {

  public static void main(String[] args) {
    //
    List<String> arr =
        Arrays.asList(
            "2021", "2022", "hexin", "hexin1", "hexin2", "zhangqiangming", "zhangqingming1","xingminghao","xingminghao1");
    List<String> a = Lists.newArrayList();
    List<String> b = Lists.newArrayList();
    List<String> c = Lists.newArrayList();
    List<List<String>> allArr = Lists.newArrayList();
    allArr.add(a);
    allArr.add(b);
    allArr.add(c);
    for (int i = 0; i < arr.size(); i++) {
      //
      String ele = arr.get(i);
      int position = CRC16.crc16(ele.getBytes());
      System.out.println(position);
      int positions = position % 3;
      allArr.get(positions).add(ele);
    }
    System.out.println(GsonUtils.toJSONString(allArr));
  }
}
