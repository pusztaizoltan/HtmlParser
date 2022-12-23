package com.training.htmlparser.model.wordsstore.selectoralgorithms;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ParametrizedSelector {
    @NotNull List<String> selectByParameter(int parameter);
}
