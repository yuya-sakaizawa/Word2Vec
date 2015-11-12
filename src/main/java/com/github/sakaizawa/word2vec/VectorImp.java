package com.github.sakaizawa.word2vec;

import java.io.Serializable;

/**
 * Created by sakaisawayuya on 2015/09/06.
 */
public class VectorImp
    implements Vector, Serializable{

    private float[] vector;

    /**
     * 次元数を受け取って vector を生成する
     * @param size 次元数
     */
    public VectorImp(int size) {
        vector = new float[size];
    }

    /**
     * ベクトルを初期化する
     */
    public void initialize() {
        for (int i=0; i<vector.length; i++) {
            vector[i] = (float) (Math.random()*2-1);
        }
    }

    /**
     * 指定されたindexにnumberを加える
     *
     * @param index
     * @param number 加える数
     */
    public void add(int index, float number) {
        vector[index] += number;
    }

    /**
     * ベクトルを返す
     *
     * @return ベクトル
     */
    public float[] getVector() {
        return vector;
    }

    /**
     * ベクトルの大きさを返す
     *
     * @return ベクトルの大きさ
     */
    public float getSize() {
        double magnitude = 0;
        for (int i = 0; i < vector.length; i++) {
            magnitude += Math.pow((double)vector[i], 2);
        }
        return (float) Math.sqrt(magnitude);
    }

    /**
     * ベクトルの指定されたindexの値を返す
     *
     * @param index
     * @return ベクトルの指定されたindexの値
     */
    public float getElement(int index) {
        return vector[index];
    }

    /**
     * ベクトルの次元を返す
     *
     * @return ベクトルの次元
     */
    public int getDimension() {
        return vector.length;
    }
}