package com.training.htmlparser;

import com.training.htmlparser.model.fetcher.JsoupFetcher;
import com.training.htmlparser.model.wordsstore.WordsStore;
import com.training.htmlparser.model.wordsstore.WordsStoreOfTenMostFrequent;
import com.training.htmlparser.model.fetcher.Fetcher;

public class App {
    public static void main(String[] args) {
        // outOfThread
        WordsStore wordsStore = new WordsStoreOfTenMostFrequent();

        // inThread
//        Fetcher fetcher = new HtmlFetcher("https://justinjackson.ca/words.html");
        Fetcher fetcher = new JsoupFetcher("https://justinjackson.ca/words.html");
        fetcher.addSkipTag("head");
        fetcher.addSkipTag("style");
        fetcher.processWordContent();

        for (String word : fetcher.getWordContent()) {
            wordsStore.store(word);
        }
        wordsStore.select().forEach(System.out::println);
    }
}
