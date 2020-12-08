package com.amon.javacore.algorithm.tree;

/**
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/11/30.
 */
public class TreeNode {

    private int value;
    private int count = 1;

    private TreeNode leftSonNode;
    private TreeNode rightSonNode;
    private TreeNode fatherNode;

    public TreeNode getFatherNode() {
        return fatherNode;
    }

    public void setFatherNode(TreeNode fatherNode) {
        this.fatherNode = fatherNode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public TreeNode getLeftSonNode() {
        return leftSonNode;
    }

    public void setLeftSonNode(TreeNode leftSonNode) {
        this.leftSonNode = leftSonNode;
    }

    public TreeNode getRightSonNode() {
        return rightSonNode;
    }

    public void setRightSonNode(TreeNode rightSonNode) {
        this.rightSonNode = rightSonNode;
    }
}
