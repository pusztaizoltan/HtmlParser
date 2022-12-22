package com.training.htmlparser.model.fetcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

public class JsoupFetcher extends Fetcher {    
    private Document doc;

    public JsoupFetcher(String url) {
        try {
            this.doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            // TODO ZP: logging
            e.printStackTrace();
        }
    }

    @Override
    public void processWordContent() {
        recursiveExtract(this.doc);
    }

    private void recursiveExtract(Element element) {
        if (!super.skipTags.contains(element.nodeName())) {
            if (element.hasText()) {
                super.wordContent.addAll(List.of(element.text().replaceAll("[^\\w]+", " ").split(" "))); // TODO ZP: I would replace special characters with different char
            }
        }
        for (Element child : element.children()) {
            recursiveExtract(child);
        }
    }
}
