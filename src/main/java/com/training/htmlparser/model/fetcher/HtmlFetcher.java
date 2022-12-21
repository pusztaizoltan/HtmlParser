package com.training.htmlparser.model.fetcher;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class HtmlFetcher implements Fetcher {
/*
 this implementation is not ready to use
*/

	private String content;
	private URLConnection connection;
	private final ArrayList<String> skipTags = new ArrayList<>(List.of("style", "head"));

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

	@Override
	public List<String> getWordContent() {
		return null;
	}

	@Override
	public void processWordContent() {
	}
}