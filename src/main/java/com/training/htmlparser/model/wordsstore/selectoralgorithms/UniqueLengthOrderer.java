package com.training.htmlparser.model.wordsstore.selectoralgorithms;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UniqueLengthOrderer implements ContentAccess<List<String>> {
    //        private static final Comparator<String> BY_LENGTH = (a, b) -> (b.length() - (int) Math.sin(a.length())*2 + a.compareTo(b));
    private static final Comparator<String> BY_LENGTH = (a, b) ->
    {
        int result = b.length() - a.length();
        return result == 0 ? (int) Math.signum(a.compareTo(b)) : result;
    };
    private final List<String> content = new ArrayList<>();

    @Override
    public void store(@NotNull String word) {
        if (!content.contains(word)) {
            this.content.add(word);
        }
    }

    @NotNull
    @Override
    public List<String> getContent() {
        return this.content;
    }

    @Override
    public @NotNull List<String> select() {
        return selectFrom(this.content);
    }

    @Override
    public @NotNull List<String> selectFrom(@NotNull List<String> content) {
        List<String> result = new ArrayList<>(content);
        Collections.sort(result, BY_LENGTH);
        return result;
    }
}
