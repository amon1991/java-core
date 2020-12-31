package com.amon.javacore.algorithm.graph;

import java.util.ArrayList;

/**
 * 使用邻接表实现的图数据结构
 * PS：这种结构适用于稀疏图
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/12/30.
 */
public class AdjacencyTableGraph implements BaseGraph {

    private ArrayList<ArrayList<Integer>> graph;

    /**
     * 图中的节点数
     */
    private int nodeSize = 0;

    /**
     * 图中的边数
     */
    private int edgeSize = 0;

    /**
     * 是否为有向图
     */
    private boolean directed;


    public AdjacencyTableGraph(int nodeSize, boolean directed) {

        this.nodeSize = nodeSize;
        this.directed = directed;

        graph = new ArrayList<>(nodeSize);

        for (int i = 0; i < nodeSize; i++) {

            graph.add(new ArrayList<>());

        }

    }

    @Override
    public void addEdge(int from, int to, boolean directed) {

        if (!(from >= 0 && from < nodeSize && to >= 0 && to < nodeSize)) {
            return;
        }

        if (hasEdge(from, to, directed)) {
            return;
        }

        graph.get(from).add(to);

        if (!directed) {
            graph.get(to).add(from);
        }

        edgeSize++;

    }

    @Override
    public boolean hasEdge(int from, int to, boolean directed) {

        if (from >= 0 && from < nodeSize && to >= 0 && to < nodeSize) {

            ArrayList<Integer> list = graph.get(from);

            for (Integer toElement : list) {
                if (toElement.intValue() == to) {
                    return true;
                }
            }

        }

        return false;
    }

    @Override
    public void showGraph() {

        System.out.println("EdgeSize:" + edgeSize);

        for (int i = 0; i < nodeSize; i++) {

            System.out.print(i + ":");

            ArrayList<Integer> list = graph.get(i);

            for (Integer toElement : list) {
                System.out.print(toElement + " ");
            }

            System.out.println();

        }

    }

    @Override
    public boolean directed() {
        return directed;
    }

    public int getNodeSize() {
        return nodeSize;
    }

    public ArrayList<Integer> getRelatedNodeList(int node) {

        if (node >= 0 && node < nodeSize) {
            return graph.get(node);
        } else {
            return new ArrayList<>();
        }

    }

    public static void main(String[] args) throws Exception {

        String fileName = "graph1.txt";

        LoadGraphData loadGraphDataService = new LoadGraphData();

        int nodeSize = loadGraphDataService.getGraphSize(fileName);

        if (nodeSize != 0) {

            BaseGraph graph = new AdjacencyTableGraph(nodeSize, true);

            // load graph data
            loadGraphDataService.loadData(fileName, graph);

            // show graph data
            graph.showGraph();

        }

    }


}
