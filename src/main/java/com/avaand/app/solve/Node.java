package com.avaand.app.solve;


import java.util.ArrayList;
import java.util.List;

public class Node {

    private int value;
    private List<Node> neighbors;

    private boolean visited;

    public Node(int value){
        this.value = value;
        this.neighbors = new ArrayList<>();
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void addNode(Node node){
        this.neighbors.add(node);
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }

    public int getValue() {
        return value;
    }
}
