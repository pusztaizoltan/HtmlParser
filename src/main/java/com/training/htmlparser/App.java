package com.training.htmlparser;

import com.training.htmlparser.model.wordsstore.WordsStore;
import com.training.htmlparser.model.wordsstore.WordsStoreImpl;
import com.training.htmlparser.model.fetcher.Fetcher;
import com.training.htmlparser.model.fetcher.HtmlFetcher;

public class App {
    public static void main(String[] args) {
        // outOfThread
        WordsStore wordsStore = new WordsStoreImpl();

        // inThread
        Fetcher fetcher = new HtmlFetcher("https://justinjackson.ca/words.html");
//        Fetcher fetcher = new JsoupFetcher("https://justinjackson.ca/words.html");
        fetcher.addSkipTag("head");
        fetcher.addSkipTag("style");
        fetcher.processWordContent();

        for (String word : fetcher.getWordContent()) {
            wordsStore.store(word);
        }
        wordsStore.getMostFrequent(10).forEach(System.out::println);
    }
}
