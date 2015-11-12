package com.github.sakaizawa.word2vec;

import java.io.IOException;
import java.util.*;

/**
 * Created by sakaisawayuya on 2015/09/19.
 */
public class HuffmanImp
    implements Huffman{

    private Map<String, Node> leafNodeMap = new HashMap<String, Node>();
    private List<Node> nodeList = new ArrayList<Node>();
    private Node root;

    /**
     * リーフノードを作り、親のいないノードリストとしてそれらを全部入れる
     */
    public void initialize(List<List<String>> text, int size) {
        Map<String, Integer> wordFreq = new HashMap<String, Integer>();

        for (int senNum = 0; senNum < text.size(); senNum++) {
            for (int wordNum = 0; wordNum < text.get(senNum).size(); wordNum++) {
                String word = text.get(senNum).get(wordNum);
                if (wordFreq.containsKey(word)) {
                    wordFreq.put(word, wordFreq.get(word)+1);
                } else {
                    wordFreq.put(word, 1);
                }
            }
        }
        sortList(wordFreq);
    }

    //New
    public void initialize(SentenceReader sentenceReader, int size) {
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

    //New
    protected void sortList(Map<String, Integer> wordFreq) {
        for (String word : wordFreq.keySet()) {
            Node node = new Node(word, wordFreq.get(word), null, null, null);
            nodeList.add(node);
            leafNodeMap.put(word, node);
        }
        Collections.sort(nodeList, new NodeComparator());
        /*
        //結果を画面表示する
        Iterator<com.github.sakaizawa.word2vec.Node> it = nodeList.iterator();
        while (it.hasNext()) {
            com.github.sakaizawa.word2vec.Node data = it.next();
            System.out.println(data.getWord() + ":" + data.getFreq());
        }
        System.out.println();
        */
    }

    /**
     * 下から順にルート一歩手前のノードまでのノードのリストを返す
     * @param word 単語
     * @return ノードのリスト
     */
    public List<Node> getNodeList(String word) {
        List<Node> nodeList = new ArrayList<Node>();
        Node leafNode = leafNodeMap.get(word);

        while (leafNode.getParent() != null) {
            nodeList.add(leafNode);
            leafNode = leafNode.getParent();
        }
        return nodeList;
    }

    public String encodeWord(String word) {
        String encode = "";
        Node leafNode = null;
        for (int i = 0; i < leafNodeMap.size(); i++) {
            if (leafNodeMap.get(i).getWord().equals(word)) {
                leafNode = leafNodeMap.get(i);
                break;
            }
        }
        System.out.println();
        while (leafNode.getParent() != null) {
            encode += leafNode.getLabel();
            leafNode = leafNode.getParent();
        }
        return encode;
    }

    /**
     * ハフマン木を生成
     */
    public void createTree(List<List<String>> text, int size) {
        initialize(text, size);
        connectNode(size);
    }

    //New
    public void createTree(SentenceReader sentenceReader, int size) throws IOException {
        initialize(sentenceReader, size);
        connectNode(size);
    }


    // New
    protected void connectNode(int size) {
        while (nodeList.size() != 2) {
            Node node_1 = popMin();
            Node node_2 = popMin();
            Node parent = new Node(null, node_1.getFreq()+node_2.getFreq(), null, node_1, node_2, size);
            node_1.setParent(parent);
            node_1.setLabel("1");
            node_2.setParent(parent);
            node_2.setLabel("0");
            pushNode(parent);
        }
        Node node_1 = popMin();
        Node node_2 = popMin();
        this.root = new Node(null, node_1.getFreq()+node_2.getFreq(), null, node_1, node_2, size);
        node_1.setParent(root);
        node_1.setLabel("1");
        node_2.setParent(root);
        node_2.setLabel("0");
        nodeList.clear();

    }

    /**
     * 親のいないノードリストの中から頻度が最も低いものをpopする
     *
     * @return 頻度が最も低いノード
     */
    public Node popMin() {
        Node node = nodeList.get(0);
        nodeList.remove(0);
        return node;
    }

    //　探しかたに工夫する余地あり
    /**
     * 親のいないノードリストの中に新しく作ったノードをいれる
     *
     * @param node リストに入れるノード
     */
    public void pushNode(Node node) {
        if (node.getFreq() > nodeList.get(nodeList.size()-1).getFreq()) {
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
