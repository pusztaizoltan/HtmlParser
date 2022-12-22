package com.training.htmlparser.model.wordsstore.selectoralgorithms;

import java.util.List;
import java.util.Map;

public interface Selector {
    List<String> selectFrom(Map<String, Integer> content);
}
