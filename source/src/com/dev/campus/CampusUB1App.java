package com.dev.campus;

import com.dev.campus.util.Persistence;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class CampusUB1App extends Application {

	/**
	 * Tag used for logging in the whole app
	 */
	public static final String TAG = "CampusUB1";
	/**
	 * Main debug switch, turns on/off debugging for the whole app
	 */
	public static final boolean DEBUG = true;

	public static Persistence persistence;
	private static CampusUB1App instance;

	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}

	private void init() {
		LogD("App starting on " + Build.MODEL + " by " + Build.MANUFACTURER);
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
	
	public void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	public void showToast(int resid) {
		Toast.makeText(this, resid, Toast.LENGTH_LONG).show();
	}

}