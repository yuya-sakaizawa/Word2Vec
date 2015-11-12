package com.github.sakaizawa.word2vec;

import java.util.Comparator;

/**
 * Created by sakaisawayuya on 2015/11/11.
 */
public class HuffmanTreeComparator
        implements Comparator<HuffmanTree> {

    public int compare(HuffmanTree huffmanTree1, HuffmanTree huffmanTree2) {
        int no1 = huffmanTree1.getFreq();
        int no2 = huffmanTree2.getFreq();

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
