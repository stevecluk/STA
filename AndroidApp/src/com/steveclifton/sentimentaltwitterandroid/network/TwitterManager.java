package com.steveclifton.sentimentaltwitterandroid.network;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.OAuth2Token;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import com.steveclifton.sentimentaltwitterandroid.StaApplication;
import com.steveclifton.sentimentaltwitterandroid.model.Tweet;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;

public class TwitterManager {
	private static final String TAG = "TwitterManager";
	
	private final static String SEARCH_URL = "https://api.twitter.com/1.1/search/tweets.json?q=%40twitterapi";
	
	private final static String API_KEY = "l24r0wz81yXFgpW0974p0hZy3";
	private final static String API_SECRET = "tsmTqPCnNsTDoisLWp2jDMbDIpLqIQbh6y26f2T2rizSKsIwQj"; // TODO - Would normally hide this in some way (eg XOR it)
	
	private final static String PREF_NAME = "TwitterManager";
	private final static String PREF_TOKEN = "Token";
	
	private Configuration con = null;
	private Twitter twitter = null;
	
	public interface QueryResponseListener {
		public void queryResponse(String aQuery, ArrayList<Tweet> aTweets);
	};
	
	public TwitterManager() {
		ConfigurationBuilder conB = new ConfigurationBuilder();
		conB.setApplicationOnlyAuthEnabled(true);
		conB.setOAuthConsumerKey(API_KEY);
		conB.setOAuthConsumerSecret(API_SECRET);
		
		con = conB.build();		
		twitter = new TwitterFactory(con).getInstance();
		
		doAuth();
	}
	
	private String getToken() {
		// Check for any previously used auth token
		SharedPreferences pref = StaApplication.getContext().getSharedPreferences(PREF_NAME, 0);
		String token = pref.getString(PREF_TOKEN, null);
		return token;
	}
	
	private void setToken(String aToken) {
		SharedPreferences pref = StaApplication.getContext().getSharedPreferences(PREF_NAME, 0);
		Editor editor = pref.edit();
		editor.putString(PREF_TOKEN, aToken);
		editor.commit();
	}
	
	private void doAuth() {
		// Need to ask for one now..
		class AuthTask extends AsyncTask<Void, Void, Void> {

			@Override
			protected Void doInBackground(Void... params) {
				try {
					OAuth2Token t = twitter.getOAuth2Token();					
					String token = t.getAccessToken();
					setToken(token);
					Log.d(TAG,"Twitter token set "+token);
				} catch (TwitterException e) {
					e.printStackTrace();
				}
		
				return null;
			}				
		};
		AuthTask auth = new AuthTask();
		auth.execute();
	}
	
	/**
	 * Performs query in background thread, calls back to listener with results.
	 * @param aQueryTerm
	 * @param aListener
	 * @throws IOException
	 */
	public void startQuery(final String aQueryTerm, final QueryResponseListener aListener) {
		class Op extends AsyncTask<String, Void, ArrayList<Tweet>> {

			protected ArrayList<Tweet> doInBackground(String... params) {
				ArrayList<Tweet> ret = new ArrayList<Tweet>();
				try {					
					Query q = new Query(aQueryTerm);
					QueryResult result = twitter.search(q);
					List<twitter4j.Status> statuses = result.getTweets();
					for ( twitter4j.Status stat : statuses ) {
						Tweet tweet = new Tweet();
						tweet.setScreenName(stat.getUser().getScreenName());
						tweet.setTweetText(stat.getText());
						tweet.setTimestamp(stat.getCreatedAt());
						ret.add(tweet);
					}						
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}				
				return ret;
			}
			@Override
			protected void onPostExecute(ArrayList<Tweet> result) {
				if ( aListener != null )
					aListener.queryResponse(aQueryTerm, result);
			}
		};
		Op task = new Op();
		task.execute(new String[]{aQueryTerm});
	}	
}
