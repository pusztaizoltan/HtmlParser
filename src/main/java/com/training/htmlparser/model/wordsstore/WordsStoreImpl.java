package com.training.htmlparser.model.wordsstore;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class WordsStoreImpl extends WordsStore {
    //    @Nonnull
    @Override
    public List<String> select() { // TODO ZP: could you please do this without using stream? :)
        return super.contentMap.entrySet()
                               .stream()
                               .sorted((i, j) -> j.getValue() - i.getValue())
                               .limit(10)
                               .map(Map.Entry::getKey) // TODO ZP: show frequency too
                               .collect(Collectors.toList());
    }
}
