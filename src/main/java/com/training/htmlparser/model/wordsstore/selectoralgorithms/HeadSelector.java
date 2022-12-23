package com.training.htmlparser.model.wordsstore.selectoralgorithms;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HeadSelector implements ContentAccess<List<String>> {
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
        return getContent();
    }

    @Override
    public @NotNull List<String> selectFrom(@NotNull List<String> content) {
        throw new UnsupportedOperationException("LimitedSelector doesn't support this operation");
    }

    // todo added new selection type
    public @NotNull List<String> selectByParameter(int length) {
        if (length < 0) {
            return this.content;
        }
        return new ArrayList<>(this.content.subList(0, Math.max(length, content.size()))); // defensive copy as subList return view
    }
}
