package com.github.sakaizawa.word2vec;

import java.io.*;
import java.util.*;

/**
 * Created by sakaisawayuya on 2015/09/03.
 */
public class Word2VecImp
        implements Word2Vec, Serializable {

    private Matrix inputMatrix;
    private List<List<HuffmanTree>> nodeLists = new ArrayList<List<HuffmanTree>>();;
    private Vocab vocab;

    /**
     * word2vec の学習:epoc 指定あり
     *
     * @param file             分かち書きされたファイル
     * @param learningStrategy HS or CBOW
     * @param window           窓長
     * @param size             次元
     * @param minCount         最低頻度
     * @param learningRate     学習率
     * @param epoc             エポック
     * @throws IOException 例外処理
     */
    @Override
    public void train(File file, LearningStrategy learningStrategy, int window, int size, int minCount, double learningRate, int epoc) throws IOException {
        for (int i = 0; i < epoc; i++) {
            train(file, learningStrategy, window, size, minCount, learningRate);
        }
    }

    /**
     * word2vec の学習:一回分
     *
     * @param file             分かち書きされたファイル
     * @param learningStrategy HS or Negative(未実装)
     * @param window           窓長
     * @param size             次元
     * @param minCount         最低頻度
     * @param learningRate     学習率
     * @throws IOException 例外処理
     */
    @Override
    public void train(File file, LearningStrategy learningStrategy, int window, int size, int minCount, double learningRate) throws IOException {
        this.vocab = new Vocab(new SentenceReader(file));
        this.inputMatrix = new MatrixImp(vocab.vocabNum(), size);
        HuffmanTreeBuilder huffmanTreeBuilder = new HuffmanTreeBuilderImpl();
        huffmanTreeBuilder.buildHuffmanTree(new SentenceReader(file), size);
        for (int i = 0; i < vocab.vocabNum(); i++) {
            nodeLists.add(huffmanTreeBuilder.getNodeList(vocab.getWord(i)));
        }

        SentenceReader sentenceReader = new SentenceReader(file);
        System.out.println("start");
        for (String[] sentence : sentenceReader) {
            train(sentence, learningStrategy, window, size, minCount, learningRate);
        }
        System.out.println("end");
    }

    void train(String[] sentence, LearningStrategy learningStrategy, int window, int size, int minCount, double learningRate) throws IOException {
        for (int wordNum = 0; wordNum < sentence.length; wordNum++) {
            String word = sentence[wordNum];
            Vector errorVector = new VectorImp(size);
            Map<HuffmanTree, Double> errorNode = new HashMap<HuffmanTree, Double>();
            for (int i = -window; i <= window; i++) {
                if ((wordNum + i < 0) || (sentence.length <= wordNum + i) || (i == 0)) {
                    continue;
                } else {
                    String predictWord = sentence[wordNum + i];
                    calculateErrorNode(errorNode, predictWord, word);
                }
            }
            calculateError(errorVector, errorNode);
            updateNode(errorNode, word, learningRate);
            updateInput(errorVector, word, learningRate);
        }
    }

    void updateInput(Vector errorVector, String word, double learningRate) {
        int index = vocab.getIndex(word);
        for (int i = 0; i < errorVector.getDimension(); i++) {
            inputMatrix.addElement(index, i, -learningRate * errorVector.getElement(i));
        }
    }

    void updateNode(Map<HuffmanTree, Double> errorNode, String word, double learningRate) {
        Vector hiddenVector = inputMatrix.getRow(vocab.getIndex(word));
        for (HuffmanTree node : errorNode.keySet()) {
            for (int i = 0; i < hiddenVector.getDimension(); i++) {
                node.getVector().addElement(i, -learningRate * errorNode.get(node) * hiddenVector.getElement(i));
            }
        }
    }

    void calculateError(Vector errorVector, Map<HuffmanTree, Double> errorNode) {
        for (HuffmanTree node : errorNode.keySet()) {
            for (int i = 0; i < errorVector.getDimension(); i++) {
                errorVector.addElement(i, errorNode.get(node) * node.getVector().getElement(i));
            }
        }
    }

    void calculateErrorNode(Map<HuffmanTree, Double> errorNode, String predictWord, String word) {
        List<HuffmanTree> nodeList = nodeLists.get(vocab.getIndex(predictWord));
        Vector hiddenVector = inputMatrix.getRow(vocab.getIndex(word));
        for (int nodeNum = nodeList.size()-1; nodeNum > 0 ; nodeNum--) {
            HuffmanTree node = nodeList.get(nodeNum);
            HuffmanTree child = nodeList.get(nodeNum-1);
            double dot = calculateDot(node.getVector(), hiddenVector);
            double sig = calculateSigmoid(dot);
            if (child.equals(node.getLeftChild())) {
                if (errorNode.containsKey(node)) {
                    errorNode.put(node, errorNode.get(node) + (sig -1));
                } else {
                    errorNode.put(node, (sig -1));
                }
            } else {
                if (errorNode.containsKey(node)) {
                    errorNode.put(node, errorNode.get(node) + sig);
                } else {
                    errorNode.put(node, sig);
                }
            }
        }
    }

    double calculateDot (Vector vector1, Vector vector2) {
        double dot = 0f;
        for (int i = 0; i < vector1.getDimension(); i++) {
            dot += vector1.getElement(i) * vector2.getElement(i);
        }
        return dot;
    }

    double calculateSigmoid(double x) {
        return (1 / (1 + Math.exp(-x)));
    }

    /**
     * 二つの単語の類似度を返す
     *
     * @param word_1
     * @param word_2
     * @return 単語間の類似度
     */
    public double similarity(String word_1, String word_2) {
        Vector vector_1 = inputMatrix.getRow(vocab.getIndex(word_1));
        Vector vector_2 = inputMatrix.getRow(vocab.getIndex(word_2));
        double similarity = 0.0f;
        for (int i = 0; i < vector_1.getDimension(); i++) {
            similarity += vector_1.getElement(i) * vector_2.getElement(i);
        }
        similarity /= vector_1.getSize();
        similarity /= vector_2.getSize();
        return similarity;
    }

    /**
     * wordの類似度が近い順にtopn個出す
     *
     * @param word 単語
     * @param topN 個数
     */
    public void mostSimilarity(String word, int topN) {
        int index = 1;
        double max = 0.0f;
        for (String key : vocab.getVocab().keySet()) {
            if (similarity(word, key) >= max && !key.equals(word)) {
                System.out.print(similarity(key, word));
                System.out.println("　　　"+key);
                index = vocab.getIndex(key);
                max = similarity(word, key);
            }
        }
        System.out.println(vocab.getWord(index));
        System.out.println(max);
    }
}
