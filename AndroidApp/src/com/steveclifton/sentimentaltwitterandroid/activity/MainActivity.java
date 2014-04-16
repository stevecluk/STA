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
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements QueryResponseListener, SentimentListener {
	
	TwitterManager mTm = null;
	SentimentManager mSentiment = null;
	
	ArrayList<Tweet> mTweets = new ArrayList<Tweet>();
	private Handler mHandler = new Handler();
	
	private Button mButton = null;
	private EditText mEdit = null;
	
	private ProgressDialog mProgress = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		
		mEdit = (EditText) findViewById(R.id.edit_searchterm);
		mEdit.addTextChangedListener( mTextWatcher );
		mButton = (Button) findViewById(R.id.button_search);
		mButton.setEnabled(false);
		
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
		mProgress = new ProgressDialog(this);
		mProgress.setIndeterminate(true);
		mProgress.setMessage(getString(R.string.search_tweets_msg));
		mProgress.show();
				
		
		EditText et = (EditText)findViewById(R.id.edit_searchterm);
		String string = et.getText().toString();
		mTm.startQuery(string, this);
	}


	
	@Override
	public void queryResponse(String aQuery, ArrayList<Tweet> aTweets) {
		mTweets = aTweets;
		mProgress.dismiss();
		mProgress = null;
		
		mProgress = new ProgressDialog(this);
		mProgress.setIndeterminate(false);
		mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgress.setProgress(0);
		mProgress.setMessage(getString(R.string.sentiment_tweets_msg));
		mProgress.show();
		
		
		mSentiment.checkSentiment(aTweets, this);
	}

	@Override
	public void sentimentProgress(int aDone, int aTotal) {		
		float pc = ((float)aDone/(float)aTotal)*100.0f;
		
		mProgress.setProgress((int) pc);
		
		if ( aDone == aTotal ) {
			mProgress.dismiss();
			mProgress = null;
			
			Intent intent = new Intent();
			intent.setClass(this, ListActivity.class);
			intent.putExtra(ListActivity.TWEETS, mTweets);
			startActivity(intent);	
		}
	}
	
	TextWatcher mTextWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// Disable button if no text to search for
			mButton.setEnabled(s.toString().length() > 0);
		}
	};
	
	
}
