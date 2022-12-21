package com.training.htmlparser.model.fetchers;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HtmlFetcher {
	private String content;
	private URLConnection connection;
	private ArrayList<String> skipTags = new ArrayList<>(List.of("style","head"));

	public HtmlFetcher(String url) {
		try {
			this.connection = new URL(url).openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fetch() {
		try (Scanner scanner = new Scanner(connection.getInputStream())) {
			scanner.useDelimiter("\\Z");
			content = scanner.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print(content);
	}

	public String extractContent(){
//		content.toCharArray().

		return "";
	}

	public void parseChars(char character){

		switch(character){
			case '<':

			case '>':

		}
	}
}