package com.github.sakaizawa.word2vec;

/**
 * Created by sakaisawayuya on 2015/11/11.
 */
public interface HuffmanTree {

    /**
     * リーフノードか否か
     * @return リーフノード → True，それ以外 → False
     */
    public boolean isLeaf();

    /**
     * ROOT node か否か
     * @return ROOT node → True, otherwise → False
     */
    public boolean isROOT();

    /**
     * 現在のノードに子ノードを加える
     * @param leftChild 左の子ノード
     * @param rightChild 右の子ノード
     */
    public void addNode(HuffmanTree leftChild, HuffmanTree rightChild);

    /**
     * ノード番号を返す
     * @return ノード番号
     */
    public int getNumber();

    /**
     * ラベルを返す
     * @return ラベル
     */
    public String getLabel();

    /**
     * 親ノードを返す
     * @return 親ノード
     */
    public HuffmanTree getParent();

    /**
     * 右にある子ノードを返す
     * @return 右にある子ノード
     */
    public HuffmanTree getRightChild();

    /**
     * 左にある子ノードを返す
     * @return 左の子ノード
     */
    public HuffmanTree getLeftChild();

    /**
     * 頻度を返す
     * @return 頻度
     */
    public int getFreq();

    /**
     * そのノードが持つベクトルを返す
     * @return ベクトル
     */
    public Vector getVector();

    /**
     * 親ノードをセット
     * @param parent
     */
    public void setParent(HuffmanTree parent);

    /**
     * 右の子ノードをセット
     * @param rightChild
     */
    public void setRightChild(HuffmanTree rightChild);

    /**
     * 左の子ノードをセット
     * @param leftChild
     */
    public void setLeftChild(HuffmanTree leftChild);

    /**
     * 頻度をセット
     * @param freq 頻度
     */
    public void setFreq(int freq);
}

