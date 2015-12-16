package com.github.sakaizawa.word2vec;

import javax.naming.NameNotFoundException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by sakaisawayuya on 2015/11/11.
 */
public class HuffmanTreeBuilderImpl
    implements HuffmanTreeBuilder, Serializable{

    private Map<String, HuffmanTree> leafNodeMap = new HashMap<String, HuffmanTree>();
    private List<HuffmanTree> nodeList = new ArrayList<HuffmanTree>();
    private HuffmanTree huffmanTree;
    private int size;
    private double value = java.lang.Double.NaN;

    /**
     * ハフマン木を構築
     *
     * @param sentenceReader
     * @param size
     * @return
     */
    @Override
    public HuffmanTree buildHuffmanTree(SentenceReader sentenceReader, int size) {
        this.size = size;
        initialize(sentenceReader);
        this.huffmanTree = connectNode(size);
        return this.huffmanTree;
    }

    /**
     * ハフマン木を構築
     *
     * @param sentenceReader
     * @param size
     * @return
     */
    public HuffmanTree buildHuffmanTree(SentenceReader sentenceReader, int size, double value) {
        this.size = size;
        this.value = value;
        initialize(sentenceReader);
        this.huffmanTree = connectNode(size);
        return this.huffmanTree;
    }

    /**
     * リーフノードを作り、親のいないノードリストとしてそれらを全部入れる
     */
    private void initialize(SentenceReader sentenceReader) {
        Map<String, Integer> wordFreq = new HashMap<String, Integer>();
        for (String[] sentence : sentenceReader) {
            for (String word : sentence) {
                if (wordFreq.containsKey(word)) {
                    wordFreq.put(word, wordFreq.get(word)+1);
                } else {
                    wordFreq.put(word, 1);
                }
            }
        }
        sortList(wordFreq);
    }

    /**
     *
     * @param wordFreq
     */
    private void sortList(Map<String, Integer> wordFreq) {
        for (String word : wordFreq.keySet()) {
            if (java.lang.Double.isNaN(value)) {
                huffmanTree = new HuffmanTreeImpl(word, wordFreq.get(word), size);
            } else {
                huffmanTree = new HuffmanTreeImpl(word, wordFreq.get(word), size, value);
            }
            nodeList.add(huffmanTree);
            leafNodeMap.put(word, huffmanTree);
        }
        Collections.sort(nodeList, new HuffmanTreeComparator());
    }

    /**
     * 下から順にルートノードまでのノードのリストを返す
     * @param word 単語
     * @return ノードのリスト
     */
    public List<HuffmanTree> getNodeList(String word) {
        List<HuffmanTree> nodeList = new ArrayList<HuffmanTree>();
        HuffmanTree leafNode = leafNodeMap.get(word);

        while (leafNode.getParent() != null) {
            nodeList.add(leafNode);
            leafNode = leafNode.getParent();
        }
        nodeList.add(leafNode);
        return nodeList;
    }

    private HuffmanTree connectNode(int size) {
        while (nodeList.size() != 1) {
            HuffmanTree parent;
            if (java.lang.Double.isNaN(value)) {
                parent = new HuffmanTreeImpl(size);
            } else {
                parent = new HuffmanTreeImpl(size, value);
            }
            parent.addNode(nodeList.get(0), nodeList.get(1));
            nodeList.remove(0);
            nodeList.remove(0);
            pushNode(parent);
        }
        return nodeList.get(0);
    }

    //　探しかたに工夫する余地あり
    /**
     * 親のいないノードリストの中に新しく作ったノードをいれる
     *
     * @param node リストに入れるノード
     */
    private void pushNode(HuffmanTree node) {
        if (nodeList.size() == 0 || node.getFreq() > nodeList.get(nodeList.size()-1).getFreq()) {
            nodeList.add(node);
            return;
        }
        for (int i = 0; i < nodeList.size(); i++) {
            if (node.getFreq() > nodeList.get(i).getFreq()) {
                continue;
            } else {
                nodeList.add(i, node);
                break;
            }
        }
    }
}
