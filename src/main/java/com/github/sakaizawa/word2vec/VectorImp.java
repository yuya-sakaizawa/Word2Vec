package com.github.sakaizawa.word2vec;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Observable;

/**
 * Created by sakaisawayuya on 2015/09/06.
 */
public class VectorImp
    implements Vector, Serializable{

    private double[] vector;

    /**
     * size 次元で初期値が指定されていない（ランダム）なベクトル
     * @param size 次元
     */
    public VectorImp(int size) {
        this.vector = new double[size];
        initialize();
    }

    /**
     * size 次元で初期値が指定されているベクトル
     * @param size 次元
     * @param value 初期値
     */
    public VectorImp(int size, double value) {
        this.vector = new double[size];
        initialize(value);
    }

    /**
     * ベクトルをそのまま受け取る
     * @param vector ベクトル
     */
    public VectorImp(double[] vector) {
        this.vector = Arrays.copyOf(vector, vector.length);
    }

    /**
     * ベクトルを初期化する
     */
    private void initialize() {
        for (int i = 0; i < vector.length; i++) {
            vector[i] = Math.random() * 2 - 1;
        }
    }

    private void initialize(double value) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] = value;
        }
    }

    /**
     * ベクトルを返す
     *
     * @return ベクトル
     */
    @Override
    public double[] getVector() {
        return vector;
    }

    /**
     * ベクトルの次元数を返す
     *
     * @return ベクトルの次元数
     */
    @Override
    public int getDimension() {
        return vector.length;
    }

    /**
     * ベクトルの大きさを返す
     *
     * @return ベクトルの大きさ
     */
    @Override
    public double getSize() {
        double size = 0;
        double sum = 0;
        for (int i = 0; i < vector.length; i++) {
            sum += vector[i] * vector[i];
        }
        return Math.sqrt(sum);
    }

    /**
     * index 次元の値を返す
     *
     * @param index 次元
     * @return index 次元の値
     */
    @Override
    public double getElement(int index) {
        return vector[index];
    }

    /**
     * index 次元の値に num を加える
     *
     * @param index インデックス
     * @param num   加える数
     */
    @Override
    public void addElement(int index, double num) {
        vector[index] += num;
    }

    /*
    public boolean equals(Observable obj) {
        if (obj == null) {
            return false;
        }
        if(!(obj instanceof  Vector)) {
            return false;
        }
        return this.vector == ((Vector)obj).getVector();
    }
    */
}
