package com.steveclifton.sentimentaltwitterandroid.activity;

import java.util.ArrayList;

import com.steveclifton.sentimentaltwitterandroid.R;
import com.steveclifton.sentimentaltwitterandroid.model.SentimentManager;
import com.steveclifton.sentimentaltwitterandroid.model.SentimentManager.SentimentListener;
import com.steveclifton.sentimentaltwitterandroid.model.Tweet;
import com.steveclifton.sentimentaltwitterandroid.network.TwitterManager;
import com.steveclifton.sentimentaltwitterandroid.network.TwitterManager.QueryResponseListener;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements QueryResponseListener, SentimentListener {
	
	TwitterManager mTm = null;
	SentimentManager mSentiment = null;
	
	ArrayList<Tweet> mTweets = new ArrayList<Tweet>();
	private Handler mHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTm = new TwitterManager();
		mSentiment = new SentimentManager();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onSearch(View aView) {
		EditText et = (EditText)findViewById(R.id.edit_searchterm);
		String string = et.getText().toString();
		mTm.startQuery(string, this);
	}


	
	@Override
	public void queryResponse(String aQuery, ArrayList<Tweet> aTweets) {
		mTweets = aTweets;
		mSentiment.checkSentiment(aTweets, this);
	}

	@Override
	public void sentimentProgress(int aDone, int aTotal) {
		
		// TODO - Replace with dialog	
		Log.d("SJC","Sentiment "+aDone+"/"+aTotal);
		if ( aDone == aTotal ) {
			Intent intent = new Intent();
			intent.setClass(this, ListActivity.class);
			intent.putExtra(ListActivity.TWEETS, mTweets);
			startActivity(intent);	
		}

	}
}
