package com.github.sakaizawa.word2vec;

import com.sun.source.tree.AssertTree;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by sakaisawayuya on 2015/12/21.
 */
public class Word2VecImpTest {

    public Word2VecImp word2Vec;

    public Word2VecImpTest() throws FileNotFoundException {
        File testFile = new File("/Users/sakaisawayuya/Desktop/JX/Word2Vec/data/TestHuffmanTreeBuilder");
        double input_initialize[][] = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
        double output_initialize[][] = {{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}};
        HuffmanTreeBuilder huffmanTreeBuilder = new HuffmanTreeBuilderImpl();
        huffmanTreeBuilder.buildHuffmanTree(new SentenceReader(testFile), 3, 1);
        Vocab vocab = new Vocab(new SentenceReader(testFile));
        word2Vec = new Word2VecImp(new MatrixImp(input_initialize), new MatrixImp(output_initialize), huffmanTreeBuilder, vocab);
    }

    @Test
    public void testUpdateInput() throws Exception {
        double error[] = {1, 2, 3};
        Vector errorVector = new VectorImp(error);
        word2Vec.updateInput(errorVector, "a", 0.025);
        assertEquals(word2Vec.inputMatrix.getElement(0, 0), 0.975, 0.001);
        assertEquals(word2Vec.inputMatrix.getElement(0, 1), 0.950, 0.001);
        assertEquals(word2Vec.inputMatrix.getElement(0, 2), 0.925, 0.001);

    }

    @Test
    public void testUpdateOutput() throws Exception {
        Map<Integer, Double> updateNumbers = new HashMap<Integer, Double>();
        updateNumbers.put(0, 0.1);
        word2Vec.updateOutput(updateNumbers, "a", 0.025);
        assertEquals(word2Vec.outputMatrix.getElement(0, 0), 0.9975, 0.001);
        assertEquals(word2Vec.outputMatrix.getElement(1, 0), 0.9975, 0.001 );
        assertEquals(word2Vec.outputMatrix.getElement(2, 0), 0.9975, 0.001 );

    }

    @Test
    public void testCalculateError() throws Exception {
        Vector errorVector = new VectorImp(3, 1);
        Map<Integer, Double> updateNumbers = new HashMap<Integer, Double>();
        updateNumbers.put(0, 0.1);
        word2Vec.calculateError(errorVector, updateNumbers);
        assertEquals(errorVector.getElement(0), 1.1, 0.001);
        assertEquals(errorVector.getElement(1), 1.1, 0.001);
        assertEquals(errorVector.getElement(2), 1.1, 0.001);
    }

    @Test
    public void testCalculateUpdateNumbers() throws Exception {
        Map<Integer, Double> updateNumbers = new HashMap<Integer, Double>();
        word2Vec.calculateUpdateNumbers(updateNumbers, "d", "a");
        assertTrue(updateNumbers.containsKey(2));
        assertEquals(updateNumbers.get(2), -0.047425873177566635, 0.001);
        word2Vec.calculateUpdateNumbers(updateNumbers, "b", "a");
        assertTrue(updateNumbers.containsKey(0));
        assertTrue(updateNumbers.containsKey(2));
        assertEquals(updateNumbers.get(0), 0.9525741268224334, 0.001);
        assertEquals(updateNumbers.get(2), 0.9051482536448667, 0.001);
    }

    @Test
    public void testCalculateDot() throws Exception {
        Vector vector1 = new VectorImp(3, 1);
        Vector vector2 = new VectorImp(3, 1);
        assertEquals(word2Vec.calculateDot(vector1, vector2), 3, 0.001);
        double vector3[] = {1, 2, 3};
        double vector4[] = {3, 2, 1};
        assertEquals(word2Vec.calculateDot(new VectorImp(vector3), new VectorImp(vector4)), 10, 0.001);

    }


    @Test
    public void testCalculateSigmoid() throws Exception {
        assertEquals(word2Vec.calculateSigmoid(100), 1, 0.001);
        assertEquals(word2Vec.calculateSigmoid(0.5), 0.6224593312018546, 0.001);
        assertEquals(word2Vec.calculateSigmoid(0), 0.5, 0.001);
        assertEquals(word2Vec.calculateSigmoid(-0.5), 0.3775406687981454, 0.001);
        assertEquals(word2Vec.calculateSigmoid(-100), 0, 0.001);
    }
}