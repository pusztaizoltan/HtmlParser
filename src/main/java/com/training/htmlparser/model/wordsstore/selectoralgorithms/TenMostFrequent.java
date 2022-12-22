package com.training.htmlparser.model.wordsstore.selectoralgorithms;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TenMostFrequent implements Selector {
    private static final int SELECTION_LIMIT = 10;
    @Nullable
    private static TenMostFrequent instance;

    private TenMostFrequent() {
    }

    public static TenMostFrequent getInstance() {
        if (instance == null) {
            instance = new TenMostFrequent();
        }
        return instance;
    }

    @Override
    public List<String> selectFrom(Map<String, Integer> content) {
        Set<Map.Entry<String, Integer>> selected = getTenMostFrequent(content);
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
    private LinkedHashSet<Map.Entry<String, Integer>> getTenMostFrequent(@NotNull Map<String, Integer> content) {
        return content.entrySet()
                      .stream()
                      .sorted((i, j) -> j.getValue() - i.getValue())
                      .limit(SELECTION_LIMIT)
                      .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
