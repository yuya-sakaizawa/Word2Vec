package com.github.sakaizawa.word2vec;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by sakaisawayuya on 2015/11/22.
 */
public class Word2VecImpTest extends Word2VecImp {

    // updatehidden などは word2vec が inputmatrix をもっているので返り値なしにしているが
    // test ケース的にはベクトルを受け取ってベクトルを返すほうが自然？？
    private MatrixImp inputMatrix;
    private List<List<HuffmanTree>> nodeLists;
    private Vocab vocab;
    private Word2Vec word2Vec;


    public Word2VecImpTest () throws IOException {
        File file = new File("/Users/sakaisawayuya/Desktop/JX/Word2Vec/data/word2vec_test");
        word2Vec = new Word2VecImp();
        LearningStrategy learningStrategy = LearningStrategy.HS;
        word2Vec.train(file, learningStrategy, 5, 50, 0, 0.025, 1);
        System.out.println();
    }


    @Test
    public void testUpdateHidden() throws Exception {

    }

    @Test
    public void testSumVector() throws Exception {
        Vector vector1 = new VectorImp(2);
        Vector vector2 = new VectorImp(2);
        vector1.add(0, 1);
        vector1.add(1, 1);
        vector2.add(0, 2);
        vector2.add(1, 2);
        sumVector(vector1, vector2);
        assertEquals(vector1.getElement(0), 3.0f, 0.001);
        assertEquals(vector1.getElement(1), 3.0f, 0.001);
    }

    @Test
    public void testCalculateDot() {
        double dot1 = 2.0f;
        double dot2 = 4.0f;
        Vector vector1 = new VectorImp(2);
        Vector vector2 = new VectorImp(2);
        vector1.add(0, 1);
        vector1.add(1, 1);
        vector2.add(0, 2);
        vector2.add(1, 2);
        assertEquals(calculateDot(vector1, vector1), dot1, 0.001);
        assertEquals(calculateDot(vector1, vector2), dot2, 0.002);
    }
    
    @Test
    public void testCalculateSigmoid() {
        assertEquals(calculateSigmoid(0), 0.5, 0.001);
    }
}