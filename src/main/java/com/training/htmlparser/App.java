package com.training.htmlparser;

import com.training.htmlparser.model.fetcher.Fetcher;
import com.training.htmlparser.model.fetcher.JsoupFetcher;
import com.training.htmlparser.model.wordsstore.WordsStore;
import com.training.htmlparser.model.wordsstore.selectoralgorithms.TenMostFrequent;
import com.training.htmlparser.model.wordsstore.selectoralgorithms.TenMostFrequentNonStream;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class App {
    static WordsStore wordsStore = new WordsStore(new TenMostFrequent());
    static List<String> urls = List.of("https://justinjackson.ca/words.html", "https://justinjackson.ca/words.html");

    static void setGlobalLoggerVisibility(Level level) {
        for (Handler handler : LogManager.getLogManager().getLogger("").getHandlers()) {
            handler.setLevel(level);
        }
    }

    public static void main(String[] args) {
        setGlobalLoggerVisibility(Level.SEVERE);
        ExecutorService executor = Executors.newCachedThreadPool();
        for (String url : urls) {
            executor.execute(fetcherThread(url));
        }
        executor.shutdown();
        try {
            executor.awaitTermination(120, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wordsStore.selectBy(new TenMostFrequentNonStream()).forEach(System.out::println);
    }

    public static Runnable fetcherThread(String url) {
        return () -> {
            Fetcher fetcher = new JsoupFetcher(url);
            fetcher.addSkipTag("head");
            fetcher.addSkipTag("style");
            fetcher.processWordContent();
            wordsStore.storeAll(fetcher.getWordContent());
        };
    }
}



