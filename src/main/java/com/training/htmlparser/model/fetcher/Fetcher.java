package com.training.htmlparser.model.fetcher;

import java.util.ArrayList;
import java.util.List;

public abstract class Fetcher {
    protected final List<String> wordContent = new ArrayList<>();
    protected final ArrayList<String> skipTags = new ArrayList<>(); // TODO ZP: based on current usages I would use a Set

    public List<String> getWordContent() {
        return this.wordContent;
    }

    public void addSkipTag(String tag) {
        this.skipTags.add(tag);
    }

    // TODO ZP: javadoc
    public abstract void processWordContent();
}
