/*
 * Copyright (C) 2013 CampusUB1 Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dev.campus;

import com.dev.campus.util.Persistence;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

/**
 * Base class for maintaining global application state. 
 * @author CampusUB1 Development Team
 *
 */
public class CampusUB1App extends Application {

	/**
	 * Tag used for logging in the whole app
	 */
	public static final String TAG = "CampusUB1";
	/**
	 * Main debug switch, turns on/off debugging for the whole app
	 */
	public static final boolean DEBUG = false;

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
