package com.steveclifton.sentimentaltwitterandroid.activity;

import java.util.ArrayList;

import com.steveclifton.sentimentaltwitterandroid.R;
import com.steveclifton.sentimentaltwitterandroid.adapter.TweetAdapter;
import com.steveclifton.sentimentaltwitterandroid.model.Tweet;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class ListActivity extends Activity {

	ListView mListView    = null;
	TweetAdapter mAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		mListView = (ListView)findViewById(R.id.tweet_listview);
		
		// Create tweet adapter
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		tweets.add(new Tweet());
		tweets.add(new Tweet());
		tweets.add(new Tweet());
		mAdapter = new TweetAdapter(this, R.layout.list_item, tweets);
		mListView.setAdapter(mAdapter);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
