package com.training.htmlparser.model.wordsstore;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WordsStoreOfTenMostFrequent extends WordsStore {
    @Override
    public List<String> select() {
        LinkedHashMap<String, Integer> selected = getTenMostFrequent();
        int formatterMargin = selected.keySet().stream().mapToInt(key -> key.length()).max().orElse(0) + 1;
        return selected.entrySet()
                       .stream()
                       .map(entry -> formatToMargin(entry, formatterMargin))
                       .collect(Collectors.toList());
    }

    private String formatToMargin(Map.Entry<String, Integer> entry, int margin) {
        var keyLength = entry.getKey().length();
        return entry.getKey() + " ".repeat(margin - keyLength) + entry.getValue();
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
