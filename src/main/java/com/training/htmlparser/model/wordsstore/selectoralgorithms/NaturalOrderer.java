package com.training.htmlparser.model.wordsstore.selectoralgorithms;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NaturalOrderer implements ContentAccess<List<String>> {
    private final List<String> content = new ArrayList<>();

    @Override
    public void store(@NotNull String word) {
        this.content.add(word);
    }

    @Override
    public @NotNull List<String> getContent() {
        return this.content;
    }

    @Override
    public @NotNull List<String> select() {
        return selectFrom(this.content);
    }

    @Override
    public @NotNull List<String> selectFrom(@NotNull List<String> content) {
        List<String> result = new ArrayList<>(content);
        Collections.sort(result);
        return result;
    }
}
