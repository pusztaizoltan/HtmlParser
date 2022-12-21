package com.training.htmlparser.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordsStoreImpl implements WordsStore {
	Set<String> skipWords = new HashSet<>();
	Map<String, Integer> contentMap = new HashMap<>();

	private boolean toSkip(String word) {
		return skipWords.contains(word);
	}

	@Override
	public void store(String word) {
		String rawWord = word.toLowerCase();
		if (!toSkip(rawWord)) {
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
}
