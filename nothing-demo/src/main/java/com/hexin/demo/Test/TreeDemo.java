package com.hexin.demo.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author hex1n
 * @date 2020/11/30 13:07
 * @description
 */
public class TreeDemo {

    public static TreeNode createBinaryTree(LinkedList<Integer> inputList) {
        TreeNode node = null;
        if (inputList == null || inputList.isEmpty()) {
            return null;
        }
        Integer data = inputList.removeFirst();
        if (data == null) {
            return null;
        }
        node = new TreeNode(data);
        node.leftChild = createBinaryTree(inputList);
        node.rightChild = createBinaryTree(inputList);
        return node;
    }

    /**
     * 二叉树前序遍历
     *
     * @param node
     */
    public static void preOrderTraveral(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.data);
        preOrderTraveral(node.leftChild);
        preOrderTraveral(node.rightChild);
    }

    /**
     * 二叉树中序遍历
     *
     * @param node
     */
    public static void inOrderTraveral(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrderTraveral(node.leftChild);
        System.out.print(node.data);
        inOrderTraveral(node.rightChild);
    }

    /**
     * 二叉树后序遍历
     *
     * @param node
     */
    public static void postOrderTraveral(TreeNode node) {
        if (node == null) {
            return;
        }
        postOrderTraveral(node.leftChild);
        postOrderTraveral(node.rightChild);
        System.out.print(node.data);
    }

    /**
     * 二叉树非递归前序遍历
     *
     * @param root
     */
    public static void preOrderTraveralWithStack(TreeNode root) {
        System.out.println("-------------");

        Stack<TreeNode> stack = new Stack<>();
        TreeNode treeNode = root;
        while (treeNode != null || !stack.isEmpty()) {
            //迭代访问节点的左孩子，并入栈
            while (treeNode != null) {
                System.out.print(treeNode.data);
                stack.push(treeNode);
                treeNode = treeNode.leftChild;
            }
            //如果节点没有左孩子，则弹出栈顶节点，访问节点右孩子
            if (!stack.isEmpty()) {
                treeNode = stack.pop();
                treeNode = treeNode.rightChild;
            }
        }
    }

    /**
     * 二叉树非递归中序遍历
     *
     * @param root
     */
    public static void inOrderTraveraWithStack(TreeNode root) {
        System.out.println("-------------");

        Stack<TreeNode> stack = new Stack<>();
        TreeNode treeNode = root;
        while (treeNode != null || !stack.isEmpty()) {
            //迭代访问节点的左孩子，并入栈
            while (treeNode != null) {
                stack.push(treeNode);
                treeNode = treeNode.leftChild;
            }
            //如果节点没有左孩子，则弹出栈顶节点，访问节点右孩子
            if (!stack.isEmpty()) {
                treeNode = stack.pop();
                System.out.print(treeNode.data);
                treeNode = treeNode.rightChild;
            }
        }
    }

    /**
     * 二叉树非递归后序遍历
     *
     * @param root
     */
    public static void postOrderTraverakWithStack(TreeNode root) {
        System.out.println("-------------");

        Stack<TreeNode> stack = new Stack<>();
        TreeNode treeNode = root;
        while (treeNode != null || !stack.isEmpty()) {
            //迭代访问节点的左孩子，并入栈
            while (treeNode != null) {
                stack.push(treeNode);
                treeNode = treeNode.leftChild;
            }
            //如果节点没有左孩子，则弹出栈顶节点，访问节点右孩子
            if (!stack.isEmpty()) {
                treeNode = stack.pop();
                treeNode = treeNode.rightChild;
                System.out.print(treeNode.data);
            }
        }
    }

    /**
     * 二叉树广度遍历
     *
     * @param root
     */
    public static void levelOrderTraversal(TreeNode root) {
        System.out.println("-------------");
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.data);
            if (node.leftChild != null) {
                queue.offer(node.leftChild);
            }
            if (node.rightChild != null) {
                queue.offer(node.rightChild);
            }
        }
    }

    /**
     * 二叉树递归广度遍历
     *
     * @param root
     */
    public static void levelRecursionTraversal(TreeNode root) {
        // 3289104
        System.out.print(root.data);
        TreeNode leftChild = root.leftChild;
        if (leftChild!=null){
            levelRecursionTraversal(leftChild);
        }
        TreeNode rightChild = root.rightChild;
        if (rightChild!=null){
            levelRecursionTraversal(rightChild);
        }
    }

    private static class TreeNode {
        int data;
        TreeNode leftChild;
        TreeNode rightChild;

        TreeNode(int data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        LinkedList<Integer> inputList = new LinkedList<>(Arrays.asList(new Integer[]{3, 2, 9, null, null, 10, null, null, 8, null, 4}));
        TreeNode treeNode = createBinaryTree(inputList);
//        System.out.println("前序遍历：");
//        preOrderTraveral(treeNode);
//        System.out.println("中序遍历： ");
//        inOrderTraveral(treeNode);
//        System.out.println("后序遍历： ");
//        postOrderTraveral(treeNode);
//        System.out.println("非递归前序遍历： ");
//        preOrderTraveralWithStack(treeNode);
//        System.out.println("非递归中序遍历： ");
//        inOrderTraveraWithStack(treeNode);
//        System.out.println("非递归后序遍历： ");
//        postOrderTraverakWithStack(treeNode);
//        levelOrderTraversal(treeNode);
            levelRecursionTraversal(treeNode);
    }
}
