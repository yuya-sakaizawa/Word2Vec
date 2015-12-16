package com.github.sakaizawa.word2vec;

import java.util.Observable;

/**
 * Created by sakaisawayuya on 2015/09/06.
 */
public interface Vector {

    /**
     * ベクトルを返す
     * @return ベクトル
     */
    public double[] getVector();

    /**
     * ベクトルの次元数を返す
     * @return ベクトルの次元数
     */
    public int getDimension();


    /**
     * ベクトルの大きさを返す
     * @return ベクトルの大きさ
     */
    public double getSize();

    /**
     * index 次元の値を返す
     * @param index 次元
     * @return index 次元の値
     */
    public double getElement(int index);

    /**
     * index 次元の値に num を加える
     * @param index インデックス
     * @param num 加える数
     */
    public void addElement(int index, double num);


    //public boolean equals(Observable obj);
}
