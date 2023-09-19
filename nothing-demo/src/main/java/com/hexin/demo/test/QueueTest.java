package com.hexin.demo.test;


/**
 * @author hex1n
 * @date 2021/3/12 22:13
 * @description
 */
public class QueueTest<E> {

    Node<E> hand;
    Node<E> tail;

    private static class Node<E> {
        E item;
        Node<E> next;

        Node(E e) {
            this.item = e;
            this.next = null;
        }
    }

    public void enQueue(E e) {
        Node<E> newNode = new Node(e);
        if (hand == null) {
            hand = newNode;
            tail = newNode;
        } else {
            hand.next = newNode;
            tail = newNode;
        }
    }

    public E deQueue() {
        if (hand == null) {
            return null;
        }
        Node<E> node = hand;
        node.next = null;
        hand = hand.next;
        return node.item;
    }

}
