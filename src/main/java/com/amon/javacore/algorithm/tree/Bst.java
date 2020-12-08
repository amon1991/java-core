package com.amon.javacore.algorithm.tree;

import com.amon.javacore.algorithm.sort.SortTestHelper;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/11/30.
 */
public class Bst {

    private TreeNode rootFatherNode = new TreeNode();
    private int count = 0;

    public TreeNode getRootFatherNode() {
        return rootFatherNode;
    }

    /**
     * 向BST中插入元素
     *
     * @param currentNode
     * @param insertValue
     */
    public void insert(TreeNode currentNode, int insertValue) {

        if (count == 0) {
            TreeNode root = new TreeNode();
            // 根节点
            root.setValue(insertValue);
            root.setFatherNode(rootFatherNode);
            rootFatherNode.setLeftSonNode(root);
            count++;
            return;
        }

        if (currentNode.getValue() > insertValue) {
            if (currentNode.getLeftSonNode() == null) {
                TreeNode node = new TreeNode();
                node.setValue(insertValue);
                currentNode.setLeftSonNode(node);
                node.setFatherNode(currentNode);
                count++;
            } else {
                insert(currentNode.getLeftSonNode(), insertValue);
            }
        } else if (currentNode.getValue() < insertValue) {
            if (currentNode.getRightSonNode() == null) {
                TreeNode node = new TreeNode();
                node.setValue(insertValue);
                currentNode.setRightSonNode(node);
                node.setFatherNode(currentNode);
                count++;
            } else {
                insert(currentNode.getRightSonNode(), insertValue);
            }
        } else {
            currentNode.setCount(currentNode.getCount() + 1);
        }

    }

    /**
     * 从BST中删除元素
     *
     * @param node
     * @param value
     */
    public void delete(TreeNode node, int value) {

        // 第一步，查找节点
        TreeNode deleteNode = search(node, value);
        if (null == deleteNode) {
            return;
        }

        // 第二步，根据节点不同状态，处理删除逻辑
        if (null == deleteNode.getLeftSonNode() && null == deleteNode.getRightSonNode()) {

            // 左右儿子均为空
            if (deleteNode.getFatherNode().getLeftSonNode() == deleteNode) {
                deleteNode.getFatherNode().setLeftSonNode(null);
            } else {
                deleteNode.getFatherNode().setRightSonNode(null);
            }


        } else if (null != deleteNode.getLeftSonNode() && null == deleteNode.getRightSonNode()) {

            // 左儿子非空，右儿子为空，用左儿子替换当前节点
            deleteNode.getLeftSonNode().setFatherNode(deleteNode.getFatherNode());
            if (deleteNode.getFatherNode().getLeftSonNode() == deleteNode) {
                deleteNode.getFatherNode().setLeftSonNode(deleteNode.getLeftSonNode());
            } else {
                deleteNode.getFatherNode().setRightSonNode(deleteNode.getLeftSonNode());
            }

        } else if (null == deleteNode.getLeftSonNode() && null != deleteNode.getRightSonNode()) {

            // 左儿子为空，右儿子非空，用右儿子替换当前节点
            deleteNode.getRightSonNode().setFatherNode(deleteNode.getFatherNode());
            if (deleteNode.getFatherNode().getLeftSonNode() == deleteNode) {
                deleteNode.getFatherNode().setLeftSonNode(deleteNode.getRightSonNode());
            } else {
                deleteNode.getFatherNode().setRightSonNode(deleteNode.getRightSonNode());
            }

        } else {

            // 左右儿子都非空，查找右子树中的最小值，替换当前节点
            TreeNode p = deleteNode.getRightSonNode();
            while (p.getLeftSonNode() != null) {
                p = p.getLeftSonNode();
            }

            if (p.getFatherNode().getLeftSonNode() == p) {
                p.getFatherNode().setLeftSonNode(null);
            } else {
                p.getFatherNode().setRightSonNode(null);
            }

            p.setLeftSonNode(deleteNode.getLeftSonNode());
            p.setRightSonNode(deleteNode.getRightSonNode());
            p.setFatherNode(deleteNode.getFatherNode());

            if (deleteNode.getFatherNode().getLeftSonNode() == deleteNode) {
                deleteNode.getFatherNode().setLeftSonNode(p);
            } else {
                deleteNode.getFatherNode().setRightSonNode(p);
            }

        }

        deleteNode.setFatherNode(null);
        deleteNode.setLeftSonNode(null);
        deleteNode.setRightSonNode(null);

        count--;
        return;

    }

