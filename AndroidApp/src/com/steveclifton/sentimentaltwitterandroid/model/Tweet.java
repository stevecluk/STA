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
	
	public enum Sentiment {
		EHappy,
		EIndifferent,
		ESad
	};
		
	
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
	
	public long getTimestamp() {
		if ( mDate != null )
			return mDate.getTime();
		return 0;
	}
	
	public void setTimestamp(Date aDate) {
		mDate = aDate;
	}
	
	public Sentiment getSentiment() {
		if ( mDetectedValue < -0.25 ) return Sentiment.ESad;
		else if ( mDetectedValue <= 0.25 ) return Sentiment.EIndifferent;
		else return Sentiment.EHappy;		
	}
	
	private String mDetectedLang  = null;
	private double mDetectedValue = Double.MIN_VALUE;
	private int    mDetectedSent  = Integer.MIN_VALUE;
	
	public void setDetectedLanguage(String aLang) {	
		mDetectedLang = aLang;		
	}	
	
	public void setDetectedValue(double aVal){
		mDetectedValue = aVal;
	}
	public double getDetectedValue() {
		return mDetectedValue;
	}
	
	public void setDetectedSent(int aVal) {
		mDetectedSent = aVal;
	}
	public int getDetectedSent() {
		return mDetectedSent;
	}
}
