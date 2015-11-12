package com.github.sakaizawa.word2vec;

import java.io.*;

/**
 * Created by sakaisawayuya on 2015/09/03.
 */
public class Demo {
    public static void main(String[] args) throws IOException {

        File inputFile = new File("/Users/sakaisawayuya/Desktop/JX/Word2Vec/data/wiki-ja-train_wakati.txt");
        //File inputFile = new File("/Users/sakaisawayuya/Desktop/JX/Word2Vec/data/head_1000000.txt");
        Word2Vec word2Vec = new Word2VecImp();
        LearningStrategy learningStrategy = LearningStrategy.HS;
        word2Vec.train(inputFile, learningStrategy, 5, 50, 0, 0.1f, 1);

        //File saveFile = new File("/Users/sakaisawayuya/Desktop/JX/com.github.sakaizawa.word2vec.Word2Vec/data/wiki-ja-train_wakati.vec");
        //word2Vec.saveWord2VecFormat(saveFile);

        word2Vec.mostSimilarity("自然", 10);
        System.out.println();

        //File saveClass = new File("/Users/sakaisawayuya/Desktop/JX/com.github.sakaizawa.word2vec.Word2Vec/data/wiki-ja-train_wakati.model");
        //ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveClass));
        //oos.writeObject(word2Vec);
    }

}
