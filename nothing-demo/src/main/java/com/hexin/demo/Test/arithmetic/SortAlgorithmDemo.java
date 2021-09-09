package com.hexin.demo.Test.arithmetic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hex1n
 * @date 2020/12/3 18:24
 * @description
 */
public class SortAlgorithmDemo {

    public static void main(String[] args) {
        String str="2020112922001194215759433808";
        int[] arr = new int[]{5, 8, 6, 3, 9, 2, 1, 7, 10, 12};
//        bubbleSort1(arr);
//        bubbleSort2(arr);
//        bubbleSort3(arr);
//        bubbleSort4(arr);
//        bubbleSort5(arr);
//        jwjSort(arr);
//        jwjSort01(arr);
//        bubbleSort6(arr);
        jwjSort02(arr);
        ListNode n1 = new ListNode(1);
        n1.setNext(new ListNode(3));
        ListNode n2 = new ListNode(2);
        n2.setNext(new ListNode(4));
        ListNode n3 = new ListNode(5);
        n3.setNext(new ListNode(6));

        List<ListNode> nodeList = Arrays.asList(n1, n2, n3);
        LinkedList<ListNode> linkedList=new LinkedList();
        LinkedList<ListNode> reverseLinkedList=new LinkedList();
        linkedList.addAll(nodeList);
        Solution solution = new Solution();
        for (ListNode listNode : nodeList) {
            ListNode node = solution.reverseList(listNode);
            reverseLinkedList.add(node);
        }
        System.out.println(linkedList);
        System.out.println(reverseLinkedList);
        System.out.println(Arrays.toString(arr));
        int[] arrr=new int[]{1, 2, 4, 5, 7, 9, 0, 3};
        bubbleSort07(arrr);
        System.out.println(Arrays.toString(arrr));


    }

    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void bubbleSort1(int[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                count++;
                if (arr[j] > arr[j + 1]) {
                    int temp;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        System.out.println(count);
    }

    public static void bubbleSort2(int[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            boolean isSorted = true;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                count++;
                if (arr[j] > arr[j + 1]) {
                    int temp;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
        }
        System.out.println(count);
    }

    public static void bubbleSort3(int[] arr) {
        // 记录最后一次交换的位置
        int lastExchangeIndex = 0;
        //无序数列的边界，每次比较只需要到这里为止
        int sortBorder = arr.length - 1;
        for (int i = 0; i < arr.length - 1; i++) {
            boolean isSorted = true;
            for (int j = 0; j < sortBorder; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    // 因为有元素进行交换,所以不是有序的，标记变为false
                    isSorted = false;
                    //更新为最后一次交换元素的位置
                    lastExchangeIndex = j;
                }
            }
            sortBorder = lastExchangeIndex;
            if (isSorted) {
                break;
            }
        }
    }

    public static void bubbleSort4(int[] arr) {

        int lastIndex = 0;
        int borderIndex = arr.length - 1;
        for (int i = 0; i < arr.length - 1; i++) {
            boolean isSorted = true;
            for (int j = 0; j < borderIndex; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    isSorted = false;
                    lastIndex = j;
                }
            }
            borderIndex = lastIndex;
            if (isSorted) {
                break;
            }
        }
    }

    public static void bubbleSort5(int[] arr) {
        int borderLength = arr.length - 1;
        int lastIndex = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            boolean isSorted = true;
            for (int j = 0; j < borderLength; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    isSorted = false;
                    lastIndex = j;
                }
            }
            borderLength = lastIndex;
            if (isSorted) {
                break;
            }
        }
    }

    public static void jwjSort(int[] arr) {
        int temp = 0;
        for (int i = 0; i < arr.length / 2; i++) {
            //有序标记，每一轮的初始值都是true
            boolean isSorted = true;
            //奇数轮，从左向右比较交换
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    // 有元素交换，所以不是有序的，标记变为false
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
            // 在偶数轮之前，将isSorted重新标记为true
            isSorted = true;
            //偶数轮，从右向左比较和交换
            for (int j = arr.length - i - 1; j > i; j--) {
                if (arr[j] < arr[j - 1]) {
                    temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
        }
    }

    public static void jwjSort01(int[] arr) {
        int temp;
        for (int i = 0; i < arr.length / 2; i++) {
            boolean isSorted = true;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
            isSorted = true;
            for (int j = arr.length - i - 1; j > i; j--) {
                if (arr[j] < arr[j - 1]) {
                    temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
        }
    }

    public static void bubbleSort6(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            boolean isSorted = true;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
        }
    }

    public static void jwjSort02(int[] arr) {
        for (int i = 0; i < arr.length / 2; i++) {
            boolean isSorted = true;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    isSorted = false;
                }
            }
            isSorted = true;
            for (int j = arr.length - 1; j > i; j--) {
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
        }
    }

    public static void bubbleSort07(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length-i-1; j++) {
                if (arr[j]>arr[j+1]){
                    int temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }
    }

    public static void quickSort(int[] arr){
        // 1.设置两个指针，分别指向数组的头部和尾部
        // 2.从右至左扫描，通过偏移right指针,寻找比基准数小的元素。我们找到比基准数小的元素，将其赋值给left指针所指的位置。
        // 3.从左向右扫描,通过偏移left指针，寻找比基准数大的元素。我们找到比基准数大的元素，将其赋值给right指针所指向的位置
        // 4.不断重复2，3步骤，知道left ，right指针重合。这样，所有的元素都被扫描了一遍。我们将基准数赋值给重合位置。此时，已经完成了一次排序，基准数的左边都是比它小的数，而右边都是比它大的数。
        // 5.以基准数为分割点。对其左侧和右侧的数分别按照1，2，3，4步骤去进行排序，经过递归过程，最后排序结束
        int left=0;
        int right=arr.length-1;
//        int position=position();
    }
    public int division(int[] list,int left ,int right){
        //以最左边的数left为基准；
        int base=list[left];
        while (left<right){
            //从序列右端开始，向左遍历，直到找到小于base的数
            while (left<right && list[right]>=base){
                right--;
                //找到了比base小的元素，将这个元素放到最左边的位置
                list[left]=list[right];
                while (left<right && list[left]<=base){

                }
            }
        }
        return base;
    }

    @Getter
    @Setter
    @ToString
    public static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public static class Solution {
        public ListNode reverseList(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }
            ListNode p1 = head;
            ListNode p2 = head.next;
            ListNode p3 = null;
            while (p2 != null) {
                p3 = p2.next;
                p2.next = p1;
                p2 = p3;
            }
            head.next = null;
            head = p1;
            return head;
        }
    }
}
