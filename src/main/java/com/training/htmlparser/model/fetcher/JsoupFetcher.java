package com.training.htmlparser.model.fetcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private void recursiveExtract(Element element) {
        if (!super.skipTags.contains(element.nodeName())) {
            if (element.hasText()) {
                // dont separate pl. I'am stb.
//                super.wordContent.addAll(List.of(element.text().replaceAll("[^\\w]+", " ").split(" "))); // TODO ZP: I would replace special characters with different char
                super.wordContent.addAll(List.of(element.text().replaceAll("[^a-zA-Z]+", " ").split(" "))); // TODO ZP: I would replace special characters with different char
            }
        }
        for (Element child : element.children()) {
            recursiveExtract(child);
        }
    }
}
