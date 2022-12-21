package com.training.htmlparser.model.fetcher;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
public class HtmlFetcher extends Fetcher {
/*
 this implementation is not ready to use
*/

	private String doc;
	private URLConnection connection;


	public HtmlFetcher(String url) {
		try {
			this.connection = new URL(url).openConnection();
			fetch();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fetch() {
		try (Scanner scanner = new Scanner(connection.getInputStream())) {
			scanner.useDelimiter("\\Z");
			doc = scanner.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void processWordContent() {
	}
}