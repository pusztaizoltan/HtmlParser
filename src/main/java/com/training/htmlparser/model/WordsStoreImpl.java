package com.training.htmlparser.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class WordsStoreImpl implements WordsStore {
	private final Set<String> skipWords = new HashSet<>();
	private final Map<String, Integer> contentMap = new HashMap<>();

	private boolean isSkippable(String word) {
		return skipWords.contains(word);
	}

	@Override
	public void store(String word) {
		String rawWord = word.toLowerCase();
		if (!isSkippable(rawWord)) {
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

	public List<String> getMostFrequent(int limit) {
		return contentMap.entrySet()
		                 .stream()
		                 .sorted((i, j) -> j.getValue() - i.getValue())
		                 .limit(limit)
		                 .map(Map.Entry::getKey)
		                 .collect(Collectors.toList());
	}
}
