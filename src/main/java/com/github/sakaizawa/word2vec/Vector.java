package com.github.sakaizawa.word2vec;

/**
 * Created by sakaisawayuya on 2015/09/06.
 */
public interface Vector {

    /**
     *  ベクトルを初期化する
     */
    public void initialize();

    /**
     * ベクトルを返す
     * @return ベクトル
     */
    public double[] getVector();

    /**
     * 指定されたindexにnumberを加える
     * @param index
     * @param number 加える数
     */
    public void add(int index, double number);

    /**
     * ベクトルの大きさを返す
     * @return ベクトルの大きさ
     */
    public double getSize();

    /**
     * ベクトルの指定されたindexの値を返す
     * @param index
     * @return ベクトルの指定されたindexの値
     */
    public double getElement(int index);

    /**
     * ベクトルの大きさを返す
     * @return ベクトルの大きさ
     */
    public int getDimension();

}
