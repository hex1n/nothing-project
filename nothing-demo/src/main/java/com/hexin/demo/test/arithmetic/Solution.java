package com.hexin.demo.test.arithmetic;

/**
 * @author hex1n
 * @date 2021/2/22 19:47
 * @description
 */
public class Solution {

    class ListNode{
        private ListNode prev;
        private ListNode cur;
        private ListNode next;
    }
    public ListNode reverseList(ListNode head){
        ListNode prev=null;
        ListNode cur=head;
        while (cur!=null){
            ListNode nextTemp=head.next;
            cur.next=prev;
            prev=cur;
            cur=nextTemp;
        }
        return prev;
    }

    public ListNode reverseList01(ListNode head){
        ListNode prev=null;
        ListNode cur=head;
        while (cur!=null){
            ListNode nextTemp=head.next;
            cur.next=prev;
            prev=cur;
            cur=nextTemp;
        }
        return prev;
    }
}
