package com.steveclifton.sentimentaltwitterandroid.model;

import java.util.Date;

public class Tweet {
	public Tweet() {	
	}
	
	public String getHandle(){
		return "TestHandle";
	}
	
	public String getTweetText() {
		return "TestTweet";
	}
	
	public Date getTimestamp() {
		return new Date();
	}
}
