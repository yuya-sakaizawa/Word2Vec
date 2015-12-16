package com.github.sakaizawa.word2vec;

import java.io.File;
import java.io.IOException;

/**
 * Created by sakaisawayuya on 2015/09/03.
 */
public interface Word2Vec {

    /**
     * word2vec の学習:epoc 指定あり
     * @param file 分かち書きされたファイル
     * @param learningStrategy HS or CBOW
     * @param window 窓長
     * @param size 次元
     * @param minCount 最低頻度
     * @param learningRate 学習率
     * @param epoc エポック
     * @throws IOException 例外処理
     */
    public void train(File file, LearningStrategy learningStrategy, int window, int size, int minCount,
                                double learningRate, int epoc) throws IOException;

    /**
     * word2vec の学習:一回分
     * @param file 分かち書きされたファイル
     * @param learningStrategy HS or CBOW
     * @param window 窓長
     * @param size 次元
     * @param minCount 最低頻度
     * @param learningRate 学習率
     * @throws IOException 例外処理
     */
    public void train(File file, LearningStrategy learningStrategy, int window, int size, int minCount,
                      double learningRate) throws IOException;


    public void mostSimilarity(String word, int topN);
}
