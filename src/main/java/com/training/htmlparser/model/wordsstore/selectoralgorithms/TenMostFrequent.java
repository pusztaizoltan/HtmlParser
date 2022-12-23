package com.training.htmlparser.model.wordsstore.selectoralgorithms;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TenMostFrequent implements ContentAccess<Map<String, Integer>> {
    private static final int SELECTION_LIMIT = 10;
    private final Map<String, Integer> content = new LinkedHashMap<>();

    public @NotNull Map<String, Integer> getContent() {
        return content;
    }

    @Override
    public void store(@NotNull String word) {
        Integer wordCount = content.get(word);
        this.content.put(word, wordCount == null ? 1 : wordCount + 1);
    }

    @Override
    public @NotNull List<String> select() {
        return selectFrom(this.content);
    }

    @Override
    public List<String> selectFrom(Map<String, Integer> content) {
        Set<Map.Entry<String, Integer>> selected = getTenMostFrequent(content);
        int formatterMargin = selected.stream().mapToInt(entry -> entry.getKey().length()).max().orElse(0);
        return selected.stream()
                       .map(entry -> formatToMargin(entry, formatterMargin))
                       .collect(Collectors.toList());
    }

    private @NotNull String formatToMargin(@NotNull Map.Entry<String, Integer> entry, int margin) {
        int keyLength = entry.getKey().length();
        return entry.getKey() + " ".repeat(margin - keyLength + 1) + ":" + entry.getValue();
    }

    private @NotNull LinkedHashSet<Map.Entry<String, Integer>> getTenMostFrequent(@NotNull Map<String, Integer> content) {
        return content.entrySet()
                      .stream()
                      .sorted((i, j) -> j.getValue() - i.getValue())
                      .limit(SELECTION_LIMIT)
                      .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
