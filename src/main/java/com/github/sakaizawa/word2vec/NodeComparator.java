package com.github.sakaizawa.word2vec;

import java.util.Comparator;

/**
 * Created by sakaisawayuya on 2015/09/19.
 */
public class NodeComparator
    implements Comparator<Node>{

    public int compare(Node node_1, Node node_2) {
        int no1 = node_1.getFreq();
        int no2 = node_2.getFreq();

        //頻度の昇順でソートされる
        if (no1 > no2) {
            return 1;

        } else if (no1 == no2) {
            return 0;

        } else {
            return -1;

        }
    }
}
