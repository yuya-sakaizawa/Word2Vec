package com.github.sakaizawa.word2vec;

import java.util.List;

/**
 * Created by sakaisawayuya on 2015/09/03.
 */
public class SoftMaxImp
    implements SoftMax {

    public Vector calculateSortMax(Matrix matrix, Vector vector) {
        Vector output = new VectorImp(matrix.getColumnNum());

        double exp_sum = 0;
        for (int i = 0; i < matrix.getColumnNum(); i++) {
            double a = 0;
            for (int j = 0; j < matrix.getColumn(i).getDimension(); j++) {
                a += matrix.getElement(j, i) * vector.getElement(j);
            }
            output.add(i, (double) Math.exp(a));
            exp_sum += (double)Math.exp(a);
        }
        for (int i = 0; i < output.getDimension(); i++) {
            output.add(i, output.getElement(i) / exp_sum);
        }

        return output;
    }
}
