package com.github.sakaizawa.word2vec;

import java.io.*;
import java.util.*;

/**
 * Created by sakaisawayuya on 2015/09/03.
 */
public class Word2VecImp
        implements Word2Vec, Serializable {

    Matrix inputMatrix;
    Matrix outputMatrix;
    HuffmanTreeBuilder huffmanTreeBuilder;
    Vocab vocab;

    public Word2VecImp () {}

    public Word2VecImp(Matrix inputMatrix, Matrix outputMatrix, Vocab vocab) {
        this.inputMatrix = inputMatrix;
        this.outputMatrix = outputMatrix;
        this.vocab = vocab;
    }

    public Word2VecImp(Matrix inputMatrix, Matrix outputMatrix, HuffmanTreeBuilder huffmanTreeBuilder, Vocab vocab) {
        this.inputMatrix = inputMatrix;
        this.outputMatrix = outputMatrix;
        this.huffmanTreeBuilder = huffmanTreeBuilder;
        this.vocab = vocab;
    }

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
        this.outputMatrix = new MatrixImp(size, vocab.vocabNum()-1);
        this.huffmanTreeBuilder = new HuffmanTreeBuilderImpl();
        huffmanTreeBuilder.buildHuffmanTree(new SentenceReader(file), size);

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
            Map<Integer, Double> updateNumbers = new HashMap<Integer, Double>();
            for (int i = -window; i <= window; i++) {
                if ((wordNum + i < 0) || (sentence.length <= wordNum + i) || (i == 0)) {
                    continue;
                } else {
                    String predictWord = sentence[wordNum + i];
                    calculateUpdateNumbers(updateNumbers, predictWord, word);
                }
            }
            calculateError(errorVector, updateNumbers);
            updateOutput(updateNumbers, word, learningRate);
            updateInput(errorVector, word, learningRate);
        }
    }

    void updateInput(Vector errorVector, String word, double learningRate) {
        int index = vocab.getIndex(word);
        for (int i = 0; i < errorVector.getDimension(); i++) {
            inputMatrix.addElement(index, i, -learningRate * errorVector.getElement(i));
        }
    }

    void updateOutput(Map<Integer, Double> updateNumbers, String word, double learningRate) {
        Vector hiddenVector = inputMatrix.getRow(vocab.getIndex(word));
        for (Integer num : updateNumbers.keySet()) {
            for (int i = 0; i < hiddenVector.getDimension(); i++) {
                outputMatrix.addElement(i, num, -learningRate * updateNumbers.get(num) * hiddenVector.getElement(i));
            }
        }
    }

    void calculateError(Vector errorVector, Map<Integer, Double> updateNumbers) {
        for (Integer num : updateNumbers.keySet()) {
            for (int i = 0; i < errorVector.getDimension(); i++) {
                errorVector.addElement(i, updateNumbers.get(num) * outputMatrix.getElement(i, num));
            }
        }
    }

    void calculateUpdateNumbers(Map<Integer, Double> updateNumbers, String predictWord, String word) {
        List<HuffmanTree> nodeList = huffmanTreeBuilder.getNodeList(predictWord);
        Vector hiddenVector = inputMatrix.getRow(vocab.getIndex(word));
        for (int nodeNum = nodeList.size()-1; nodeNum > 0 ; nodeNum--) {
            HuffmanTree node = nodeList.get(nodeNum);
            HuffmanTree child = nodeList.get(nodeNum-1);
            double dot = calculateDot(outputMatrix.getColumn(node.getNumber()), hiddenVector);
            double sig = calculateSigmoid(dot);
            if (child.equals(node.getLeftChild())) {
                if (updateNumbers.containsKey(node.getNumber())) {
                    updateNumbers.put(node.getNumber(), updateNumbers.get(node.getNumber()) + (sig -1));
                } else {
                    updateNumbers.put(node.getNumber(), (sig - 1));
                }
            } else {
                if (updateNumbers.containsKey(node.getNumber())) {
                    updateNumbers.put(node.getNumber(), updateNumbers.get(node.getNumber()) + sig);
                } else {
                    updateNumbers.put(node.getNumber(), sig);
                }
            }
        }
    }


    double calculateDot (Vector vector1, Vector vector2) {
        double dot = 0;
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
        int index = 0;
        Map<String, Double> similarity = new HashMap<String, Double>();
        for (String key : vocab.getVocab().keySet()) {
            similarity.put(key, similarity(key, word));
        }
        Map<String, Double> sortMap = sortByComparator(similarity);
        sortMap.remove(word);
        for (String key : sortMap.keySet()) {
            if (index < topN) {
                System.out.println(key+" "+sortMap.get(key));
            } else {
                break;
            }
            index++;
        }
    }

    private static Map<String, Double> sortByComparator(Map<String, Double> unsortMap) {

        // Convert Map to List
        List<Map.Entry<String, Double>> list =
                new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());

        // Sort list with comparator, to compare the Map values
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Collections.reverse(list);

        // Convert sorted map back to a Map
        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Iterator<Map.Entry<String, Double>> it = list.iterator(); it.hasNext();) {
            Map.Entry<String, Double> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
