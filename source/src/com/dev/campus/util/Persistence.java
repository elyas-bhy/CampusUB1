package com.dev.campus.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Persistence {
	
	public static final String PREF_SUBSCRIBE_UB1 = "subscribe_ub1";
	public static final String PREF_SUBSCRIBE_LABRI = "subscribe_labri";
	public static final String PREF_FILTER_UB1 = "filter_ub1";
	public static final String PREF_FILTER_LABRI = "filter_labri";
	
	private SharedPreferences shared_prefs;
	private SharedPreferences.Editor prefs_editor;
	
	public Persistence(Context context) {
		shared_prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs_editor = shared_prefs.edit();
	}
	
	public boolean isUBSubscribed() {
		return shared_prefs.getBoolean(PREF_SUBSCRIBE_UB1, false);
	}
	
	public boolean isLabriSubscribed() {
		return shared_prefs.getBoolean(PREF_SUBSCRIBE_LABRI, false);
	}
	
	public void setSubscribeUB(boolean subscribed) {
		prefs_editor.putBoolean(PREF_SUBSCRIBE_UB1, subscribed);
		prefs_editor.commit();
	}
	
	public void setSubscribeLabri(boolean subscribed) {
		prefs_editor.putBoolean(PREF_SUBSCRIBE_LABRI, subscribed);
		prefs_editor.commit();
	}
	
	public void setFilterUB(boolean on) {
		prefs_editor.putBoolean(PREF_FILTER_UB1, on);
		prefs_editor.commit();
	}
	
	public void setFilterLabri(boolean on) {
		prefs_editor.putBoolean(PREF_FILTER_LABRI, on);
		prefs_editor.commit();
	}

}