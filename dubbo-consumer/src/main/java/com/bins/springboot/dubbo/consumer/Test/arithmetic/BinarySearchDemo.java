package com.bins.springboot.dubbo.consumer.Test.arithmetic;

/**
 * @author hex1n
 * @date 2020/12/26 23:24
 * @description
 */
public class BinarySearchDemo {
    private int[] array;

    //递归实现二分查找法
    public int searchRecursion(int target) {
        if (array != null) {
            return searchRecursion(target, 0, array.length - 1);
        }
        return -1;
    }

    private int searchRecursion(int target, int start, int end) {
        if (start > end) {
            return -1;
        }
        int mid = start + (end - start) / 2;
        if (array[mid] == target) {
            return mid;
        } else if (target < array[mid]) {
            return searchRecursion(target, start, mid - 1);
        } else {
            return searchRecursion(target, mid + 1, end);
        }
    }

    //非递归方式
    public BinarySearchDemo(int[] array) {
        this.array = array;
    }

    public int search(int target) {
        if (array == null) {
            return -1;
        }
        int start = 0;
        int end = array.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (array[mid] == target) {
                return mid;
            } else if (target < array[mid]) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = {1, 3, 5, 7, 9, 11, 19};
        BinarySearchDemo binarySearch = new BinarySearchDemo(array);
        System.out.println(binarySearch.search(0));
        System.out.println(binarySearch.search(11));
        System.out.println(binarySearch.searchRecursion(0));
        System.out.println(binarySearch.searchRecursion(11));
    }
}
