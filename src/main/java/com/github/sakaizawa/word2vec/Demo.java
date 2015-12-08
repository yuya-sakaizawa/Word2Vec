package com.github.sakaizawa.word2vec;

import java.io.*;

/**
 * Created by sakaisawayuya on 2015/09/03.
 */
public class Demo {
    public static void main(String[] args) throws IOException {

        //File inputFile = new File("/Users/sakaisawayuya/Desktop/JX/Word2Vec/data/wiki-ja-train_wakati.txt");
        File inputFile = new File("/Users/sakaisawayuya/Desktop/JX/Word2Vec/data/head_200000.txt");
        // サーバ用
        //File inputFile = new File("/work/sakaizawa/JX_demo/data/wiki-ja-train_wakati.txt");
        //File inputFile = new File("/work/sakaizawa/JX_demo/data/corpus.txt");
        Word2Vec word2Vec = new Word2VecImp();
        LearningStrategy learningStrategy = LearningStrategy.HS;
        word2Vec.train(inputFile, learningStrategy, 5, 50, 0, 0.025, 1);

        //File saveFile = new File("/Users/sakaisawayuya/Desktop/JX/Word2Vec/data/wiki-ja-train_wakati.vec");
        //File saveFile = new File("/Users/sakaisawayuya/Desktop/JX/Word2Vec/data/head_200000.vec");
        // サーバ用
        //File saveFile = new File("/work/sakaizawa/JX_demo/data/wiki-ja-train_wakati.vec");
        //File saveFile = new File("/work/sakaizawa/JX_demo/data/corpus.vec");
        //word2Vec.saveWord2VecFormat(saveFile);

        //File saveClass = new File("/Users/sakaisawayuya/Desktop/JX/Word2Vec/data/wiki-ja-train_wakati.model");
        //File saveClass = new File("/Users/sakaisawayuya/Desktop/JX/Word2Vec/data/head_200000.model");
        // サーバ用
        //File saveClass = new File("/work/sakaizawa/JX_demo/data/wiki-ja-train_wakati.model");
        //File saveClass = new File("/work/sakaizawa/JX_demo/data/corpus.model");
        //ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveClass));
        //oos.writeObject(word2Vec);

        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(is);
        String con = "no";
        do {
            System.out.println("文字列を入力してください：");
            String word = br.readLine();
            word2Vec.mostSimilarity(word, 10);
            System.out.println("続けますか：yes/no:");
            con = br.readLine();
        } while (con.equals("yes"));


    }

}
