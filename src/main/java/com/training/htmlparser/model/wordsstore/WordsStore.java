package com.training.htmlparser.model.wordsstore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// TODO ZP: better name and/or javadoc
public abstract class WordsStore {
    protected final Map<String, Integer> contentMap = new HashMap<>();
    private final Set<String> skipWords = new HashSet<>();

    private boolean isSkippable(String word) {
        return skipWords.contains(word);
    }

    public void store(String word) { // TODO ZP: @Nullable, @Nonnull annotations (javax)
        String rawWord = word.toLowerCase();
        if (!isSkippable(rawWord)) {
            Integer value = contentMap.get(word);
            contentMap.put(word, value == null ? 1 : value + 1);
        }
    }

    public void addSkipWord(String word) {
        skipWords.add(word.toLowerCase());
    }

    public abstract List<String> select();
}
