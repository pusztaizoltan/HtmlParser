package com.training.htmlparser.model.wordsstore;

import java.util.List;

public interface WordsStore {
    void store(String word);

    void addSkipWord(String word);

    List<String> getMostFrequent(int limit);
}
