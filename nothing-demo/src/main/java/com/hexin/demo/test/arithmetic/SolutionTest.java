package com.hexin.demo.test.arithmetic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

/**
 * @author hex1n
 * @date 2021/3/16 19:45
 * @description
 */
public class SolutionTest {

    public static void main(String[] args) {
        String[] strs = {"flower", "flow", "flight"};
        String s = zuiChangGGQZ(strs);
        String str = "abccccdd";
        int i = longestPalindrome(str);
        System.out.println(i);
    }

    /**
     * 两个栈实现队列
     */
    public static class twoStackImpQueue {
        Stack<Integer> stack1 = new Stack<Integer>();
        Stack<Integer> stack2 = new Stack<Integer>();

        // 当执行push操作时，将元素添加到stack1
        public void push(int node) {
            stack1.push(node);
        }

        public int pop() {
            //如果两个队列都为空则抛出异常，说明用户没有push进任何元素
            if (stack1.empty() && stack2.empty()) {
                throw new RuntimeException("Queue is empty!");
            }
            // 如果stack2 不为空直接对stack2执行pop操作
            if (stack2.empty()) {
                while (!stack1.empty()) {
                    //将stack1 的元素按后进先出push进stack2里面
                    stack2.push(stack1.pop());
                }
            }
            return stack2.pop();
        }
    }

    /**
     * 替换空格
     */
    public static String replaceSpace(StringBuilder str) {
        int length = str.length();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char b = str.charAt(i);
            if (" ".equals(String.valueOf(b))) {
                result.append("%20");
            } else {
                result.append(b);
            }
        }
        return result.toString();
    }

    /**
     * 最长公共前缀
     * 先利用Arrays.sort(strs)为数组排序，再将数组第一个元素和最后一个元素的字符从前往后对比
     *
     * @return
     */
    public static String zuiChangGGQZ(String[] strs) {
        // 检查值不合法及就返回空串
        if (!checkStrs(strs)) {
            return "";
        }
        // 数组长度
        int length = strs.length;
        // 用以保存结果
        StringBuilder res = new StringBuilder();
        // 排序
        Arrays.sort(strs);
        int m = strs[0].length();
        int n = strs[length - 1].length();
        int num = Math.min(m, n);
        for (int i = 0; i < num; i++) {
            if (strs[0].charAt(i) == strs[length - 1].charAt(i)) {
                res.append(strs[0].charAt(i));
            } else {
                break;
            }
        }
        return res.toString();
    }

    /**
     * 最长回文串
     * LeetCode ： 给定一个包含大写字母和小写字母的字符串，找到通过这些字母造成的最长的回文串，在构造过程中，请注意区分大小写，比如Aa不能当做一个回文字符串
     * 回文串是一个正读和反读都一样的字符串，如level
     * 构成回文串的两种情况：
     * 1.字符出现次数为双数的组合
     * 2.字符出现次数为双数的组合+一个只出现一次的字符
     * 统计字符出现的次数即可，双数才能构成回文，因为允许中间一个数单独出现，比如”abcba“，
     * 所以如果最后有字母落单，总长度可以加1.首先将字符串转变为字符数组，然后遍历该数组，判断对应字符是否在hashset中
     * 如果不在就加进去，如果在就让count++，然后移除该字符。这样就能找到出现次数为双数的字符个数。
     *
     * @param s
     * @return
     */
    public static int longestPalindrome(String s) {
        if (s.length() == 0) {
            return 0;
        }
        // 用于存放字符
        HashSet<Character> hashSet = new HashSet<>();
        char[] chars = s.toCharArray();
        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            if (!hashSet.contains(chars[i])) {
                //如果hashSet没有该字符就保存进去
                hashSet.add(chars[i]);
            } else {
                //如果有，就让count++（说明找到了一个成对的字符），然后把该字符移除
                hashSet.remove(chars[i]);
                count++;
            }
        }
        return hashSet.isEmpty() ? count * 2 : count * 2 + 1;
    }


    private static boolean checkStrs(String[] strs) {
        int length = strs.length;
        boolean flag = false;
        if (strs == null) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (" ".equals(strs[i]) || strs[i] != null || strs.length != 0) {
                flag = true;
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
