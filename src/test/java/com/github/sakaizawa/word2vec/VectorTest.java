package com.github.sakaizawa.word2vec;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sakaisawayuya on 2015/12/16.
 */
public class VectorTest {

    private Vector vector1 = new VectorImp(3, 0);
    private Vector vector2 = new VectorImp(4, 1);
    private Vector vector3 = new VectorImp(5, 2);

    @Test
    public void testGetDimension() throws Exception {
        assertEquals(vector1.getDimension(), 3);
        assertEquals(vector2.getDimension(), 4);
        assertEquals(vector3.getDimension(), 5);
    }

    @Test
    public void testGetSize() throws Exception {
        assertEquals(vector1.getSize(), 0, 0.001);
        assertEquals(vector2.getSize(), Math.sqrt(4), 0.001);
        assertEquals(vector3.getSize(), Math.sqrt(20), 0.001);
    }

    @Test
    public void testGetElement() throws Exception {
        assertEquals(vector1.getElement(0), 0, 0.001);
        assertEquals(vector1.getElement(1), 0, 0.001);
        assertEquals(vector1.getElement(2), 0, 0.001);
        assertEquals(vector2.getElement(0), 1, 0.001);
        assertEquals(vector2.getElement(1), 1, 0.001);
        assertEquals(vector2.getElement(2), 1, 0.001);
        assertEquals(vector2.getElement(3), 1, 0.001);
        assertEquals(vector3.getElement(0), 2, 0.001);
        assertEquals(vector3.getElement(1), 2, 0.001);
        assertEquals(vector3.getElement(2), 2, 0.001);
        assertEquals(vector3.getElement(3), 2, 0.001);
        assertEquals(vector3.getElement(4), 2, 0.001);
    }

    @Test
    public void testAddElement() throws Exception {
        vector1.addElement(0, 5);
        vector1.addElement(1, 6);
        vector1.addElement(2, 7);
        assertEquals(vector1.getElement(0), 5, 0.001);
        assertEquals(vector1.getElement(1), 6, 0.001);
        assertEquals(vector1.getElement(2), 7, 0.001);

        vector2.addElement(0, 5);
        vector2.addElement(1, 6);
        vector2.addElement(2, 7);
        vector2.addElement(3, 8);
        assertEquals(vector2.getElement(0), 6, 0.001);
        assertEquals(vector2.getElement(1), 7, 0.001);
        assertEquals(vector2.getElement(2), 8, 0.001);
        assertEquals(vector2.getElement(3), 9, 0.001);
    }
}