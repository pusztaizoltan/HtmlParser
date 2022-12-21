package com.training.htmlparser.model.fetcher;

import java.util.ArrayList;
import java.util.List;

public abstract class Fetcher {
    protected final List<String> wordContent = new ArrayList<>();
    protected final ArrayList<String> skipTags = new ArrayList<>();

    public List<String> getWordContent() {
        return this.wordContent;
    }

    public void addSkipTag(String tag) {
        this.skipTags.add(tag);
    }

    public abstract void processWordContent();
}
