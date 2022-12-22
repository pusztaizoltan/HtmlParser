package com.training.htmlparser.model.fetcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Fetcher {
    protected final List<String> wordContent = new ArrayList<>();
    protected final Set<String> skipTags = new HashSet();

    public List<String> getWordContent() {
        return this.wordContent;
    }

    public void addSkipTag(String tag) {
        this.skipTags.add(tag);
    }

    // TODO ZP: javadoc
    public abstract void processWordContent();
}
