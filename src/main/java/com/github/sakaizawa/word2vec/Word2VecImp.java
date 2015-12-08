package com.github.sakaizawa.word2vec;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sakaisawayuya on 2015/09/03.
 */
public class Word2VecImp
    implements Word2Vec, Serializable {

    private MatrixImp inputMatrix;
    private MatrixImp outputMatrix;
    //private List<List<Node>> nodeLists;
    private List<List<HuffmanTree>> nodeLists;
    private Vocab vocab;
    private SoftMax softMax;

    /**
     * word2vecの学習：epoc指定あり
     * @param file 分かち書きされたファイル
     * @param learningStrategy HS or CBOW or com.github.sakaizawa.word2vec.SoftMax
     * @param window 窓長
     * @param size 次元
     * @param minCount 最低頻度
     * @param learningRate 学習率
     * @param epoc エポック数
     * @throws IOException
     */
    public void train(File file, LearningStrategy learningStrategy, int window, int size, int minCount, double learningRate, int epoc) throws IOException {
        for (int i = 0; i < epoc; i++) {
            train(file, learningStrategy, window, size, minCount, learningRate);
        }
    }

    /**
     * word2vecの学習：一回分
     * @param file 分かち書きされたファイル
     * @param learningStrategy HS or CBOW or com.github.sakaizawa.word2vec.SoftMax
     * @param window 窓長
     * @param size 次元
     * @param minCount 最低頻度
     * @param learningRate 学習率
     * @throws IOException
     */
    public void train(File file, LearningStrategy learningStrategy, int window, int size, int minCount, double learningRate) throws IOException {
        vocab = new Vocab(new SentenceReader(file));
        inputMatrix = new MatrixImp(vocab.vocabNum(), size);
        inputMatrix.initialize();

        if (learningStrategy.equals(learningStrategy.HS)) {
            HuffmanTreeBuilder huffmanTreeBuilder = new HuffmanTreeBuilderImpl();
            huffmanTreeBuilder.buildHuffmanTree(new SentenceReader(file), size);
            nodeLists = new ArrayList<List<HuffmanTree>>();
            for (int i = 0; i < vocab.vocabNum(); i++) {
                nodeLists.add(huffmanTreeBuilder.getNodeList(vocab.getWord(i)));
            }
        } else if (learningStrategy.equals(learningStrategy.SoftMax)) {
            outputMatrix = new MatrixImp(size, vocab.vocabNum());
            outputMatrix.initialize();
            softMax = new SoftMaxImp();
        }

        SentenceReader sentenceReader = new SentenceReader(file);
        System.out.println("start!!");
        for (String[] sentence : sentenceReader) {
            train(sentence, learningStrategy, window, size, minCount, learningRate);
        }
        System.out.println("end!!");
    }

    public void train(String[] sentence, LearningStrategy learningStrategy, int window, int size, int minCount, double learningRate) {
        for (int wordNum = 0; wordNum < sentence.length; wordNum++) {
            String word = sentence[wordNum];
            Vector errorVector = new VectorImp(size);
            Vector outputVector = null;
            if (learningStrategy.equals(learningStrategy.SoftMax)) {
                errorVector = new VectorImp(vocab.vocabNum());
                outputVector = softMax.calculateSortMax(outputMatrix, inputMatrix.getRow(vocab.getIndex(word)));
            } else if (learningStrategy.equals(learningStrategy.HS)) {
                errorVector = new VectorImp(size);
            }
            for (int i = -window; i <= window; i++) {
                if ((wordNum + i < 0) || (sentence.length <= wordNum + i) || (i == 0)) {
                    continue;
                } else {
                    String predictWord = sentence[wordNum+i];
                    if (learningStrategy.equals(learningStrategy.HS)) {
                        updateHidden(predictWord, word, learningRate);
                        calculateError(errorVector, predictWord, word);
                    } else if (learningStrategy.equals(learningStrategy.SoftMax)) {
                        Vector correctVector;
                        correctVector = createCorrectVector(vocab.getIndex(predictWord), vocab.vocabNum());
                        errorVector = calculateError(errorVector, correctVector, outputVector);
                    }
                }
            }
            if (learningStrategy.equals(learningStrategy.HS)) {
                updateInput(errorVector, word, learningRate);
            } else if (learningStrategy.equals(learningStrategy.SoftMax)) {
                updateOutputMatrix(errorVector, inputMatrix.getRow(vocab.getIndex(word)), learningRate);
                updateInputMatrix(word, errorVector, learningRate);
            }
        }
    }

    protected void updateHidden(String predictWord, String word, double learningRate) {
        List<HuffmanTree> nodeList = nodeLists.get(vocab.getIndex(predictWord));
        Vector hiddenVector = inputMatrix.getRow(vocab.getIndex(word));
        for (int nodeNum = 0; nodeNum < nodeList.size()-1; nodeNum++) {
            HuffmanTree child = nodeList.get(nodeNum);
            HuffmanTree node = nodeList.get(nodeNum+1);
            double dot = calculateDot(node.getVector(), hiddenVector);
            double sig = (double) (1 / (1 + Math.exp(-dot)));
            if (child.equals(node.getLeftChild())) {
                updateHiddenVector(node.getVector(), learningRate, sig - 1, hiddenVector);
            } else {
                updateHiddenVector(node.getVector(), learningRate, sig, hiddenVector);
            }
        }
    }

    protected void updateHiddenVector (Vector oldVector, double learningRate, double sig,  Vector hiddenVector) {
        for (int i = 0; i < oldVector.getDimension(); i++) {
            oldVector.add(i, -learningRate * sig * hiddenVector.getElement(i));
        }
    }

    /**
     * エラー計算
     * @param errorVector エラーベクトル
     * @param predictWord ノードリスト
     * @param word 中間層のベクトル（単語ベクトル）
     */
    protected void calculateError(Vector errorVector, String predictWord, String word) {
        Vector error = new VectorImp(errorVector.getDimension());
        List<HuffmanTree> nodeList = nodeLists.get(vocab.getIndex(predictWord));
        Vector hiddenVector = inputMatrix.getRow(vocab.getIndex(word));
        for (int nodeNum = 0; nodeNum < nodeList.size()-1 ; nodeNum++) {
            HuffmanTree child = nodeList.get(nodeNum);
            HuffmanTree node = nodeList.get(nodeNum + 1);
            double dot = calculateDot(node.getVector(), hiddenVector);
            double sig = calculateSigmoid(dot);
            if (child.equals(node.getLeftChild())) {
                sumErrorVector(error, sig-1, node.getVector());
            } else {
                sumErrorVector(error, sig, node.getVector());
            }
        }
        sumVector(errorVector, error);
    }

    protected void sumErrorVector (Vector errorVector, double sig,  Vector nodeVector) {
        for (int i = 0; i < errorVector.getDimension(); i++) {
            errorVector.add(i, sig * nodeVector.getElement(i));
        }
    }

    protected void sumVector (Vector errorVector, Vector error) {
        for (int i = 0; i < errorVector.getDimension(); i++) {
            errorVector.add(i, error.getElement(i));
        }
    }

    /**
     * シグモイド関数の計算
     * @param x 変数
     * @return 計算結果
     */
    protected double calculateSigmoid(double x) {
        return (1 / (1 + Math.exp(-x)));
    }
    /**
     * hidden → input の誤差伝搬
     * @param errorVector エラーベクトル
     * @param word 単語ベクトル
     * @param learningRate 学習率
     */
    protected void updateInput(Vector errorVector, String word, double learningRate) {
        int id = vocab.getIndex(word);
        for (int i = 0; i < errorVector.getDimension(); i++) {
            inputMatrix.add(id, i, -learningRate * errorVector.getElement(i));
        }
    }



    /**
     * SoftMaxの学習
     * @param sentence 文
     * @param window 窓長
     * @param size 次元
     * @param minCount 最低頻度
     * @param learningRate 学習率
     */
    public void trainSoftMax(String[] sentence, int window, int size, int minCount, double learningRate) {
        for (int wordNum = 0; wordNum < sentence.length; wordNum++) {
            String word = sentence[wordNum];
            Vector outputVector;
            outputVector = softMax.calculateSortMax(outputMatrix, inputMatrix.getRow(vocab.getIndex(word)));
            Vector errorVector = new VectorImp(vocab.vocabNum());
            for (int i = -window ; i <= window; i++) {
                if ((wordNum + i < 0) || (sentence.length <= wordNum + i) || (i == 0)) {
                    continue;
                } else {
                    String predictWord = sentence[wordNum + i];
                    Vector correctVector;
                    correctVector = createCorrectVector(vocab.getIndex(predictWord), vocab.vocabNum());
                    errorVector = calculateError(errorVector, correctVector, outputVector);
                }
            }
            updateOutputMatrix(errorVector, inputMatrix.getRow(vocab.getIndex(word)), learningRate);
            updateInputMatrix(word, errorVector, learningRate);
        }
    }

    /**
     * 正解ベクトルの生成
     * @param index 予測単語のインデックス
     * @param size 次元
     * @return 正解ベクトル
     */
    protected Vector createCorrectVector(int index, int size) {
        Vector correctVector = new VectorImp(size);
        for (int i = 0; i < size; i++) {
            if (index == i) {
                correctVector.add(i, 1);
            } else {
                correctVector.add(i, 0);
            }
        }
        return correctVector;
    }

    /**
     * エラーベクトルの計算
     * @param errorVector エラーベクトル
     * @param correctVector 正解ベクトル
     * @param outputVector SoftMaxによって出力したベクトル
     * @return エラーベクトル
     */
    protected Vector calculateError(Vector errorVector, Vector correctVector, Vector outputVector) {
        for (int i = 0; i < correctVector.getDimension(); i++) {
            errorVector.add(i, errorVector.getElement(i) + (outputVector.getElement(i) - correctVector.getElement(i)));
        }
        return errorVector;
    }

    /**
     * output → hidden への誤差伝搬
     * @param errorVector エラーベクトル
     * @param inputVector 中間層のベクトル
     * @param learningRate 学習率
     */
    protected void updateOutputMatrix(Vector errorVector, Vector inputVector, double learningRate) {
        for (int i = 0; i < outputMatrix.getRowNum(); i++) {
            for (int j = 0; j < outputMatrix.getColumnNum(); j++) {
                outputMatrix.add(i, j, -learningRate * errorVector.getElement(j) * inputVector.getElement(i));
            }
        }
    }

    /**
     * hidden → input への誤差伝搬
     * @param word 単語
     * @param errorVector　エラーベクトル
     * @param learningRate 学習率
     */
    protected void updateInputMatrix(String word, Vector errorVector, double learningRate) {
        Vector error = new VectorImp(inputMatrix.getColumnNum());
        for (int i = 0; i < errorVector.getDimension(); i++) {
            for (int j = 0; j < outputMatrix.getRowNum(); j++) {
                error.add(j, -learningRate * errorVector.getElement(i) * outputMatrix.getElement(j, i));
            }
        }
        for (int i = 0; i < error.getDimension(); i++) {
            inputMatrix.add(vocab.getIndex(word), i, error.getElement(i));
        }

    }



    /**
     * word2Vecのフォーマットでベクトルを保存
     * @param saveFile ファイルの名前
     * @throws IOException 例外処理
     */
    public void saveWord2VecFormat(File saveFile) throws IOException {
        FileWriter fileWriter = new FileWriter(saveFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter);
        for (int i = 0; i < inputMatrix.getRowNum(); i++) {
            String line;
            line = vocab.getWord(i) + " ";
            for (int j = 0; j < inputMatrix.getRow(i).getDimension(); j++) {
                line += inputMatrix.getRow(i).getElement(j) + " ";
            }
            printWriter.println(line);
        }
    }

    /**
     * その単語のベクトルを返す
     *
     * @param word 単語
     * @return ベクトル
     */
    public Vector getWordVec(String word) {
        return inputMatrix.getRow(vocab.getIndex(word));
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

    protected double calculateDot(Vector vector1, Vector vector2) {
        double dot = 0f;
        for (int i = 0; i < vector1.getDimension(); i++) {
            dot += vector1.getElement(i) * vector2.getElement(i);
        }
        return dot;
    }
}
