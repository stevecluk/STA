package com.steveclifton.sentimentaltwitterandroid.activity;

import com.steveclifton.sentimentaltwitterandroid.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onSearch(View aView) {
		// For now, jump straight to list view.
		Intent intent = new Intent();
		intent.setClass(this, ListActivity.class);
		startActivity(intent);
	}

}
