package com.training.htmlparser.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// TODO ZP: better name and/or javadoc
public class WordsStoreImpl implements WordsStore {
    private final Set<String> skipWords = new HashSet<>();
    private final Map<String, Integer> contentMap = new HashMap<>();

    private boolean isSkippable(String word) {
        return skipWords.contains(word);
    }

    @Override
    public void store(String word) { // TODO ZP: @Nullable, @Nonnull annotations (javax)
        String rawWord = word.toLowerCase();
        if (!isSkippable(rawWord)) {
            // TODO ZP:
            // Integer value = contentMap.get(word);
            // contentMap.put(word, value == null ? 1 : value + 1);
            
            if (contentMap.containsKey(rawWord)) {
                contentMap.compute(word, (key, value) -> (value == null) ? 1 : value + 1);
            } else {
                contentMap.put(word, 1);
            }
        }
    }

    @Override
    public void addSkipWord(String word) {
        skipWords.add(word.toLowerCase());
    }

    @Override
    public List<String> getMostFrequent(int limit) { // TODO ZP: could you please do this without using stream? :)
        return contentMap.entrySet()
                         .stream()
                         .sorted((i, j) -> j.getValue() - i.getValue())
                         .limit(limit)
                         .map(Map.Entry::getKey) // TODO ZP: 
                         .collect(Collectors.toList());
    }
}
