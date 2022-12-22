package com.training.htmlparser.model.wordsstore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class WordsStore {
    private static final Logger LOGGER = Logger.getLogger(WordsStore.class.getName());
    protected final Map<String, Integer> contentMap = new HashMap<>();
    private final Set<String> skipWords = new HashSet<>();

    public void addSkipWord(String word) {
        skipWords.add(word.toLowerCase());
        LOGGER.log(Level.INFO, String.format("Skipword: %s added", word));
    }

    private boolean isSkippable(String word) {
        return skipWords.contains(word);
    }

    public void store(String word) { // TODO ZP: @Nullable, @Nonnull annotations (javax)
        String rawWord = word.toLowerCase();
        if (!isSkippable(rawWord)) {
            Integer wordCount = contentMap.get(rawWord);
            contentMap.put(rawWord, wordCount == null ? 1 : wordCount + 1);
            LOGGER.log(Level.INFO, String.format("Word{%s: %s } stored", rawWord, wordCount == null ? 1 : wordCount + 1));
        } else {
            LOGGER.log(Level.INFO, String.format("Word{%s} skipped", rawWord));
        }
    }

    /**
     * Select one or multiple element from contentMap field.
     * Selection strategy is specific to and implemented by subClasses.
     */
    public abstract List<String> select();
}
