package com.github.sakaizawa.word2vec;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by sakaisawayuya on 2015/09/04.
 */
public class MatrixImp
    implements Matrix , Serializable{

    private double[][] matrix;

    public MatrixImp(int row, int column) {
        this.matrix = new double[row][column];
        initialize();
    }

    public MatrixImp(int row, int column, int value) {
        this.matrix = new double[row][column];
        initialize(value);
    }

    public MatrixImp(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            this.matrix[i] = matrix[i].clone();
        }
    }

    /**
     * 行列の初期化
     * @param value 初期値
     */
    public void initialize(double value) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = value;
            }
        }
    }

    /**
     * 行列の初期化
     */
    private void initialize() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = Math.random() * 2 - 1;
            }
        }
    }

    /**
     * 行列の行数を返す
     *
     * @return 行数
     */
    @Override
    public int getRowNum() {
        return matrix.length;
    }

    /**
     * 行列の列数を返す
     *
     * @return 列数
     */
    @Override
    public int getColumnNum() {
        return matrix[0].length;
    }

    /**
     * 指定された行のベクトルを返す
     *
     * @param row 行数
     * @return 指定された行のベクトル
     */
    @Override
    public Vector getRow(int row) {
        Vector vector = new VectorImp(matrix[row].length, 0);
        for (int i = 0; i < vector.getDimension(); i++) {
            vector.addElement(i, matrix[row][i]);
        }
        return vector;
    }

    /**
     * 指定された列のベクトルを返す
     *
     * @param column 列数
     * @return 指定された列のベクトル
     */
    @Override
    public Vector getColumn(int column) {
        return null;
    }

    /**
     * 行列の指定された要素を返す
     *
     * @param row    行数
     * @param column 列数
     * @return 指定された要素
     */
    @Override
    public double getElement(int row, int column) {
        return matrix[row][column];
    }

    /**
     * 行列の要素に num を加える
     *
     * @param row    行数
     * @param column 列数
     * @param num    加える数
     */
    @Override
    public void addElement(int row, int column, double num) {
        matrix[row][column] += num;
    }
}
