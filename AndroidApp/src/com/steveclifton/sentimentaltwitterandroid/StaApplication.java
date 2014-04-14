package com.steveclifton.sentimentaltwitterandroid;

import android.app.Application;
import android.content.Context;

public class StaApplication extends Application {
	
	private static Context sContext = null;
	
	@Override
	public void onCreate() {
		sContext = getApplicationContext();
	}
	
	public static Context getContext() {
		return sContext;
	}
}
