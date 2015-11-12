package com.github.sakaizawa.word2vec;

import java.io.*;
import java.util.*;


/**
 * Created by sakaisawayuya on 2015/09/03.
 */
public class Vocab
    implements Serializable {

    /**
     * @param vocab 単語のユニークなIDを記憶
     * @param index2Word 単語にIDとインデックスがひもづいている list
     */
    private Map<String, Integer> vocab = new HashMap<String, Integer>();
    private List<String> index2Word = new ArrayList<String>();

    /**
     * コーパス内にあるすべての単語に対してユニークなIDを与える
     * @param sentenceReader　テキストを一行一文の形で出力するイテレータ
     */
    public Vocab(SentenceReader sentenceReader) {
        int index = 0;
        for (String[] sentence : sentenceReader) {
            for (String word : sentence) {
                if (!vocab.containsKey(word)) {
                    vocab.put(word, index);
                    index2Word.add(word);
                    index++;
                }
            }
        }
    }

    /**
     * 単語のIDを返す
     * @param word 単語
     * @return 単語のID
     */
    public int getIndex(String word) {
        return vocab.get(word);
    }

    /**
     * 引数の id をもつ単語を返す
     * @param id 単語の id
     * @return その id をもつ単語
     */
    public String getWord(int id) {
        return index2Word.get(id);
    }

    /**
     * vocab 内にある単語の総数を返す
     * @return vocab 内にある単語の総数
     */
    public int vocabNum() {
        return vocab.size();
    }

    /**
     * vocab を返す
     * @return vocab
     */
    public Map<String, Integer> getVocab() {
        return vocab;
    }
}
