package com.github.sakaizawa.word2vec;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by sakaisawayuya on 2015/11/12.
 */
public class HuffmanTreeBuilderImplTest {

    @Test
    public void testBuildHuffmanTree() throws Exception {
        File testFile = new File("/Users/sakaisawayuya/Desktop/JX/Word2Vec/data/TestHuffmanTreeBuilder");
        SentenceReader sentenceReader = new SentenceReader(testFile);
        HuffmanTreeBuilder huffmanTreeBuilder = new HuffmanTreeBuilderImpl();
        HuffmanTree huffmanTree = huffmanTreeBuilder.buildHuffmanTree(sentenceReader, 10);

        assertEquals(10, huffmanTree.getFreq());
        assertEquals(4, huffmanTree.getLeftChild().getFreq());
        assertEquals("d", huffmanTree.getLeftChild().getLabel());
        assertEquals(6, huffmanTree.getRightChild().getFreq());
        assertEquals(3, huffmanTree.getRightChild().getRightChild().getFreq());
        assertEquals("c", huffmanTree.getRightChild().getRightChild().getLabel());
        assertEquals(3, huffmanTree.getRightChild().getLeftChild().getFreq());
        assertEquals(1, huffmanTree.getRightChild().getLeftChild().getLeftChild().getFreq());
        assertEquals("a", huffmanTree.getRightChild().getLeftChild().getLeftChild().getLabel());
        assertEquals(2, huffmanTree.getRightChild().getLeftChild().getRightChild().getFreq());
        assertEquals("b", huffmanTree.getRightChild().getLeftChild().getRightChild().getLabel());
    }
}