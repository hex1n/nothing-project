package com.hexin.springboot.dubbo.consumer.Test;

/**
 * @author hex1n
 * @date 2020/12/26 23:39
 * @description
 */
public class QuickSortDemo {

    private int[] arr = {-1, 3, 5, 8, 1, 2, 1, 9, 4, 7, 6};

    public int sorting(int low, int high) {
        arr[0] = arr[low];
        while (low < high) {
            while (low < high && arr[high] >= arr[0]) {
                high--;
            }
            arr[low] = arr[high];
            while (low < high && arr[low] <= arr[0]) {
                low++;
            }
            arr[high] = arr[low];
        }
        arr[low] = arr[0];
        return low;
    }

    public void qsort(int low, int high) {
        if (low < high) {
            int pos = sorting(low, high);
            qsort(low, pos - 1);
            qsort(pos + 1, high);
        }
    }
}
