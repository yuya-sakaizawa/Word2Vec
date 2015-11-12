package com.github.sakaizawa.word2vec;

import java.util.*;

/**
 * Created by sakaisawayuya on 2015/11/11.
 */
public class HuffmanTreeBuilderImpl
    implements HuffmanTreeBuilder{

    private Map<String, HuffmanTree> leafNodeMap = new HashMap<String, HuffmanTree>();
    private List<HuffmanTree> nodeList = new ArrayList<HuffmanTree>();
    private HuffmanTree huffmanTree;

    /**
     * リーフノードを作り、親のいないノードリストとしてそれらを全部入れる
     */
    private void initialize(SentenceReader sentenceReader, int size) {
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
        sortList(wordFreq, size);
    }

    /**
     *
     * @param wordFreq
     */
    private void sortList(Map<String, Integer> wordFreq, int size) {
        for (String word : wordFreq.keySet()) {
            HuffmanTree huffmanTree = new HuffmanTreeImpl(word, wordFreq.get(word), size);
            nodeList.add(huffmanTree);
            leafNodeMap.put(word, huffmanTree);
        }
        Collections.sort(nodeList, new HuffmanTreeComparator());
    }

    /**
     * ハフマン木を構築
     *
     * @param sentenceReader
     * @param size
     * @return
     */
    @Override
    public HuffmanTree buildHuffmanTree(SentenceReader sentenceReader, int size) {
        initialize(sentenceReader, size);
        this.huffmanTree = connectNode(size);
        return this.huffmanTree;
    }

    private HuffmanTree connectNode(int size) {
        while (nodeList.size() != 1) {
            //HuffmanTree node1 = popMin();
            //HuffmanTree node2 = popMin();
            HuffmanTree parent = new HuffmanTreeImpl(size);
            //parent.addNode(node1, node2);
            parent.addNode(nodeList.get(0), nodeList.get(1));
            nodeList.remove(0);
            nodeList.remove(0);
            pushNode(parent);
        }
        return nodeList.get(0);
    }

    /**
     * 親のいないノードリストの中から頻度が最も低いものをpopする
     *
     * @return 頻度が最も低いノード
     */
    private HuffmanTree popMin() {
        HuffmanTree node = nodeList.get(0);
        nodeList.remove(0);
        return node;
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

    /**
     * leafnodeを返す
     * @return leafnode
     */
    public Map<String, HuffmanTree> getLeafNodeMap() {
        return leafNodeMap;
    }
}
