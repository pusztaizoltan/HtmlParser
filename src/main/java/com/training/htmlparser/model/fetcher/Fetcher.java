package com.training.htmlparser.model.fetcher;

import java.util.List;

public interface Fetcher {
	List<String> getWordContent();

	void addSkipTag(String tag);

	void processWordContent();
}
