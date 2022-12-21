package com.training.htmlparser.model.fetcher;

import java.util.List;

public interface Fetcher {
	List<String> getWordContent();

	void processWordContent();
}
