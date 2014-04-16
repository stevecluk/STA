package com.steveclifton.sentimentaltwitterandroid.model;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.steveclifton.sentimentaltwitterandroid.R;
import com.steveclifton.sentimentaltwitterandroid.StaApplication;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class SentimentManager {
	public interface SentimentListener {
		public void sentimentProgress(int aDone, int aTotal);
	};
	
	private final static String URL_SENTIMENT = "https://sentimentalsentimentanalysis.p.mashape.com/sentiment/current/classify_text/";
	private final static String HDR_SENTIMENT = "X-Mashape-Authorization";
	
	public void checkSentiment(ArrayList<Tweet> aResult, SentimentListener aListener) {

		SentimentTask sentimentTask = new SentimentTask();
		sentimentTask.start(aResult, aListener); 
	}
	
	// Need to ask for one now..
	class SentimentTask extends AsyncTask<Tweet, Void, Void> {
		
		SentimentListener mListener = null;
		
		public void start(ArrayList<Tweet> aResult, SentimentListener aListener) {
			// Marshal into the correct type of array
			Tweet[] array = new Tweet[aResult.size()];
			for ( int i=0; i<aResult.size(); i++ )
				array[i] = aResult.get(i);
			mListener = aListener;
			
			String maShapeKey = StaApplication.getContext().getResources().getString(R.string.mashape_key);
			if ( maShapeKey.length() == 0 ) {
				Toast.makeText(StaApplication.getContext(), "No MaShape key specified, so no sentiment info available.  Edit 'mashape_key' ", Toast.LENGTH_LONG).show();
			}
			execute(array);
		}

		@Override
		protected Void doInBackground(Tweet... params) {
			try {
				int total = params.length;
					
				String maShapeKey = StaApplication.getContext().getResources().getString(R.string.mashape_key);
												
				for ( int i=0; i<total; i++ ) {
					Tweet tweet = params[i];
										
					// Only make the MaShape request if we actually have a key
					if ( maShapeKey.length() > 0 ) {
						HttpResponse<JsonNode> request = Unirest.post(URL_SENTIMENT)
								  .header(HDR_SENTIMENT,maShapeKey)
								  .field("lang", "en")
								  .field("text", tweet.getTweetText())
								  .asJson();				
						
						// Update the tweet with the updated values
						JsonNode s = request.getBody();
						JSONObject ss = s.getObject();
						tweet.setDetectedLanguage( ss.getString("language") );
						tweet.setDetectedValue( ss.getDouble("value") );
						tweet.setDetectedSent( ss.getInt("sent") );	
					}
					
					// Notify listener of update
					if ( mListener != null )
						mListener.sentimentProgress(i+1, total);
				}
			} catch (UnirestException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
	
			return null;
		}				
	};
	

}
