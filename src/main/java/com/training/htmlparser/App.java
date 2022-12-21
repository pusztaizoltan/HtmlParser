package com.training.htmlparser;

import com.training.htmlparser.model.HtmlFetcher;

public class App {
	public static void main(String[] args) {
		HtmlFetcher fetcher = new HtmlFetcher("https://justinjackson.ca/words.html");
		fetcher.fetch();
	}
}
