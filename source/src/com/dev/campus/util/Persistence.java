package com.dev.campus.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.preference.PreferenceManager;

public class Persistence {

	public static final String PREF_SCREEN_SUBSCRIBE = "pref_subscriptions_screen";
	public static final String PREF_SCREEN_FILTERS = "pref_filters_screen";
	public static final String PREF_SUBSCRIBE_UB1 = "pref_subscribe_ub1";
	public static final String PREF_SUBSCRIBE_LABRI = "pref_subscribe_labri";
	public static final String PREF_FILTER_UB1 = "pref_filter_ub1";
	public static final String PREF_FILTER_LABRI = "pref_filter_labri";
	public static final String PREF_UPCOMING_EVENTS = "pref_upcoming_events";
	public static final String PREF_ABOUT = "pref_about";

	public static final int DEFAULT_UPCOMING_MONTHS = 3;
	public static final int MAX_UPCOMING_MONTHS = 6;

	private ConnectivityManager conMan;
	private SharedPreferences shared_prefs;
	private SharedPreferences.Editor prefs_editor;

	public Persistence(Context context) {
		shared_prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs_editor = shared_prefs.edit();
		conMan = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
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

	public int getUpcomingEventsRange() {
		return shared_prefs.getInt(PREF_UPCOMING_EVENTS, DEFAULT_UPCOMING_MONTHS);
	}

	public void setUpcomingEventsRange(int months) {
		prefs_editor.putInt(PREF_UPCOMING_EVENTS, months);
		prefs_editor.commit();
	}

	public boolean isMobileConnected() {
		State state = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
		return state == NetworkInfo.State.CONNECTED;
	}

	public boolean isWifiConnected() {
		State state = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		return state == NetworkInfo.State.CONNECTED;
	}

	public boolean isConnected() {
		return isMobileConnected() || isWifiConnected();
	}

}
