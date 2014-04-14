package com.steveclifton.sentimentaltwitterandroid.model;

import java.io.Serializable;
import java.util.Date;

public class Tweet implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mScreenName 	= null;
	private String mText 		= null;
	private Date   mDate 		= null;
		
	
	public Tweet() {	
	}
	
	public String getScreenName(){
		return mScreenName;
	}
	
	public void setScreenName(String aHandle) {
		mScreenName = aHandle;
	}
	
	public String getTweetText() {
		return mText;
	}
	
	public void setTweetText(String aText) {
		mText = aText;
	}
	
	public Date getTimestamp() {
		return mDate;
	}
	
	public void setTimestamp(Date aDate) {
		mDate = aDate;
	}
}
