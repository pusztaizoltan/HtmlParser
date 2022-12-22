package com.training.htmlparser.model.wordsstore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class WordsStore {
    protected final Map<String, Integer> contentMap = new HashMap<>();
    private final Set<String> skipWords = new HashSet<>();

    public void addSkipWord(String word) {
        skipWords.add(word.toLowerCase());
    }

    private boolean isSkippable(String word) {
        return skipWords.contains(word);
    }

    public void store(String word) { // TODO ZP: @Nullable, @Nonnull annotations (javax)
        String rawWord = word.toLowerCase();
        if (!isSkippable(rawWord)) {
            Integer wordCount = contentMap.get(rawWord);
            contentMap.put(rawWord, wordCount == null ? 1 : wordCount + 1);
        }
    }

    /**
     * Select one or multiple element from ContentMap field.
     * Selection strategy is specific to and implemented by subClasses
     */
    public abstract Map<String, Integer> select();
}
