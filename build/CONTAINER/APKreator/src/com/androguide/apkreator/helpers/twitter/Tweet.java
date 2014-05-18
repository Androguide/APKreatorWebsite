package com.androguide.apkreator.helpers.twitter;

public class Tweet {

	private static String author;
	private static String content;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		Tweet.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		Tweet.author = author;
	}

}
