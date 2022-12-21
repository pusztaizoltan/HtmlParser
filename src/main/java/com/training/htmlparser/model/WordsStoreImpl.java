package com.training.htmlparser.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class WordsStoreImpl implements WordsStore {
	Set<String> skipWords = new HashSet<>();
	Map<String, Integer> contentMap = new HashMap<>();

	private boolean isSkipable(String word) {
		return skipWords.contains(word);
	}

	@Override
	public void store(String word) {
		String rawWord = word.toLowerCase();
		if (!isSkipable(rawWord)) {
			if (contentMap.containsKey(rawWord)) {
				contentMap.compute(word, (key, value) -> value++);
			} else {
				contentMap.put(word, 1);
			}
		}
	}

	@Override
	public void addSkipWord(String word) {
		skipWords.add(word.toLowerCase());
	}

	public List<String> getTenMostFrequent(){
		return contentMap.entrySet()
		                 .stream()
		                 .sorted()
		                 .limit(10)
		                 .map(i->i.getKey())
		                 .collect(Collectors.toList());
	}
}
