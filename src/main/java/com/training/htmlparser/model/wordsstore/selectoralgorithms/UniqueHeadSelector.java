package com.training.htmlparser.model.wordsstore.selectoralgorithms;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UniqueHeadSelector implements ContentAccess<List<String>>, ParametrizedSelector {
    private final List<String> content = new ArrayList<>();

    @Override
    public void store(@NotNull String word) {
        if (!content.contains(word)) {
            this.content.add(word);
        }
    }

    @Override
    public @NotNull List<String> getContent() {
        return this.content;
    }

    @Override
    public @NotNull List<String> select() {
        return new ArrayList<>(getContent());
    }

    @Override
    public @NotNull List<String> selectFrom(@NotNull List<String> content) {
        throw new UnsupportedOperationException("LimitedSelector doesn't support this operation");
    }

    // todo added new selection type
    @Override
    public @NotNull List<String> selectByParameter(int length) {
        if (length < 0) {
            return select();
        }
        return new ArrayList<>(this.content.subList(0, Math.min(length, content.size()))); // defensive copy as subList return view
    }
}
