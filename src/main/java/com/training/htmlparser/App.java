package com.training.htmlparser;

import com.training.htmlparser.model.WordsStore;
import com.training.htmlparser.model.WordsStoreImpl;
import com.training.htmlparser.model.fetcher.Fetcher;
import com.training.htmlparser.model.fetcher.JsoupFetcher;

public class App {
	public static void main(String[] args) {
		Fetcher fetcher = new JsoupFetcher("https://justinjackson.ca/words.html");
		fetcher.processWordContent();
		WordsStore wordsStore = new WordsStoreImpl();
		for (String word : fetcher.getWordContent()) {
			wordsStore.store(word);
		}
		wordsStore.getMostFrequent(10).forEach(System.out::println);
	}
}
