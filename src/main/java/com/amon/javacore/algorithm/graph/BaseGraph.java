package com.amon.javacore.algorithm.graph;

/**
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2020/12/30.
 */
public interface BaseGraph {

    /**
     * add edge in the graph
     *
     * @param from     start node
     * @param to       end node
     * @param directed Is it a directed garph
     */
    public void addEdge(int from, int to, boolean directed);


    /**
     * add weight edge in the graph
     *
     * @param from     start node
     * @param to       end node
     * @param directed Is it a directed garph
     * @param weight   weight
     */
    public void addWeightEdge(int from, int to, boolean directed, double weight);

    /**
     * Is it a edge between two nodes
     *
     * @param from     start node
     * @param to       end node
     * @param directed Is it a directed garph
     * @return
     */
    public boolean hasEdge(int from, int to, boolean directed);

    /**
     * show graph
     */
    public void showGraph();

    /**
     * Is it a directed graph
     *
     * @return
     */
    public boolean directed();

}
