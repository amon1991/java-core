package com.amon.javacore.algorithm.graph;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 图的深度优先遍历算法（基于邻接表实现）
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/12/30.
 */
public class DepthFirstSearch {

    private AdjacencyTableGraph graph;
    private boolean[] visited;

    /**
     * 用于对图中的节点进行分组
     */
    private int[] group;

    /**
     * 用于保存递归路径中节点的前置位节点
     */
    private int[] from;

    /**
     * 联通分量
     */
    private int ccount;

    public DepthFirstSearch(AdjacencyTableGraph graph) {

        this.graph = graph;
        this.ccount = 0;

        visited = new boolean[graph.getNodeSize()];
        group = new int[graph.getNodeSize()];
        from = new int[graph.getNodeSize()];

        for (int i = 0; i < graph.getNodeSize(); i++) {
            visited[i] = false;
            group[i] = -1;
            from[i] = -1;
        }

        for (int i = 0; i < graph.getNodeSize(); i++) {

            if (!visited[i]) {
                dfs(i);
                ccount++;
            }

        }

    }

    private void dfs(int node) {

        ArrayList<Integer> relatedNodeList = graph.getRelatedNodeList(node);

        visited[node] = true;
        group[node] = ccount;

        for (Integer releatedNode : relatedNodeList) {
            if (!visited[releatedNode]) {
                from[releatedNode] = node;
                dfs(releatedNode);
            }
        }

    }

    public int getCcount() {
        return ccount;
    }

    public boolean isConnected(int v, int w) {
        return group[v] == group[w];
    }

    public void showPath(int v, int w) {

        if (!isConnected(v, w)) {
            return;
        }

        Stack<Integer> stack = new Stack<>();
        stack.push(w);
        int endNode = w;
        while (from[endNode] != -1 && from[endNode] != v) {
            stack.push(from[endNode]);
            endNode = from[endNode];
        }
        stack.push(v);

        System.out.println("Path:");
        while (!stack.empty()) {
            System.out.print(stack.pop() + " ");
        }

    }

    public static void main(String[] args) throws Exception {

        String fileName = "graph1.txt";

        LoadGraphData loadGraphDataService = new LoadGraphData();

        int nodeSize = loadGraphDataService.getGraphSize(fileName);

        if (nodeSize != 0) {

            AdjacencyTableGraph graph = new AdjacencyTableGraph(nodeSize, true);

            // load graph data
            loadGraphDataService.loadData(fileName, graph);

            DepthFirstSearch component = new DepthFirstSearch(graph);

            System.out.println(component.getCcount());

            System.out.println(component.isConnected(0, 3));
            System.out.println(component.isConnected(0, 2));
            System.out.println(component.isConnected(3, 7));
            System.out.println(component.isConnected(5, 13));

            component.showPath(3, 7);

        }

    }

}
