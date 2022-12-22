package com.training.htmlparser.model.wordsstore.selectoralgorithms;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class TenMostFrequentNonStream implements Selector{
    private static final Comparator<Map.Entry<String, Integer>> BY_DESCENDING_VALUE = (a, b) -> b.getValue() - a.getValue();
    private static final int SELECTION_LIMIT = 10;

    @Override
    public List<String> selectFrom(Map<String, Integer> content) {
        TreeSet<Map.Entry<String, Integer>> selected = getTenMostFrequent(content);
        int formatterMargin = selected.stream().mapToInt(entry -> entry.getKey().length()).max().orElse(0);
        return selected.stream()
                       .map(entry -> formatToMargin(entry, formatterMargin))
                       .collect(Collectors.toList());
    }

    @NotNull
    private String formatToMargin(@NotNull Map.Entry<String, Integer> entry, int margin) {
        int keyLength = entry.getKey().length();
        return entry.getKey() + " ".repeat(margin - keyLength + 1) + ":" + entry.getValue();
    }

    @NotNull
    private TreeSet<Map.Entry<String, Integer>> getTenMostFrequent(Map<String, Integer> content) {
        TreeSet<Map.Entry<String, Integer>> collection = new TreeSet<>(BY_DESCENDING_VALUE);
        for (Map.Entry<String, Integer> entry : content.entrySet()) {
            collection.add(entry);
            if (collection.size() > SELECTION_LIMIT) {
                collection.pollLast();
            }
        }
        return collection;
    }
}
