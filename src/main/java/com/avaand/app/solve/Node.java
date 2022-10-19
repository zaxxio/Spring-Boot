package com.avaand.app.solve;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Node {

    private final int value;
    private final List<Node> neighbors = new ArrayList<>();
    private boolean visited;

    public Node(int value){
        this.value = value;
    }

    public boolean isVisited() {
        return !visited;
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
