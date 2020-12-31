package com.amon.javacore.algorithm.graph;

/**
 * 使用邻接矩阵实现的图数据结构
 * PS：这种结构适用于稠密图
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/12/30.
 */
public class AdjacencyMatrixGraph implements BaseGraph {


    private int[][] graph;

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

    public AdjacencyMatrixGraph(int nodeSize, boolean directed) {

        this.nodeSize = nodeSize;
        this.directed = directed;

        graph = new int[nodeSize][nodeSize];

        for (int i = 0; i < nodeSize; i++) {
            for (int j = 0; j < nodeSize; j++) {
                graph[i][j] = 0;
            }
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

        graph[from][to] = 1;

        if (!directed) {
            graph[to][from] = 1;
        }

        edgeSize++;

    }

    @Override
    public boolean hasEdge(int from, int to, boolean directed) {
        if (from >= 0 && from < nodeSize && to >= 0 && to < nodeSize) {
            return graph[from][to] == 1;
        } else {
            return false;
        }
    }

    @Override
    public void showGraph() {

        System.out.println("EdgeSize:" + edgeSize);

        for (int i = 0; i < nodeSize; i++) {
            System.out.print(i + "： ");
            for (int j = 0; j < nodeSize; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }

    }

    @Override
    public boolean directed() {
        return directed;
    }

    public static void main(String[] args) throws Exception {

        String fileName = "graph1.txt";

        LoadGraphData loadGraphDataService = new LoadGraphData();

        int nodeSize = loadGraphDataService.getGraphSize(fileName);

        if (nodeSize != 0) {

            BaseGraph graph = new AdjacencyMatrixGraph(nodeSize, false);

            // load graph data
            loadGraphDataService.loadData(fileName, graph);

            // show graph data
            graph.showGraph();

        }

    }


}
