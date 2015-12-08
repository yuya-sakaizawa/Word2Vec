package com.github.sakaizawa.word2vec;

import java.io.File;
import java.io.IOException;

/**
 * Created by sakaisawayuya on 2015/09/03.
 */
public interface Word2Vec {

    /**
     * word2vecの学習：epoc指定あり
     * @param file 分かち書きされたファイル
     * @param learningStrategy HS or CBOW or com.github.sakaizawa.word2vec.SoftMax
     * @param window 窓長
     * @param size 次元
     * @param minCount 最低頻度
     * @param learningRate 学習率
     * @param epoc エポック数
     * @throws IOException 例外処理
     */
    public void train(File file, LearningStrategy learningStrategy, int window, int size, int minCount, double learningRate, int epoc) throws IOException;

    public void train(String[] sentence, LearningStrategy learningStrategy, int window, int size, int minCount, double learningRate);

    /**
     * word2vecの学習：一回分
     * @param file 分かち書きされたファイル
     * @param learningStrategy HS or CBOW or com.github.sakaizawa.word2vec.SoftMax
     * @param window 窓長
     * @param size 次元
     * @param minCount 最低頻度
     * @param learningRate 学習率
     * @throws IOException 例外処理
     */
    public void train(File file, LearningStrategy learningStrategy, int window, int size, int minCount, double learningRate) throws IOException;

    /**
     * SoftMaxの学習
     * 階層的SoftMaxの学習
     * @param sentence 文
     * @param window 窓長
     * @param size 次元
     * @param minCount 最低頻度
     * @param learningRate 学習率
     */
    public void trainSoftMax(String[] sentence, int window, int size, int minCount, double learningRate);

    /**
     * word2Vecのフォーマットでベクトルを保存
     * @param saveFile ファイルの名前
     * @throws IOException 例外処理
     */
    public void saveWord2VecFormat(File saveFile) throws IOException;

    /**
     * その単語のベクトルを返す
     * @param word 単語
     * @return ベクトル
     */
    public Vector getWordVec(String word);

    /**
     * 二つの単語の類似度を返す
     * @param word_1
     * @param word_2
     * @return 単語間の類似度
     */
    public double similarity(String word_1, String word_2);

    /**
     * wordの類似度が近い順にtopn個出す
     * @param word 単語
     * @param topN 個数
     */
    public void mostSimilarity(String word, int topN);
}
