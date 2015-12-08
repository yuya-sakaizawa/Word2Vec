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

    private HuffmanTree huffmanTree;
    private HuffmanTreeBuilder huffmanTreeBuilder;

    public HuffmanTreeBuilderImplTest () throws Exception {
        File testFile = new File("/Users/sakaisawayuya/Desktop/JX/Word2Vec/data/TestHuffmanTreeBuilder");
        SentenceReader sentenceReader = new SentenceReader(testFile);
        huffmanTreeBuilder = new HuffmanTreeBuilderImpl();
        huffmanTree = huffmanTreeBuilder.buildHuffmanTree(sentenceReader, 10);
    }

    @Test
    public void testBuildHuffmanTree() throws Exception {
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

    @Test
    public void testGetNodeList() throws Exception {
        List<HuffmanTree> nodeList1 = huffmanTreeBuilder.getNodeList("a");
        assertEquals(1, nodeList1.get(0).getFreq());
        assertEquals(3, nodeList1.get(1).getFreq());
        assertEquals(6, nodeList1.get(2).getFreq());
        assertEquals(10, nodeList1.get(3).getFreq());
    }
}