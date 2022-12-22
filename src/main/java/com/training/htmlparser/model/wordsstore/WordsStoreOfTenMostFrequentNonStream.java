package com.training.htmlparser.model.wordsstore;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class WordsStoreOfTenMostFrequentNonStream extends WordsStore{
    @Override
    public List<String> select() {
        return List.of();
    }

    private LinkedHashMap<String, Integer> getTenMostFrequent() {
        return super.contentMap.entrySet()
                               .stream()
                               .sorted((i, j) -> j.getValue() - i.getValue())
                               .limit(10)
                               .collect(Collectors.toMap(
                                       Map.Entry::getKey,
                                       Map.Entry::getValue,
                                       (x, y) -> y,
                                       LinkedHashMap::new
                               ));
    }
}
