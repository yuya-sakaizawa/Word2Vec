package com.github.sakaizawa.word2vec;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * Created by sakaisawayuya on 2015/11/12.
 */
public class VocabTest {

    Vocab vocab;

    public VocabTest() throws Exception {
        File file = new File("/Users/sakaisawayuya/Desktop/JX/Word2Vec/data/TestVocab");
        SentenceReader sentenceReader = new SentenceReader(file);
        this.vocab = new Vocab(sentenceReader);
    }

    @Test
    public void testGetIndex() throws Exception {
        assertEquals(0, vocab.getIndex("a"));
        assertEquals(1, vocab.getIndex("b"));
        assertEquals(2, vocab.getIndex("c"));
        assertEquals(3, vocab.getIndex("d"));
        assertEquals(4, vocab.getIndex("e"));
        assertEquals(5, vocab.getIndex("f"));
    }

    @Test
    public void testGetWord() throws Exception {
        assertEquals("a", vocab.getWord(0));
        assertEquals("b", vocab.getWord(1));
        assertEquals("c", vocab.getWord(2));
        assertEquals("d", vocab.getWord(3));
        assertEquals("e", vocab.getWord(4));
        assertEquals("f", vocab.getWord(5));
    }

    @Test
    public void testVocabNum() throws Exception {
        assertEquals(6, vocab.vocabNum());
    }
}