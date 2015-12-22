package com.github.sakaizawa.word2vec;

import java.io.Serializable;

/**
 * Created by sakaisawayuya on 2015/11/11.
 */
public class HuffmanTreeImpl
    implements HuffmanTree, Serializable{

    private int number;
    private String label;
    private HuffmanTree parent;
    private HuffmanTree rightChild;
    private HuffmanTree leftChild;
    private int freq;
    private Vector vector;

    public HuffmanTreeImpl (int number, int size) {
        this.number = number;
        this.vector = new VectorImp(size);
    }

    public HuffmanTreeImpl (int number, int size, double value) {
        this.number = number;
        this.vector = new VectorImp(size, value);
    }

    public HuffmanTreeImpl (String label, int freq, int size) {
        this.number = -1;
        this.label = label;
        this.freq = freq;
        this.vector = new VectorImp(size);
    }

    public HuffmanTreeImpl (String label, int freq, int size, double value) {
        this.number = -1;
        this.label = label;
        this.freq = freq;
        this.vector = new VectorImp(size, value);
    }

    /**
     * リーフノードか否か
     *
     * @return リーフノード → True，それ以外 → False
     */
    @Override
    public boolean isLeaf() {
        return this.rightChild == null;
    }

    /**
     * ROOT node か否か
     *
     * @return ROOT node → True, otherwise → False
     */
    @Override
    public boolean isROOT() {
        return this.parent == null;
    }

    /**
     * 現在のノードに子ノードを加える
     * @param leftChild 左の子ノード
     * @param rightChild 右の子ノード
     */
    @Override
    public void addNode(HuffmanTree leftChild, HuffmanTree rightChild) {
        setLeftChild(leftChild);
        setRightChild(rightChild);
        setFreq(leftChild.getFreq()+rightChild.getFreq());
        leftChild.setParent(this);
        rightChild.setParent(this);
    }

    /**
     * ノード番号を返す
     * @return ノード番号
     */
    public int getNumber() { return this.number; }

    /**
     * ラベルを返す
     * @return ラベル
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * 親ノードを返す
     *
     * @return 親ノード
     */
    @Override
    public HuffmanTree getParent() {
        return this.parent;
    }

    /**
     * 右にある子ノードを返す
     *
     * @return 右にある子ノード
     */
    @Override
    public HuffmanTree getRightChild() {
        return this.rightChild;
    }

    /**
     * 左にある子ノードを返す
     *
     * @return 左の子ノード
     */
    @Override
    public HuffmanTree getLeftChild() {
        return this.leftChild;
    }

    /**
     * 頻度を返す
     * @return 頻度
     */
    @Override
    public int getFreq() {
        return this.freq;
    }

    /**
     * そのノードが持つベクトルを返す
     *
     * @return ベクトル
     */
    @Override
    public Vector getVector() {
        return this.vector;
    }

    /**
     * 親ノードをセット
     *
     * @param parent
     */
    @Override
    public void setParent(HuffmanTree parent) {
        this.parent = parent;
    }

    /**
     * 右の子ノードをセット
     *
     * @param rightChild
     */
    @Override
    public void setRightChild(HuffmanTree rightChild) {
        this.rightChild = rightChild;
    }

    /**
     * 左の子ノードをセット
     *
     * @param leftChild
     */
    @Override
    public void setLeftChild(HuffmanTree leftChild) {
        this.leftChild = leftChild;
    }

    /**
     * 頻度をセット
     * @param freq 頻度
     */
    @Override
    public void setFreq(int freq) {
        this.freq = freq;
    }
}
