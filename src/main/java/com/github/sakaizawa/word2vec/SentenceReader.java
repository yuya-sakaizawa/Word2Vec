package com.github.sakaizawa.word2vec;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.InflaterInputStream;

/**
 * Created by sakaisawayuya on 2015/09/24.
 */
public class SentenceReader
    implements Iterator<String[]>, Iterable<String[]>{

    private BufferedReader bufferedReader;
    private List<String[]> sentence;
    private String line;

    public SentenceReader(File file) throws FileNotFoundException {
        FileReader fileReader = new FileReader(file);
        this.bufferedReader = new BufferedReader(fileReader);

    }

    public Iterator iterator() {
        if (bufferedReader.equals(null)) {
            return sentence.iterator();
        } else {
            return this;
        }
    }

    @Override
    public boolean hasNext() {
        try {
            this.line = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.line != null;
    }

    @Override
    public String[] next() {
        return this.line.split(" ");
    }

    @Override
    public void remove() {

    }
}
