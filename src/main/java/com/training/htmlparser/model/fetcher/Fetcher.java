package com.training.htmlparser.model.fetcher;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Fetcher {
    protected final List<String> wordContent = new ArrayList<>();
    protected final Set<String> skipTags = new HashSet();
    protected final String url;

    /**
     * Provide implementation framework to subclasses with distinct fetch technologies
     * Concrete fetch algorithms implemented in the constructors of subclasses
     */
    protected Fetcher(@NotNull String url) {
        this.url = url;
    }

    @NotNull
    public List<String> getWordContent() {
        return this.wordContent;
    }

    public void addSkipTag(@NotNull String tag) {
        this.skipTags.add(tag);
    }

    /**
     * Process the HTML content of the fetched url by extracting
     * human-visible text content and storing it in wordContent field
     * word by word.
     * Implemented in subclasses, given that different fetching technologies
     * can produce HTML content Objects with different structure.
     */
    public abstract void processWordContent();
}
