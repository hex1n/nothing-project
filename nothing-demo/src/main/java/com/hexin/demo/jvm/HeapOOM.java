package com.hexin.demo.jvm;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @Author: hex1n
 * @Date: 2022/7/25 22:39
 * @Description: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 **/
public class HeapOOM {

    static class OOMObject {
    }
    public static void main(String[] args) {

        String str ="111222333aaabbbccccc1234567abcdefabacbdddddddabc1234567";
        Map<String,Integer> map = Maps.newHashMap();
        String[] strList = str.split("");
        String findStr="";
        Integer count = 0;
        for (int i = 0; i < strList.length; i++) {
            if (!map.containsKey(strList[i])){
                map.put(strList[i],1);
                count = 1;
                findStr = strList[i];
            }else if(i+1<strList.length && map.get(strList[i]).equals(map.get(strList[i+1]))){
                map.put(strList[i], map.get(strList[i])+1);
                count = count>map.get(strList[i])?count:map.get(strList[i]);
                findStr= count> map.get(strList[i])?findStr: strList[i];
            }

        }

        System.out.println("findStr: "+findStr);

    }
}
