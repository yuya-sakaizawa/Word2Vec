package com.github.sakaizawa.word2vec;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sakaisawayuya on 2015/11/11.
 */
public class HuffmanTreeImplTest {

    @Test
    public void testIsLeaf() throws Exception {
        HuffmanTree node1 = new HuffmanTreeImpl("node1", 1,  10);
        assertTrue(node1.isLeaf());
        HuffmanTree node2 = new HuffmanTreeImpl("node2", 2,  10);
        assertTrue(node2.isLeaf());

        HuffmanTree parent1 = new HuffmanTreeImpl(1, 10);
        parent1.addNode(node1, node2);
        assertFalse(parent1.isLeaf());
        assertTrue(parent1.getLeftChild().isLeaf());
        assertTrue(parent1.getRightChild().isLeaf());

        HuffmanTree node3 = new HuffmanTreeImpl("node3", 3,  10);
        HuffmanTree node4 = new HuffmanTreeImpl("node4", 4,  10);
        HuffmanTree parent2 = new HuffmanTreeImpl(2, 10);
        parent2.addNode(node3, node4);
        HuffmanTree parent3 = new HuffmanTreeImpl(3, 10);
        parent3.addNode(parent1, parent2);
        assertFalse(parent3.isLeaf());
        assertFalse(parent3.getLeftChild().isLeaf());
        assertFalse(parent3.getRightChild().isLeaf());
        assertTrue(parent3.getLeftChild().getLeftChild().isLeaf());
        assertTrue(parent3.getLeftChild().getRightChild().isLeaf());
        assertTrue(parent3.getRightChild().getLeftChild().isLeaf());
        assertTrue(parent3.getRightChild().getRightChild().isLeaf());
    }

    @Test
    public void testIsROOT() throws Exception {
        HuffmanTree node1 = new HuffmanTreeImpl("node1", 1,  10);
        assertTrue(node1.isROOT());
        HuffmanTree node2 = new HuffmanTreeImpl("node2", 2,  10);
        assertTrue(node2.isROOT());

        HuffmanTree parent1 = new HuffmanTreeImpl(1, 10);
        parent1.addNode(node1, node2);
        assertTrue(parent1.isROOT());
        assertFalse(parent1.getLeftChild().isROOT());
        assertFalse(parent1.getRightChild().isROOT());

        HuffmanTree node3 = new HuffmanTreeImpl("node3", 3,  10);
        HuffmanTree node4 = new HuffmanTreeImpl("node4", 4,  10);
        HuffmanTree parent2 = new HuffmanTreeImpl(2, 10);
        parent2.addNode(node3, node4);
        HuffmanTree parent3 = new HuffmanTreeImpl(3, 10);
        parent3.addNode(parent1, parent2);
        assertTrue(parent3.isROOT());
        assertFalse(parent3.getLeftChild().isROOT());
        assertFalse(parent3.getRightChild().isROOT());
        assertFalse(parent3.getLeftChild().getLeftChild().isROOT());
        assertFalse(parent3.getLeftChild().getRightChild().isROOT());
        assertFalse(parent3.getRightChild().getLeftChild().isROOT());
        assertFalse(parent3.getRightChild().getRightChild().isROOT());
    }

    @Test
    public void testAddNode() throws Exception {
        HuffmanTree node1 = new HuffmanTreeImpl("node1", 1,  10);
        HuffmanTree node2 = new HuffmanTreeImpl("node2", 2,  10);

        HuffmanTree parent1 = new HuffmanTreeImpl(1, 10);
        parent1.addNode(node1, node2);
        assertEquals("node1", parent1.getLeftChild().getLabel());
        assertEquals("node2", parent1.getRightChild().getLabel());
        assertEquals(null, node1.getParent().getLabel());
        assertEquals(null, node2.getParent().getLabel());

        HuffmanTree node3 = new HuffmanTreeImpl("node3", 3,  10);
        HuffmanTree node4 = new HuffmanTreeImpl("node4", 4,  10);
        HuffmanTree parent2 = new HuffmanTreeImpl(2, 10);
        parent2.addNode(node3, node4);
        HuffmanTree parent3 = new HuffmanTreeImpl(3, 10);
        parent3.addNode(parent1, parent2);
        assertEquals("node1", parent3.getLeftChild().getLeftChild().getLabel());
        assertEquals("node2", parent3.getLeftChild().getRightChild().getLabel());
        assertEquals("node3", parent3.getRightChild().getLeftChild().getLabel());
        assertEquals("node4", parent3.getRightChild().getRightChild().getLabel());
    }

    @Test
    public void testGetFreq() throws Exception {
        HuffmanTree node1 = new HuffmanTreeImpl("node1", 1,  10);
        HuffmanTree node2 = new HuffmanTreeImpl("node2", 2,  10);
        assertEquals(1, node1.getFreq());
        assertEquals(2, node2.getFreq());

        HuffmanTree parent1 = new HuffmanTreeImpl(1, 10);
        parent1.addNode(node1, node2);
        assertEquals(3, parent1.getFreq());

        HuffmanTree node3 = new HuffmanTreeImpl("node3", 3,  10);
        HuffmanTree node4 = new HuffmanTreeImpl("node4", 4,  10);
        HuffmanTree parent2 = new HuffmanTreeImpl(2, 10);
        parent2.addNode(node3, node4);
        HuffmanTree parent3 = new HuffmanTreeImpl(3, 10);
        parent3.addNode(parent1, parent2);
        assertEquals(10, parent3.getFreq());
    }
}