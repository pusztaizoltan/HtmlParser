package com.training.htmlparser.model.wordsstore;

import com.training.htmlparser.model.wordsstore.selectoralgorithms.ContentAccess;
import com.training.htmlparser.model.wordsstore.selectoralgorithms.ParametrizedSelector;
import com.training.htmlparser.model.wordsstore.selectoralgorithms.UniqueHeadSelector;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WordsStore {
    private static final Logger LOGGER = Logger.getLogger(WordsStore.class.getName());
    private final Set<String> skipWords = new HashSet<>();
    private final ContentAccess defaultContentAccess;

    public WordsStore(ContentAccess contentAccess) {
        this.defaultContentAccess = contentAccess;
    }

    public void addSkipWord(@NotNull String word) {
        skipWords.add(word.toLowerCase());
        LOGGER.log(Level.INFO, String.format("Skipword: %s added", word));
    }

    private boolean isSkippable(@NotNull String word) {
        return skipWords.contains(word);
    }

    public void store(@NotNull String word) {
        String rawWord = word.toLowerCase();
        if (!isSkippable(rawWord)) {
            this.defaultContentAccess.store(rawWord);
            LOGGER.log(Level.INFO, String.format("Word{%s} stored", rawWord));
        } else {
            LOGGER.log(Level.INFO, String.format("Word{%s} skipped", rawWord));
        }
    }

    /**
     * Thread safe mass storing, required for multiThreaded access,
     * given that when words are ready to be stored from a fetching
     * process, they are ready en masse rather than one by one.
     */
    public synchronized void storeAll(@NotNull List<String> words) {
        for (String word : words) {
            this.store(word);
        }
    }

    /**
     * Select one or multiple element from content,
     * using default selector algorithm.
     */
    public @NotNull List<String> select() {
        return this.defaultContentAccess.select();
    }

    /**
     * Select one or multiple element from content.
     * using selector algorithm of other ContentAccess class
     * provided that content types are compatible.
     */
    public @NotNull List<String> selectBy(@NotNull ContentAccess contentAccessAlgorithm) {
        // todo type missmatch test
        return contentAccessAlgorithm.selectFrom(this.defaultContentAccess.getContent());
    }

    // todo added new mediator access to new selection type
    public @NotNull List<String> selectByParameter(int param) {
        ParametrizedSelector access;
        try {
            access = (ParametrizedSelector) this.defaultContentAccess;
        } catch (ClassCastException e) {
            throw new UnsupportedOperationException();
        }
        return access.selectByParameter(param);
    }
}
