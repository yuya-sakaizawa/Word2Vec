package com.github.sakaizawa.word2vec;

import java.io.Serializable;

/**
 * Created by sakaisawayuya on 2015/09/04.
 */
public class MatrixImp
    implements Matrix , Serializable{

    private double[][] matrix;

    /**
     * 行数と列数を受け取って行列を生成
     * @param row 行数
     * @param column 列数
     */
    public MatrixImp(int row, int column) {
        matrix = new double[row][column];
    }

    /**
     * 行列の初期化
     */
    public void initialize() {
        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix[i].length; j++) {
                matrix[i][j] = (double) (Math.random()*2-1);
            }
        }
    }

    /**
     * 行列の行数を返す
     *
     * @return 行数
     */
    public int getRowNum() {
        return matrix.length;
    }

    /**
     * 行列の列数を返す
     *
     * @return 列数
     */
    public int getColumnNum() {
        return matrix[0].length;
    }

    /**
     * 行列の要素にnumberを加える
     *
     * @param row    行数
     * @param column 列数
     * @param number 加える数
     */
    public void add(int row, int column, double number) {
        matrix[row][column] += number;
    }

    /**
     * 行列の指定された要素を返す
     *
     * @param row    行数
     * @param column 列数
     * @return 要素
     */
    public double getElement(int row, int column) {
        return matrix[row][column];
    }

    /**
     * 行列の指定された行を返す
     *
     * @param row 行数
     * @return 指定された行
     */
    public Vector getRow(int row) {
        Vector rowVector = new VectorImp(matrix[0].length);
        for (int i = 0; i < matrix[0].length; i++) {
            rowVector.add(i, matrix[row][i]);
        }
        return rowVector;
    }

    /**
     * 指定された列を返す
     *
     * @param column 列数
     * @return 指定された列
     */
    public Vector getColumn(int column) {
        Vector columnVector = new VectorImp(matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            columnVector.add(i, matrix[i][column]);
        }
        return columnVector;
    }
}
