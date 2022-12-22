package com.training.htmlparser.model.wordsstore.selectoralgorithms;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TenMostFrequent implements Selector {
    private static final int SELECTION_LIMIT = 10;

    @Override
    public List<String> selectFrom(Map<String, Integer> content) {
        LinkedHashMap<String, Integer> selected = getTenMostFrequent(content);
        int formatterMargin = selected.keySet().stream().mapToInt(key -> key.length()).max().orElse(0);
        return selected.entrySet()
                       .stream()
                       .map(entry -> formatToMargin(entry, formatterMargin))
                       .collect(Collectors.toList());
    }

    @NotNull
    private String formatToMargin(@NotNull Map.Entry<String, Integer> entry, int margin) {
        int keyLength = entry.getKey().length();
        return entry.getKey() + " ".repeat(margin - keyLength + 1) + ":" + entry.getValue();
    }

    @NotNull
    private LinkedHashMap<String, Integer> getTenMostFrequent(Map<String, Integer> content) {
        return content.entrySet()
                      .stream()
                      .sorted((i, j) -> j.getValue() - i.getValue())
                      .limit(SELECTION_LIMIT)
                      .collect(Collectors.toMap(
                              Map.Entry::getKey,
                              Map.Entry::getValue,
                              (x, y) -> y,
                              LinkedHashMap::new
                      ));
    }
}
