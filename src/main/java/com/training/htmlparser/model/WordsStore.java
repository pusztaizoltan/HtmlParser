package com.training.htmlparser.model;

public interface WordsStore {
	void store(String word);

	void addSkipWord(String word);
}
