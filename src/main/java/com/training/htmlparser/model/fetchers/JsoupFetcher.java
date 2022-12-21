package com.training.htmlparser.model.fetchers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsoupFetcher {
	Document doc;
	private ArrayList<String> skipTags = new ArrayList<>(List.of("style","head","em"));

	public JsoupFetcher(String url) {
		try {
			this.doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void show(){
		recursiveShow(doc);
	}

	public void recursiveShow(Element element){
		if(!skipTags.contains(element.nodeName()))
			if(element.hasText()) {
				System.out.println(element.nodeName());
				System.out.println(element.text());
			}
		for(Element child : element.children())
			recursiveShow(child);
	}



}
