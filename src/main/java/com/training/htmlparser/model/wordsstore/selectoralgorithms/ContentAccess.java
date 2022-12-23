package com.training.htmlparser.model.wordsstore.selectoralgorithms;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ContentAccess<T> {
    @NotNull
    T getContent();

    void store(@NotNull String word);

    @NotNull
    List<String> select();

    @NotNull
    List<String> selectFrom(@NotNull T content);
}
