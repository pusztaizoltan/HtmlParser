package com.training.htmlparser.model.fetcher;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class JsoupFetcher extends Fetcher {
    private static final Logger LOGGER = Logger.getLogger(JsoupFetcher.class.getName());
    private Document doc;

    public JsoupFetcher(String url) {
        super(url);
        try {
            this.doc = Jsoup.connect(super.url).get();
            LOGGER.log(Level.INFO, String.format("%s is fetched", super.url));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void processWordContent() {
        recursiveExtract(this.doc);
    }

    private void recursiveExtract(@NotNull Element element) {
        if (!super.skipTags.contains(element.nodeName())) {
            if (element.hasText()) {
                super.wordContent.addAll(textToWords(element.text()));
            }
        }
        for (Element child : element.children()) {
            recursiveExtract(child);
        }
    }

    @NotNull
    private List<String> textToWords(@NotNull String text) {
        return Arrays.stream(text.split(" "))
                     .map(word -> word.replaceAll("^[^\\w]+", "").replaceAll("[^\\w]+$", ""))
                     .filter(word -> !word.isBlank())
                     .collect(Collectors.toList());
    }
}
