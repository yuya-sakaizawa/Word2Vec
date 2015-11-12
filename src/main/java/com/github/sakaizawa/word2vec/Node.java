package com.github.sakaizawa.word2vec;

import java.io.Serializable;

/**
 * Created by sakaisawayuya on 2015/09/19.
 */
public class Node
    implements Serializable{

    private String word;
    private int freq;
    private Node parent;
    private Node left;
    private Node right;
    private String label;
    public Vector vector;

    public Node (String word, int freq, Node parent, Node left, Node right, int size) {
        this.word = word;
        this.freq = freq;
        this.left = left;
        this.right = right;
        this.vector = new VectorImp(size);
        this.vector.initialize();
    }

    public Node (String word, int freq, Node parent, Node left, Node right) {
        this.word = word;
        this.freq = freq;
        this.left = left;
        this.right = right;
        this.vector = null;
    }

    public Vector getVector() { return vector; }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getWord() { return word; }

    public int getFreq() {
        return freq;
    }

    public Node getParent() {
        return parent;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public String getLabel() {
        return label;
    }
}
