package com.training.htmlparser.model.fetcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsoupFetcher implements Fetcher {
	private Document doc;
	private final List<String> wordContent = new ArrayList<>();
	private final ArrayList<String> skipTags = new ArrayList<>();

	public JsoupFetcher(String url) {
		try {
			this.doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void processWordContent() {
		recursiveExtract(this.doc);
	}

	private void recursiveExtract(Element element) {
		if (!this.skipTags.contains(element.nodeName()))
			if (element.hasText()) {
				this.wordContent.addAll(List.of(element.text().replaceAll("[^\\w]+", " ").split(" ")));
			}
		for (Element child : element.children())
			recursiveExtract(child);
	}

	@Override
	public List<String> getWordContent() {
		return this.wordContent;
	}

	@Override
	public void addSkipTag(String tag) {
		skipTags.add(tag);
	}
}
