package com.training.htmlparser.model.wordsstore;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class WordsStoreOfTenMostFrequentNonStream extends WordsStore {
    private final Comparator<Map.Entry<String, Integer>> byDescendingValue = (a, b) -> b.getValue() - a.getValue();

    @Override
    public List<String> select() {
        TreeSet<Map.Entry<String, Integer>> selected = getTenMostFrequent();
        int formatterMargin = selected.stream().mapToInt(entry -> entry.getKey().length()).max().orElse(0);
        return selected.stream()
                       .map(entry -> formatToMargin(entry, formatterMargin))
                       .collect(Collectors.toList());
    }

    private String formatToMargin(Map.Entry<String, Integer> entry, int margin) {
        var keyLength = entry.getKey().length();
        return entry.getKey() + " ".repeat(margin - keyLength + 1) + ":" + entry.getValue();
    }

    private TreeSet<Map.Entry<String, Integer>> getTenMostFrequent() {
        TreeSet<Map.Entry<String, Integer>> collection = new TreeSet<>(byDescendingValue);
        for (Map.Entry<String, Integer> entry : super.contentMap.entrySet()) {
            collection.add(entry);
            if (collection.size() > 10) {
                collection.pollLast();
            }
        }
        return collection;
    }
}