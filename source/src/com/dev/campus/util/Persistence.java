package com.dev.campus.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.preference.PreferenceManager;

public class Persistence {
	
	public static final String PREF_SCREEN_SUBSCRIBE = "subscriptions_screen";
	public static final String PREF_SCREEN_FILTERS = "filters_screen";
	public static final String PREF_SUBSCRIBE_UB1 = "subscribe_ub1";
	public static final String PREF_SUBSCRIBE_LABRI = "subscribe_labri";
	public static final String PREF_FILTER_UB1 = "filter_ub1";
	public static final String PREF_FILTER_LABRI = "filter_labri";
	
	
	private ConnectivityManager conMan;
	private SharedPreferences shared_prefs;
	private SharedPreferences.Editor prefs_editor;
	private State mobile;
	private State wifi;
	
	public Persistence(Context context) {
		shared_prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs_editor = shared_prefs.edit();
		conMan = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		mobile = null;
		wifi = null;
	}
	
	public boolean isSubscribedUB1() {
		return shared_prefs.getBoolean(PREF_SUBSCRIBE_UB1, false);
	}
	
	public boolean isSubscribedLabri() {
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
	
	public boolean isFilteredUB1() {
		return shared_prefs.getBoolean(PREF_FILTER_UB1, false);
	}
	
	public boolean isFilteredLabri() {
		return shared_prefs.getBoolean(PREF_FILTER_LABRI, false);
	}
	
	public void setFilterUB(boolean on) {
		prefs_editor.putBoolean(PREF_FILTER_UB1, on);
		prefs_editor.commit();
	}
	
	public void setFilterLabri(boolean on) {
		prefs_editor.putBoolean(PREF_FILTER_LABRI, on);
		prefs_editor.commit();
	}

	public boolean isMobileConnected(){
		mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		return (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING);
	}
	
	public boolean isWifiConnected(){
		wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		return (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING);
	}
}
