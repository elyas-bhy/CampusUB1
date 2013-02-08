package com.dev.campus;

import com.dev.campus.util.Persistence;

import android.app.Application;
import android.util.Log;

public class CampusUB1App extends Application {
	
	public static final String TAG = "CampusUB1";
	
	public static final boolean DEBUG = true;
	public static Persistence persistence;
	private static CampusUB1App instance;
	
	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}
	
	private void init() {
		LogD("Initializing app");
		instance = this;
		persistence = new Persistence(this);
	}
	
	@Override
	public void onTerminate() {
		LogD("CampusUB1 app terminated!");
		super.onTerminate();
	}
	
	public static CampusUB1App getInstance() {
		return instance;
	}
	
	public static void LogD(String message) {
		if (DEBUG) Log.d(TAG, message);
	}
	
	
}