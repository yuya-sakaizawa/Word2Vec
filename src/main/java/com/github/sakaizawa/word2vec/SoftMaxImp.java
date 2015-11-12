package com.github.sakaizawa.word2vec;

import java.util.List;

/**
 * Created by sakaisawayuya on 2015/09/03.
 */
public class SoftMaxImp
    implements SoftMax {

    public Vector calculateSortMax(Matrix matrix, Vector vector) {
        Vector output = new VectorImp(matrix.getColumnNum());

        float exp_sum = 0;
        for (int i = 0; i < matrix.getColumnNum(); i++) {
            float a = 0;
            for (int j = 0; j < matrix.getColumn(i).getDimension(); j++) {
                a += matrix.getElement(j, i) * vector.getElement(j);
            }
            output.add(i, (float) Math.exp(a));
            exp_sum += (float)Math.exp(a);
        }
        for (int i = 0; i < output.getDimension(); i++) {
            output.add(i, output.getElement(i) / exp_sum);
        }

        return output;
    }

    public Vector calculateHSoftMax(List<List<Node>> nodeLists, Vector vector) {
        Vector outputVector = new VectorImp(nodeLists.size());
        for (int wordNum = 0; wordNum < nodeLists.size(); wordNum++) {
            float sig = 1;
            List<Node> wordNodes = nodeLists.get(wordNum);
            for (int nodeNum = 0; nodeNum < wordNodes.size(); nodeNum++) {
                Node node = wordNodes.get(nodeNum);
                float dot = 0;
                if (node.getLabel().equals("1")) {
                    for (int i = 0; i < node.getParent().getVector().getDimension(); i++) {
                        dot += node.getParent().getVector().getElement(i) * vector.getElement(i);
                    }
                } else {
                    for (int i = 0; i < node.getParent().getVector().getDimension(); i++) {
                        dot += -node.getParent().getVector().getElement(i) * vector.getElement(i);
                    }
                }
                sig *= (float)(1 / (1 + Math.exp(-dot)));
            }
            outputVector.add(wordNum, sig);
        }
        return outputVector;
    }


}
