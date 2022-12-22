package com.training.htmlparser.model.wordsstore.selectoralgorithms;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public interface Selector {
    @NotNull
    List<String> selectFrom(@NotNull Map<String, Integer> content);
}
