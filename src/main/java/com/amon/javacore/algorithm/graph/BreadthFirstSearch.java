package com.amon.javacore.algorithm.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 图的广度优先遍历算法（基于邻接表实现）
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/12/31.
 */
public class BreadthFirstSearch {

    private AdjacencyTableGraph graph;
    private boolean[] visited;
    private int[] group;

    /**
     * 用于保存节点的前置位节点
     */
    private int[] from;

    public BreadthFirstSearch(AdjacencyTableGraph graph) {

        this.graph = graph;

        visited = new boolean[graph.getNodeSize()];
        from = new int[graph.getNodeSize()];
        group = new int[graph.getNodeSize()];

        for (int i = 0; i < graph.getNodeSize(); i++) {
            visited[i] = false;
            from[i] = -1;
            group[i] = -1;
        }

        System.out.println("BFS:");
        for (int i = 0; i < graph.getNodeSize(); i++) {

            if (!visited[i]) {

                Queue<Integer> queue = new LinkedList<>();

                queue.add(i);
                visited[i] = true;
                group[i] = i;

                while (!queue.isEmpty()) {

                    int v = queue.poll();
                    System.out.print(v + " ");

                    ArrayList<Integer> relatedNodeList = graph.getRelatedNodeList(v);

                    for (Integer relatedNode : relatedNodeList) {

                        if (!visited[relatedNode]) {

                            queue.add(relatedNode);
                            visited[relatedNode] = true;
                            from[relatedNode] = v;
                            group[relatedNode] = i;

                        }

                    }

                }


            }

        }

        System.out.println();

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

            BreadthFirstSearch component = new BreadthFirstSearch(graph);

            System.out.println(component.isConnected(0, 3));
            System.out.println(component.isConnected(0, 2));
            System.out.println(component.isConnected(3, 7));
            System.out.println(component.isConnected(5, 13));

            component.showPath(3, 7);

        }

    }


}
