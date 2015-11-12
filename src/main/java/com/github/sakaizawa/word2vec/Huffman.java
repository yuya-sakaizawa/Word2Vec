package com.github.sakaizawa.word2vec;

import java.io.IOException;
import java.util.List;

/**
 * Created by sakaisawayuya on 2015/09/19.
 */
public interface Huffman {


    public void initialize(SentenceReader sentenceReader, int size) throws IOException;

    public void createTree(SentenceReader sentenceReader, int size) throws IOException;


    /**
     * リーフノードを作り、親のいないノードリストとしてそれらを全部入れる
     */
    public void initialize(List<List<String>> text, int size);


    public String encodeWord(String word);

    public List<Node> getNodeList(String word);

    /**
     * ハフマン木を生成
     */
    public void createTree(List<List<String>> text, int size);

    /**
     * 親のいないノードリストの中から頻度が最も低いものをpopする
     * @return 頻度が最も低いノード
     */
    public Node popMin();

    /**
     * 親のいないノードリストの中に新しく作ったノードをいれる
     * @param node リストに入れるノード
     */
    public void pushNode(Node node);

}
