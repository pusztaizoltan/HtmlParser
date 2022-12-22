package com.training.htmlparser.model.wordsstore;

import java.util.Map;
import java.util.stream.Collectors;

public class WordsStoreOfTenMostFrequent extends WordsStore {
    @Override
    public Map<String, Integer> select() { // TODO ZP: could you please do this without using stream? :)
        return getTenMostFrequent();
    }

    private Map<String, Integer> getTenMostFrequent() {
        return super.contentMap.entrySet()
                               .stream()
                               .sorted((i, j) -> j.getValue() - i.getValue())
                               .limit(10)
                               .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
