package com.bins.springboot.dubbo.consumer.Test;

import com.google.common.collect.Lists;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author hex1n
 * @date 2021/4/12 12:54
 * @description
 */
public class BloomFilterTest {
    public static void main(String[] args) {
        BloomFilter<CharSequence> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")), 100000000, 0.0001);
        bloomFilter.put("何");
        bloomFilter.put("鑫");
        bloomFilter.put("redis");

//        System.out.println(bloomFilter.mightContain("redis"));
//        System.out.println(bloomFilter.mightContain("何"));
//        System.out.println(bloomFilter.mightContain("鑫"));
//        System.out.println(bloomFilter.mightContain("Java"));

        List<String> list = Lists.newArrayList();
        List<String> subList1 = Lists.newArrayList();
        List<String> subList2 = Lists.newArrayList();
        List<String> subList3 = Lists.newArrayList();

        initData(list, subList1, subList2, subList3);
        int index = 0;
        int sub1Count = 0;
        int sub2Count = 0;
        int sub3Count = 0;
        while (list.size() > 0) {
            List<String> sub = list.subList(index, index + subList1.size());
            if (sub.containsAll(subList1)) {
                sub1Count += 1;
                list = list.subList(sub.size(), list.size());
                index += subList1.size();
            } else if (subList2.containsAll(list.subList(index, index + subList2.size()))) {
                sub2Count++;
                list = list.subList(subList2.size(), list.size());
                index += subList2.size();
            } else if (subList3.containsAll(list.subList(index, index + subList3.size()))) {
                sub3Count++;
                list = list.subList(subList3.size(), list.size());
                index += subList3.size();

            } else {
                list = list.subList(1, list.size());
                index += 1;
            }
        }
        System.out.println(index);
        System.out.println(sub1Count);
        System.out.println(sub2Count);
        System.out.println(sub3Count);

    }

    private static void initData(List<String> list, List<String> subList1, List<String> subList2, List<String> subList3) {
        list.add("1");
        list.add("5");
        list.add("6");
        list.add("3");
        list.add("2");
        list.add("1");
        list.add("4");
        list.add("9");
        list.add("4");
        list.add("1");
        list.add("5");
        list.add("6");
        list.add("1");
        list.add("4");
        list.add("2");
        list.add("11");
        list.add("1");
        list.add("4");
        list.add("33");
        list.add("3");
        list.add("5");
        list.add("5");
        list.add("44");
        list.add("22");
        list.add("3");
        list.add("5");

        subList1.add("1");
        subList1.add("5");
        subList1.add("6");

        subList2.add("1");
        subList2.add("4");

        subList3.add("3");
        subList3.add("5");
    }
}
