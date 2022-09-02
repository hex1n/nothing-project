package com.hexin.demo.jvm;

import java.util.ArrayList;

/**
 * @Author: hex1n
 * @Date: 2022/7/25 22:39
 * @Description: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 **/
public class HeapOOM {

    static class OOMObject {
    }

    public static void main(String[] args) {
        ArrayList<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}
