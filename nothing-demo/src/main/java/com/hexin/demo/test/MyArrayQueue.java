package com.hexin.demo.test;

/**
 * @author hex1n
 * @date 2020/12/26 23:09
 * @description
 */
public class MyArrayQueue<E> {
    private int size;//队列元素个数
    private int length;// 数字实际长度
    private int font;// 指向对头
    private int rear;//指向队尾
    private Object[] element;//存储队列元素
    private final int DEFAULT_SIZE = 10;

    MyArrayQueue() {
        this.element = new Object[DEFAULT_SIZE];
        this.font = 0;
        this.rear = 0;
        this.length = DEFAULT_SIZE;
    }
    MyArrayQueue(int initCapacity) {
        this.element = new Object[initCapacity];
        this.font = 0;
        this.rear = 0;
        this.length = initCapacity;
    }

    public int getSize() {
        return (rear - font + length) % length;
    }

    public boolean enQueue(E e) {
        if ((rear + 1) % length == font) {
            throw new ArrayIndexOutOfBoundsException("队列已满");
        }
        element[rear] = e; //元素置于队尾
        rear = (rear + 1) % length; // rear 后移一位，若到末尾则转移到数组头部
        return true;
    }

    //出列。获取并移除队列头部元素
    public E deQueue() {
        // 首先判断队列是否为空
        if (font == rear) {
            return null;
        }
        E ele = (E) element[font];
        font = (font + 1) % length; // rear 后移一位，若末尾则转移到数组头部
        return ele;
    }

    public E getFirst() {
        if (font == rear) {
            return null;
        }
        return (E) element[font];
    }

    public boolean isEmpty() {
        return font == rear;
    }

    public static void main(String[] args) {
        MyArrayQueue<Object> myArrayQueue = new MyArrayQueue<>(11);
        for (int i = 0; i < 10; i++) {
            myArrayQueue.enQueue(i);
        }
        System.out.println();
        System.out.println("队列元素的个数为："+myArrayQueue.getSize());
        System.out.println("--------打印输出------");
        while (!myArrayQueue.isEmpty()){
            System.out.println(myArrayQueue.deQueue()+"");
        }
    }
}
