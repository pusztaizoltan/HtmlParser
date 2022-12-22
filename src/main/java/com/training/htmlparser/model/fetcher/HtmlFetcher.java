package com.training.htmlparser.model.fetcher;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class HtmlFetcher extends Fetcher {
    private String doc;
    private URLConnection connection;
    private String splitPattern = "";

    public HtmlFetcher(String url) {
        super(url);
        try {
            this.connection = new URL(super.url).openConnection();
            fetch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fetch() {
        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            scanner.useDelimiter("\\Z");
            doc = scanner.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSkipTag(String tag) {
        super.addSkipTag(tag);
        if (!this.splitPattern.equals("")) {
            this.splitPattern += "|";
        }
        this.splitPattern += tag;
    }

    @Override
    public void processWordContent() {
        String endTagPattern = "</(" + this.splitPattern + ")>";
        Arrays.stream(doc.split(endTagPattern))
              .map(this::clearSkippableTagContent)
              .flatMap(this::getTextContent)
              .peek(System.out::print) //todo
              .flatMap(this::getWords)
//              .filter(i->!i.isBlank())
              .forEach(super.wordContent::add);
    }

    private String clearSkippableTagContent(String text) {
        String startTagPattern = "<(" + this.splitPattern + ") .*";
        return text.replaceAll(startTagPattern, "").trim();
    }

    private Stream<String> getTextContent(String text) {
        return Arrays.stream(text.split(" *(?=<)|(?<=>) *"))
                     .filter(this::isTextContent);
    }

    private boolean isTextContent(String text) {
        return !text.isBlank() && !text.contains("<") && !text.contains(">");
    }

    private Stream<String> getWords(String text) {
        return Arrays.stream(text.trim().strip().replaceAll("[^a-zA-Z]+", " ").split(" "));
    }
}