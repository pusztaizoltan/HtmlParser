package com.training.htmlparser;

import com.training.htmlparser.model.fetcher.Fetcher;
import com.training.htmlparser.model.fetcher.JsoupFetcher;
import com.training.htmlparser.model.wordsstore.WordsStore;
import com.training.htmlparser.model.wordsstore.WordsStoreOfTenMostFrequentNonStream;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class App {
    static void setGlobalLoggerVisibility(Level level) {
        for (Handler handler : LogManager.getLogManager().getLogger("").getHandlers()) {
            handler.setLevel(level);
        }
    }

    public static void main(String[] args) {
        setGlobalLoggerVisibility(Level.SEVERE);
        // outOfThread
        WordsStore wordsStore = new WordsStoreOfTenMostFrequentNonStream();
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