    /**
     * 查询BST中的节点
     *
     * @param node
     * @param value
     * @return
     */
    public TreeNode search(TreeNode node, int value) {

        if (node.getValue() == value) {
            return node;
        } else if (node.getValue() > value) {
            // 在左子树中查询
            if (node.getLeftSonNode() == null) {
                return null;
            } else {
                return search(node.getLeftSonNode(), value);
            }
        } else {
            // 在右子树中查询
            if (node.getRightSonNode() == null) {
                return null;
            } else {
                return search(node.getRightSonNode(), value);
            }
        }

    }

    /**
     * 前序遍历
     *
     * @param node
     */
    public void prePrint(TreeNode node) {

        if (node == null) {
            return;
        } else {
            System.out.print(node.getValue() + " ");
            prePrint(node.getLeftSonNode());
            prePrint(node.getRightSonNode());
        }

    }

    /**
     * 中序遍历
     *
     * @param node
     */
    public void midPrint(TreeNode node) {

        if (node == null) {
            return;
        } else {
            midPrint(node.getLeftSonNode());
            System.out.print(node.getValue() + " ");
            midPrint(node.getRightSonNode());
        }

    }

    /**
     * 后序遍历
     *
     * @param node
     */
    public void afterPrint(TreeNode node) {

        if (node == null) {
            return;
        } else {
            afterPrint(node.getLeftSonNode());
            afterPrint(node.getRightSonNode());
            System.out.print(node.getValue() + " ");
        }

    }

    /**
     * 层序遍历
     *
     * @param node
     */
    public void levelPrint(TreeNode node) {

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(node);

        while (!queue.isEmpty()) {

            TreeNode headNode = queue.poll();
            System.out.print(headNode.getValue() + " ");

            if (null != headNode.getLeftSonNode()) {
                queue.offer(headNode.getLeftSonNode());
            }

            if (null != headNode.getRightSonNode()) {
                queue.offer(headNode.getRightSonNode());
            }

        }

    }


    public static void main(String[] args) {

        int[] arr = SortTestHelper.generateRandomArray(10, 10, 100);

        Bst bst = new Bst();

        // do insert
        System.out.println("随机数数组：");
        for (int i : arr) {
            System.out.print(i + " ");
            bst.insert(bst.getRootFatherNode().getLeftSonNode(), i);
        }
        System.out.println();


        // pre print
        System.out.println("前序遍历：");
        bst.prePrint(bst.getRootFatherNode().getLeftSonNode());
        System.out.println();

        // mid print
        System.out.println("中序遍历：");
        bst.midPrint(bst.getRootFatherNode().getLeftSonNode());
        System.out.println();

        // after print
        System.out.println("后序遍历：");
        bst.afterPrint(bst.getRootFatherNode().getLeftSonNode());
        System.out.println();

        // level print
        System.out.println("层序遍历：");
        bst.levelPrint(bst.getRootFatherNode().getLeftSonNode());
        System.out.println();

        // do search
        TreeNode treeNode = bst.search(bst.getRootFatherNode().getLeftSonNode(), arr[5]);
        // do delete and do mid print
        System.out.println("删除元素后中序遍历：");
        bst.delete(bst.getRootFatherNode().getLeftSonNode(), treeNode.getValue());
        bst.midPrint(bst.getRootFatherNode().getLeftSonNode());
        System.out.println();


    }

}
