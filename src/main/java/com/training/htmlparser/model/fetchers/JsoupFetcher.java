package com.training.htmlparser.model.fetchers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsoupFetcher {
	Document doc;
	private ArrayList<String> skipTags = new ArrayList<>(List.of("style","head"));

	public JsoupFetcher(String url) {
		try {
			this.doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void show(){
		System.out.println(doc);
	}




}
