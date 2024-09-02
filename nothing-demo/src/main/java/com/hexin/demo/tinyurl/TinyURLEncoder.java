package com.hexin.demo.tinyurl;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @Author hex1n
 * @Date 2024/8/31/22:33
 * @Description
 **/
public class TinyURLEncoder {

    private static final String BASE_58_ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    private static final int BASE = BASE_58_ALPHABET.length();
    private static final Map<Character, Integer> BASE_58_MAP = Maps.newHashMap();

    static {
        for (int i = 0; i < BASE_58_ALPHABET.length(); i++) {
            BASE_58_MAP.put(BASE_58_ALPHABET.charAt(i), i);
        }
    }

    /**
     * Encodes a decimal ID to a base-58 string
     *
     * @param num
     * @return
     */
    public static String encodeBase58(long num) {
        StringBuilder encoded = new StringBuilder();
        while (num > 0) {
            int remainder = (int) (num % BASE);
            encoded.append(BASE_58_ALPHABET.charAt(remainder));
            num /= BASE;
        }
        return encoded.reverse().toString();
    }

    /**
     * Decodes a base-58 string to a decimal ID
     *
     * @param encoded
     * @return
     */
    public static long decodeBase58(String encoded) {
        long num = 0;
        for (char c : encoded.toCharArray()) {
            num = num * BASE + BASE_58_MAP.get(c);
        }
        return num;
    }

    public static void main(String[] args) {
        // Example usage
        long decimalId = 2468135791014L;
        String encoded = encodeBase58(decimalId);
        long decoded = decodeBase58(encoded);

        System.out.println("Decimal ID: " + decimalId);
        System.out.println("Encoded Base-58: " + encoded);
        System.out.println("Decoded back to Decimal ID: " + decoded);

        String encoded1 = encodeBase58(17L);
        System.out.println(encoded1);

    }
}
