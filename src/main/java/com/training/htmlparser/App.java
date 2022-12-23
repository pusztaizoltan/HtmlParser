package com.training.htmlparser;

import com.training.htmlparser.model.fetcher.Fetcher;
import com.training.htmlparser.model.fetcher.JsoupFetcher;
import com.training.htmlparser.model.wordsstore.WordsStore;
import com.training.htmlparser.model.wordsstore.selectoralgorithms.UniqueLengthOrderer;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

public class App {
    static Formatter customLogFormatter = new Formatter() {
        @Override
        public String format(LogRecord record) {
            return LocalDateTime.now() + " Thread:" + record.getThreadID() + " Level:" + record.getLevel() + " Message:" + record.getMessage() + "\n";
        }
    };

    static WordsStore wordsStore = new WordsStore(new UniqueLengthOrderer());
    static List<String> urls = List.of("https://justinjackson.ca/words.html");

    static void setGlobalLoggerVisibility(Level level) {
        for (Handler handler : LogManager.getLogManager().getLogger("").getHandlers()) {
            handler.setLevel(level);
            handler.setFormatter(customLogFormatter);
        }
    }

    public static void main(String[] args) {
        setGlobalLoggerVisibility(Level.INFO);
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
        wordsStore.select().forEach(System.out::println);
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



