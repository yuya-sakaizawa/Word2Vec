package com.github.sakaizawa.word2vec;

import com.sun.crypto.provider.AESCipher;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sakaisawayuya on 2015/11/12.
 */
public class VectorTest {

    private Vector vector1 = new VectorImp(1);
    private Vector vector2 = new VectorImp(3);
    private Vector vector3 = new VectorImp(5);

    public VectorTest () {
        vector1.add(0, 1);
        vector2.add(0, 1);
        vector2.add(1, 2);
        vector2.add(2, 3);
        vector3.add(0, 1);
        vector3.add(1, 2);
        vector3.add(2, 3);
        vector3.add(3, 4);
        vector3.add(4, 5);
    }

    @Test
    public void testAdd() throws Exception {
        vector1.add(0, 10);
        assertEquals(11, vector1.getElement(0), 0.001);

        vector2.add(0, 10);
        vector2.add(1, 20);
        vector2.add(2, 30);
        assertEquals(11, vector2.getElement(0), 0.001);
        assertEquals(22, vector2.getElement(1), 0.001);
        assertEquals(33, vector2.getElement(2), 0.001);

        vector3.add(0, 10);
        vector3.add(1, 10);
        vector3.add(2, 10);
        assertEquals(11, vector3.getElement(0), 0.001);
        assertEquals(12, vector3.getElement(1), 0.001);
        assertEquals(13, vector3.getElement(2), 0.001);
        assertEquals(4, vector3.getElement(3), 0.001);
        assertEquals(5, vector3.getElement(4), 0.001);
    }

    @Test
    public void testGetSize() throws Exception {
        assertEquals(Math.sqrt(1), vector1.getSize(), 0.001);
        assertEquals(Math.sqrt(14), vector2.getSize(), 0.001);
        assertEquals(Math.sqrt(55), vector3.getSize(), 0.001);
    }

    @Test
    public void testGetElement() throws Exception {
        assertEquals(1, vector1.getElement(0), 0.001);

        assertEquals(1, vector2.getElement(0), 0.001);
        assertEquals(2, vector2.getElement(1), 0.001);
        assertEquals(3, vector2.getElement(2), 0.001);

        assertEquals(1, vector3.getElement(0), 0.001);
        assertEquals(2, vector3.getElement(1), 0.001);
        assertEquals(3, vector3.getElement(2), 0.001);
        assertEquals(4, vector3.getElement(3), 0.001);
        assertEquals(5, vector3.getElement(4), 0.001);
    }

    @Test
    public void testGetDimension() throws Exception {
        assertEquals(1, vector1.getDimension());
        assertEquals(3, vector2.getDimension());
        assertEquals(5, vector3.getDimension());
    }
}