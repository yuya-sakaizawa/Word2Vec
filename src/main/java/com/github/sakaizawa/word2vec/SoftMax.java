package com.github.sakaizawa.word2vec;

import java.util.List;

/**
 * Created by sakaisawayuya on 2015/09/03.
 */
public interface SoftMax {

    public Vector calculateSortMax(Matrix matrix, Vector vector);

    public Vector calculateHSoftMax(List<List<Node>> nodeLists, Vector vector);
}
