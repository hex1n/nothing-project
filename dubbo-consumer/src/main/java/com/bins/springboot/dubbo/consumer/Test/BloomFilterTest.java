package com.bins.springboot.dubbo.consumer.Test;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;

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

        System.out.println(bloomFilter.mightContain("redis"));
        System.out.println(bloomFilter.mightContain("何"));
        System.out.println(bloomFilter.mightContain("鑫"));
        System.out.println(bloomFilter.mightContain("Java"));

    }
}
