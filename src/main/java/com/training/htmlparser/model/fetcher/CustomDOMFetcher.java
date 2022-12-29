package com.training.htmlparser.model.fetcher;

import com.training.htmlparser.model.fetcher.domcomponents.Element;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CustomDOMFetcher extends Fetcher {
    private static final Logger LOGGER = Logger.getLogger(CustomDOMFetcher.class.getName());
    private URLConnection connection;
    private Element doc;

    public CustomDOMFetcher(String url) {
        super(url);
        try {
            this.connection = new URL(super.url).openConnection();
            fetch();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    private void fetch() {
        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            scanner.useDelimiter("\\Z");
            String content = scanner.next();
            doc = new Element(null, content);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void processWordContent() {
        recursiveExtract(this.doc);
    }

    private void recursiveExtract(@NotNull Element element) {
        if (!super.skipTags.contains(element.nodeName()) && element.hasText()) {
            super.wordContent.addAll(textToWords(element.getText()));
        }
        for (Element child : element.getChildren()) {
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
