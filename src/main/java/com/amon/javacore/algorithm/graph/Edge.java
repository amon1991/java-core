package com.amon.javacore.algorithm.graph;

/**
 * 带权边
 *
 * @author yaming.chen@siemens.com
 * Created by chenyaming on 2021/1/15.
 */
public class Edge {

    private Integer fromNode;

    private Integer toNode;

    private double weight;

    public Edge(Integer fromNode, Integer toNode, double weight) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.weight = weight;
    }

    public Integer getFromNode() {
        return fromNode;
    }

    public void setFromNode(Integer fromNode) {
        this.fromNode = fromNode;
    }

    public Integer getToNode() {
        return toNode;
    }

    public void setToNode(Integer toNode) {
        this.toNode = toNode;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "fromNode=" + fromNode +
                ", toNode=" + toNode +
                ", weight=" + weight +
                '}';
    }
}
