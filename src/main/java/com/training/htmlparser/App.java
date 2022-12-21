package com.training.htmlparser;

import com.training.htmlparser.model.fetchers.HtmlFetcher;
import com.training.htmlparser.model.fetchers.JsoupFetcher;

public class App {
	public static void main(String[] args) {
//		HtmlFetcher fetcher = new HtmlFetcher("https://justinjackson.ca/words.html");
		JsoupFetcher fetcher = new JsoupFetcher("https://justinjackson.ca/words.html");
		fetcher.show();
	}
}
