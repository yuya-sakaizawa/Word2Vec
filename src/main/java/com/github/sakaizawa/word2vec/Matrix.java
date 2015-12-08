package com.github.sakaizawa.word2vec;

/**
 * Created by sakaisawayuya on 2015/09/04.
 */
public interface Matrix {

    /**
     * 行列の初期化
     */
    public void initialize();

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
     * 行列の要素にnumberを加える
     * @param row 行数
     * @param column 列数
     * @param number 加える数
     */
    public void add(int row, int column, double number);

    /**
     * 行列の指定された要素を返す
     * @param row 行数
     * @param column 列数
     * @return 要素
     */
    public double getElement(int row, int column);

    /**
     * 行列の指定された行を返す
     * @param row 行数
     * @return 指定された行
     */
    public Vector getRow(int row);

    /**
     * 指定された列を返す
     * @param column 列数
     * @return 指定された列
     */
    public Vector getColumn(int column);
}
