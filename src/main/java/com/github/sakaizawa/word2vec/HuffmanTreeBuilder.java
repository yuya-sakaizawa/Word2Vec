package com.github.sakaizawa.word2vec;

import java.util.List;
import java.util.Map;

/**
 * Created by sakaisawayuya on 2015/11/11.
 */
public interface HuffmanTreeBuilder {

    /**
     * ハフマン木を構築
     * @param sentenceReader
     * @param size
     * @return
     */
    public HuffmanTree buildHuffmanTree(SentenceReader sentenceReader, int size);

    /**
     * ハフマン木を構築
     * @param sentenceReader
     * @param size
     * @param value
     * @return
     */
    public HuffmanTree buildHuffmanTree(SentenceReader sentenceReader, int size, double value);

    /**
     * 下から順にルート一歩手前のノードまでのノードのリストを返す
     * @param word 単語
     * @return ノードのリスト
     */
    public List<HuffmanTree> getNodeList(String word);
}
