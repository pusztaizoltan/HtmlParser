package com.training.htmlparser.model;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class HtmlFetcher {
	private String content;
	private URLConnection connection;

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
		System.out.println(content);
	}
}