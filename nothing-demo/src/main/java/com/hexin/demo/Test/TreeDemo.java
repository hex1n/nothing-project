package com.hexin.demo.Test;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author hex1n
 * @date 2020/11/30 13:07
 * @description
 */
@Slf4j
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

   /* public static void main(String[] args) {
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
    }*/

    public static void main(String[] args) {
        String str ="1============\n" +
                "accessId: ly7e4f498757da45\n" +
                "accessKey: 58796e53423e4073b4fb02aa9a838fe8 2============\n" +
                "accessId: lyd4d45aa1e62f4c\n" +
                "accessKey: 089e2cfde39f4b3d9585bec96283d9e7 3============\n" +
                "accessId: ly74eb71731c8f4c\n" +
                "accessKey: f6870ea498014f31868558410606a14a 4============\n" +
                "accessId: ly10d1fec6aefc4b\n" +
                "accessKey: f78f8a499c9e46b084393a32ab306ba1 5============\n" +
                "accessId: ly8e13013dd1ec47\n" +
                "accessKey: 1110420d47b84b2299520c4cbee14bc2 6============\n" +
                "accessId: ly7cb81a59e8d145\n" +
                "accessKey: 74349a577974409fb85de1099a3ff988 7============\n" +
                "accessId: lye7c62727fcf948\n" +
                "accessKey: 6a62aa6beeaf4627bf8441887ab1e6df 8============\n" +
                "accessId: lydf671a976a3840\n" +
                "accessKey: 2456b1a0747e469395c7a54215d56bdb 9============\n" +
                "accessId: ly663547c94f3043\n" +
                "accessKey: f6a2ea7611a446a1acf014321807f867 10============\n" +
                "accessId: ly704e63f3cd2941\n" +
                "accessKey: 4b8cc240966d4c6cabc958c82dbc5d4f 11============\n" +
                "accessId: ly525d51dffecc48\n" +
                "accessKey: 94eb5911c37645a38d92162595c095bd 12============\n" +
                "accessId: lya177e0313d8746\n" +
                "accessKey: 8ea0f601d8d940548148bce5c738ed45 13============\n" +
                "accessId: ly3d556458f2a94e\n" +
                "accessKey: 0c53557020b141b885de34da222cd9c8 14============\n" +
                "accessId: ly964ebcfa8fba4d\n" +
                "accessKey: fe9da533495640b5aaaeea115a817001 15============\n" +
                "accessId: ly76f8b17274474c\n" +
                "accessKey: bee4f867846f447984cac4a84d80d135 16============\n" +
                "accessId: ly4d8d5d48ab8043\n" +
                "accessKey: 4ef162ae832a4010bf2d64c9bc83bbbd 17============\n" +
                "accessId: ly58f225fb9eb740\n" +
                "accessKey: aa30683ef8184cc3b6be0111b7e9f036 18============\n" +
                "accessId: lybdb527fe93e24f\n" +
                "accessKey: 5a538a768f514d7d98f44bfcdc38934c 19============\n" +
                "accessId: ly204161efdbe646\n" +
                "accessKey: e241cf9706b54237a5fbbf426d9783e8 20============\n" +
                "accessId: lyc1a4a280953749\n" +
                "accessKey: 5db128cf6a9a463d94fad9d41aa07435 21============\n" +
                "accessId: ly283305b4e8ec46\n" +
                "accessKey: f4ce265c17cb40549f786e4c11b56570 22============\n" +
                "accessId: ly253ebbf632404b\n" +
                "accessKey: 57bec5f9775a41b6aea96cfc09e282a0 23============\n" +
                "accessId: ly1f808abb470446\n" +
                "accessKey: 611a38e9481044c291b02d2446bd1624 24============\n" +
                "accessId: ly4f6ca282538b41\n" +
                "accessKey: b6b9590025a64eb9accce0ea63ab7d94 25============\n" +
                "accessId: ly20c5502fd0e34e\n" +
                "accessKey: 3570c2b00a7f4b4db2fc6dc8cdee4533 26============\n" +
                "accessId: ly109781615a3b44\n" +
                "accessKey: 535f12a9d800487abe1b8e5b0215eac6 27============\n" +
                "accessId: ly4a081513eec048\n" +
                "accessKey: a20837d3e15e4437965a53fd845a8e50 28============\n" +
                "accessId: ly027636e65e0c4e\n" +
                "accessKey: 029f517be00147bba65088e60420f7e1 29============\n" +
                "accessId: lya75af4abb6f749\n" +
                "accessKey: ca05a1ab847749dd8f0f52a5d47ae9e3 30============\n" +
                "accessId: lyfb8a453d0c0e49\n" +
                "accessKey: 9596f9b4f60e4194bc2b5accfe808e35\n";
                String[] split = str.split("============");
                log.info(JSON.toJSONString(split));
    }
}
