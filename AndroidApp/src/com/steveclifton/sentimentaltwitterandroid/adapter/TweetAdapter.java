package com.steveclifton.sentimentaltwitterandroid.adapter;

import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.steveclifton.sentimentaltwitterandroid.R;
import com.steveclifton.sentimentaltwitterandroid.StaApplication;
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
	       
	       // Set the strings on the view
	       tvHandle.setText(tweet.getScreenName());
	       tvTweet.setText(tweet.getTweetText());	  
	       tvAge.setText(getTweetAge(tweet));
	       
	       // And set the icon
	       switch (tweet.getSentiment()) {
		       case EHappy:
		    	   ivIcon.setImageResource(R.drawable.happy);
		    	   break;
		       case EIndifferent:
		    	   ivIcon.setImageResource(R.drawable.indifferent);
		    	   break;		    	   
		       case ESad:
		    	   ivIcon.setImageResource(R.drawable.sad);
		    	   break;		    	   
	       }	       
	       
	       return convertView;
	}

	private final int PERIOD_SEC  = 1000;
	private final int PERIOD_MIN  = 60 * PERIOD_SEC;
	private final int PERIOD_HOUR = 60 * PERIOD_MIN;
	private final int PERIOD_DAY  = 24 * PERIOD_HOUR;
	
	
	private CharSequence getTweetAge(Tweet tweet) {
		long diffMs = System.currentTimeMillis() - tweet.getTimestamp();
		String ret = "";
		
		if ( Math.abs(diffMs) < PERIOD_MIN )  {
			ret = StaApplication.getContext().getResources().getString(R.string.period_s_ago, diffMs/PERIOD_SEC);
		} else if ( Math.abs(diffMs) < PERIOD_HOUR )  {
			ret = StaApplication.getContext().getResources().getString(R.string.period_m_ago, diffMs/PERIOD_MIN);
		} else if ( Math.abs(diffMs) < PERIOD_DAY )  {
			ret = StaApplication.getContext().getResources().getString(R.string.period_h_ago, diffMs/PERIOD_HOUR);
		} else {
			String format = "HH:mm dd-MM-yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
			ret = sdf.format(new Date(tweet.getTimestamp()));
		}
		return ret;
	}

	@Override
	public int getCount() {
		return super.getCount();
	}
	

}
