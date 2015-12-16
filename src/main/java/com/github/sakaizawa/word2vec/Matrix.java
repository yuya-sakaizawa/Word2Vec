package com.github.sakaizawa.word2vec;

/**
 * Created by sakaisawayuya on 2015/09/04.
 */
public interface Matrix {

    /**
     * 行列の行数を返す
     * @return 行数
     */
    public int getRowNum();

    /**
     * 行列の列数を返す
     * @return 列数
     */
    public int getColumnNum();


    /**
     * 指定された行のベクトルを返す
     * @param row 行数
     * @return 指定された行のベクトル
     */
    public Vector getRow(int row);

    /**
     * 指定された列のベクトルを返す
     * @param column 列数
     * @return 指定された列のベクトル
     */
    public Vector getColumn(int column);

    /**
     * 行列の指定された要素を返す
     * @param row 行数
     * @param column 列数
     * @return 指定された要素
     */
    public double getElement(int row, int column);

    /**
     * 行列の要素に num を加える
     * @param row 行数
     * @param column 列数
     * @param num 加える数
     */
    public void addElement(int row, int column, double num);
}
