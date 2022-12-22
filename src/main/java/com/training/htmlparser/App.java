package com.training.htmlparser;

import com.training.htmlparser.model.fetcher.Fetcher;
import com.training.htmlparser.model.fetcher.JsoupFetcher;
import com.training.htmlparser.model.wordsstore.WordsStore;
import com.training.htmlparser.model.wordsstore.WordsStoreOfTenMostFrequent;

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
        wordsStore.select()
                  .entrySet()
                  .stream()
                  .sorted((i, j) -> j.getValue() - i.getValue())
                  .forEach(i ->
                          System.out.println(i.getKey() + (i.getKey().length() < 4 ? "\t\t\t" : "\t\t") + i.getValue())
                  );
    }
}
