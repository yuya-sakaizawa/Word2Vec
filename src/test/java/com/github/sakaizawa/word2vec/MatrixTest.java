package com.github.sakaizawa.word2vec;

import org.junit.Test;
import org.omg.CORBA.MARSHAL;

import static org.junit.Assert.*;

/**
 * Created by sakaisawayuya on 2015/12/16.
 */
public class MatrixTest {

    private Matrix matrix1 = new MatrixImp(1, 1, 0);
    private Matrix matrix2 = new MatrixImp(2, 2, 1);
    private Matrix matrix3 = new MatrixImp(2, 3, 1);

    @Test
    public void testGetRowNum() throws Exception {
        assertEquals(matrix1.getRowNum(), 1);
        assertEquals(matrix2.getRowNum(), 2);
        assertEquals(matrix3.getRowNum(), 2);
    }

    @Test
    public void testGetColumnNum() throws Exception {
        assertEquals(matrix1.getColumnNum(), 1);
        assertEquals(matrix2.getColumnNum(), 2);
        assertEquals(matrix3.getColumnNum(), 3);
    }

    @Test
    public void testGetRow() throws Exception {
        /*
        Vector vector = new VectorImp(1, 0);
        Vector vector1 = matrix1.getRow(0);
        assertTrue(vector1.equals(vector));

        vector = new VectorImp(2, 1);
        Vector vector2_1 = matrix2.getRow(0);
        Vector vector2_2 = matrix2.getRow(1);
        assertTrue(vector2_1.equals(vector));
        assertTrue(vector2_2.equals(vector));

        vector = new VectorImp(3, 1);
        Vector vector3_1 = matrix3.getRow(0);
        Vector vector3_2 = matrix3.getRow(1);
        assertTrue(vector3_1.equals(vector));
        assertTrue(vector3_2.equals(vector));
        */
    }

    @Test
    public void testGetColumn() throws Exception {
        /*
        Vector vector = new VectorImp(1, 0);
        Vector vector1 = matrix1.getColumn(0);
        assertTrue(vector1.equals(vector));

        vector = new VectorImp(2, 1);
        Vector vector2_1 = matrix2.getColumn(0);
        Vector vector2_2 = matrix2.getColumn(1);
        assertTrue(vector2_1.equals(vector));
        assertTrue(vector2_2.equals(vector));

        Vector vector3_1 = matrix3.getColumn(0);
        Vector vector3_2 = matrix3.getColumn(1);
        Vector vector3_3 = matrix3.getColumn(2);
        assertTrue(vector3_1.equals(vector));
        assertTrue(vector3_2.equals(vector));
        assertTrue(vector3_3.equals(vector));
        */
    }

    @Test
    public void testGetElement() throws Exception {
        assertEquals(matrix1.getElement(0, 0), 0, 0.001);

        assertEquals(matrix2.getElement(0, 0), 1, 0.001);
        assertEquals(matrix2.getElement(0, 1), 1, 0.001);
        assertEquals(matrix2.getElement(1, 0), 1, 0.001);
        assertEquals(matrix2.getElement(1, 1), 1, 0.001);

        assertEquals(matrix3.getElement(0, 0), 1, 0.001);
        assertEquals(matrix3.getElement(0, 1), 1, 0.001);
        assertEquals(matrix3.getElement(0, 2), 1, 0.001);
        assertEquals(matrix3.getElement(1, 0), 1, 0.001);
        assertEquals(matrix3.getElement(1, 1), 1, 0.001);
        assertEquals(matrix3.getElement(1, 2), 1, 0.001);
    }

    @Test
    public void testAddElement() throws Exception {
        matrix1.addElement(0, 0, 2);
        assertEquals(matrix1.getElement(0, 0), 2, 0.001);

        matrix2.addElement(0, 0, 2);
        matrix2.addElement(0, 1, 3);
        matrix2.addElement(1, 0, 4);
        matrix2.addElement(1, 1, 5);
        assertEquals(matrix2.getElement(0, 0), 3, 0.001);
        assertEquals(matrix2.getElement(0, 1), 4, 0.001);
        assertEquals(matrix2.getElement(1, 0), 5, 0.001);
        assertEquals(matrix2.getElement(1, 1), 6, 0.001);

        matrix3.addElement(0, 0, 2);
        matrix3.addElement(0, 1, 3);
        matrix3.addElement(0, 2, 4);
        matrix3.addElement(1, 0, 5);
        matrix3.addElement(1, 1, 6);
        matrix3.addElement(1, 2, 7);
        assertEquals(matrix3.getElement(0, 0), 3, 0.001);
        assertEquals(matrix3.getElement(0, 1), 4, 0.001);
        assertEquals(matrix3.getElement(0, 2), 5, 0.001);
        assertEquals(matrix3.getElement(1, 0), 6, 0.001);
        assertEquals(matrix3.getElement(1, 1), 7, 0.001);
        assertEquals(matrix3.getElement(1, 2), 8, 0.001);

    }
}