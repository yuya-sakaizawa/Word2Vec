package com.github.sakaizawa.word2vec;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sakaisawayuya on 2015/11/12.
 */
public class MatrixTest {

    private Matrix matrix1 = new MatrixImp(1, 1);
    private Matrix matrix2 = new MatrixImp(2, 3);

    public MatrixTest () {
        matrix1.add(0, 0, 1);

        matrix2.add(0, 0, 1);
        matrix2.add(0, 1, 2);
        matrix2.add(0, 2, 3);
        matrix2.add(1, 0, 4);
        matrix2.add(1, 1, 5);
        matrix2.add(1, 2, 6);
    }

    @Test
    public void testGetRowNum() throws Exception {
        assertEquals(1, matrix1.getRowNum());
        assertEquals(2, matrix2.getRowNum());
    }

    @Test
    public void testGetColumnNum() throws Exception {
        assertEquals(1, matrix1.getColumnNum());
        assertEquals(3, matrix2.getColumnNum());
    }

    @Test
    public void testAdd() throws Exception {
        matrix1.add(0, 0, 10);
        assertEquals(11, matrix1.getElement(0, 0), 0.001);

        matrix2.add(0, 0, 10);
        matrix2.add(0, 1, 20);
        matrix2.add(0, 2, 30);
        matrix2.add(1, 0, 40);
        matrix2.add(1, 1, 50);
        matrix2.add(1, 2, 60);
        assertEquals(11, matrix2.getElement(0, 0), 0.001);
        assertEquals(22, matrix2.getElement(0, 1), 0.001);
        assertEquals(33, matrix2.getElement(0, 2), 0.001);
        assertEquals(44, matrix2.getElement(1, 0), 0.001);
        assertEquals(55, matrix2.getElement(1, 1), 0.001);
        assertEquals(66, matrix2.getElement(1, 2), 0.001);
    }

    @Test
    public void testGetElement() throws Exception {
        assertEquals(1, matrix1.getElement(0, 0), 0.001);

        assertEquals(1, matrix2.getElement(0, 0), 0.001);
        assertEquals(2, matrix2.getElement(0, 1), 0.001);
        assertEquals(3, matrix2.getElement(0, 2), 0.001);
        assertEquals(4, matrix2.getElement(1, 0), 0.001);
        assertEquals(5, matrix2.getElement(1, 1), 0.001);
        assertEquals(6, matrix2.getElement(1, 2), 0.001);
    }

    @Test
    public void testGetRow() throws Exception {
        Vector row1 = matrix1.getRow(0);
        assertEquals(1, row1.getElement(0), 0.001);

        Vector row2 = matrix2.getRow(0);
        assertEquals(1, row2.getElement(0), 0.001);
        assertEquals(2, row2.getElement(1), 0.001);
        assertEquals(3, row2.getElement(2), 0.001);

        Vector row3 = matrix2.getRow(1);
        assertEquals(4, row3.getElement(0), 0.001);
        assertEquals(5, row3.getElement(1), 0.001);
        assertEquals(6, row3.getElement(2), 0.001);

    }

    @Test
    public void testGetColumn() throws Exception {
        Vector column1 = matrix1.getColumn(0);
        assertEquals(1, column1.getElement(0), 0.001);

        Vector column2 = matrix2.getColumn(0);
        assertEquals(1, column2.getElement(0), 0.001);
        assertEquals(4, column2.getElement(1), 0.001);

        Vector column3 = matrix2.getColumn(1);
        assertEquals(2, column3.getElement(0), 0.001);
        assertEquals(5, column3.getElement(1), 0.001);

        Vector column4 = matrix2.getColumn(2);
        assertEquals(3, column4.getElement(0), 0.001);
        assertEquals(6, column4.getElement(1), 0.001);
    }
}