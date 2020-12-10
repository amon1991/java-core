package com.amon.javacore.algorithm.unionfind;

/**
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/12/10.
 */
public class UnionFind {


    /**
     * 父节点标记
     */
    private int[] parentArr;
    /**
     * group的层高
     */
    private int[] rankArr;
    /**
     * 数组长度
     */
    private int count;

    public int[] getParentArr() {
        return parentArr;
    }

    public int[] getRankArr() {
        return rankArr;
    }

    public UnionFind(int count) {

        this.count = count;
        parentArr = new int[count];
        rankArr = new int[count];

        for (int i = 0; i < count; i++) {

            parentArr[i] = i;
            rankArr[i] = 1;

        }

    }

    /**
     * 查找数组中p位置group的树根节点
     *
     * @param p
     * @return
     */
    private int find(int p){

        while (parentArr[p]!=p){
            // 路径压缩优化
            parentArr[p] = parentArr[parentArr[p]];
            p = parentArr[p];
        }

        return parentArr[p];

    }

    /**
     * 判断数组中p位置和q位置的连接性
     *
     * @param p
     * @param q
     * @return
     */
    public boolean connected(int p,int q){

        return find(p) == find(q);

    }

    /**
     * 将数组中p位置和q位置的元素合并到同一个组中
     *
     * @param p
     * @param q
     */
    public void union(int p,int q){

        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot==qRoot){
            return;
        }else if (rankArr[pRoot]>rankArr[qRoot]){
            parentArr[qRoot] = pRoot;
        }else if (rankArr[pRoot]<rankArr[qRoot]){
            parentArr[pRoot] = qRoot;
        }else {
            parentArr[qRoot] = pRoot;
            rankArr[pRoot] +=1;
        }

    }

    public static void main(String[] args) {

        UnionFind unionFind = new UnionFind(10);

        // 将1和5连接
        unionFind.union(0,4);
        // 将2和6连接
        unionFind.union(1,5);
        // 将1和2连接
        unionFind.union(0,1);

        // 判断5,6的连接性，结果应该为ture
        System.out.println(unionFind.connected(4,5));
        // 判断6，7的连接性，结果应该为false
        System.out.println(unionFind.connected(5,6));

        // 打印parent数组和rank数组
        for (int i : unionFind.getParentArr()) {
            System.out.print(i+" ");
        }
        System.out.println();
        for (int i : unionFind.getRankArr()) {
            System.out.print(i+" ");
        }
        System.out.println();

    }

}
