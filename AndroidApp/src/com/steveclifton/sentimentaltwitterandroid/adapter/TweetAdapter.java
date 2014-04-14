package com.steveclifton.sentimentaltwitterandroid.adapter;

import java.util.List;

import com.steveclifton.sentimentaltwitterandroid.R;
import com.steveclifton.sentimentaltwitterandroid.model.Tweet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetAdapter extends ArrayAdapter<Tweet> {
	
	public TweetAdapter(Context context, int resource, List<Tweet> objects) {
		super(context, resource, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	       Tweet tweet = getItem(position);    

	       if (convertView == null) {
	          convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
	       }
	      
	       TextView tvHandle = (TextView) convertView.findViewById(R.id.handle);
	       TextView tvTweet = (TextView) convertView.findViewById(R.id.tweet);
	       TextView tvAge = (TextView) convertView.findViewById(R.id.tweet_age);
	       ImageView ivIcon = (ImageView) convertView.findViewById(R.id.tweet_icon);
	       
	       tvHandle.setText(tweet.getHandle());
	       tvTweet.setText(tweet.getTweetText());
//	       tvAge.setText(tweet.getTimestamp());	TODO
//	       ivIcon.setImageBitmap(bm); // TODO
	       
	       return convertView;
	}

	@Override
	public int getCount() {
		return super.getCount();
	}
	

}
