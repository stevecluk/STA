package com.steveclifton.sentimentaltwitterandroid.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.steveclifton.sentimentaltwitterandroid.R;
import com.steveclifton.sentimentaltwitterandroid.model.Tweet;
import com.steveclifton.sentimentaltwitterandroid.network.TwitterManager;
import com.steveclifton.sentimentaltwitterandroid.network.TwitterManager.QueryResponseListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity implements QueryResponseListener {
	TwitterManager mTm = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTm = new TwitterManager();
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
		Intent intent = new Intent();
		intent.setClass(this, ListActivity.class);
		intent.putExtra(ListActivity.TWEETS, aTweets);
		startActivity(intent);
	}
}
