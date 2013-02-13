package com.dev.campus.util;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

public class PrefFragment extends PreferenceFragment 
implements SharedPreferences.OnSharedPreferenceChangeListener {

	private SubscribeDialog mSubscribeDialog;
	private FilterDialog mFilterDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		mSubscribeDialog = new SubscribeDialog(getActivity());
		mFilterDialog = new FilterDialog(getActivity());
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(PrefFragment.this);
	}
	
	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
		if (preference.getKey().equals(Persistence.PREF_SCREEN_SUBSCRIBE)) {
			mSubscribeDialog.showDialog(false);
		}
		if (preference.getKey().equals(Persistence.PREF_SCREEN_FILTERS)) {
			mFilterDialog.showDialog();
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
		
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		boolean state;
		if (key.equals(Persistence.PREF_SUBSCRIBE_UB1)) {
			state = CampusUB1App.persistence.isSubscribedUB1();
			CampusUB1App.persistence.setFilterUB(state);
		}
		
		if (key.equals(Persistence.PREF_SUBSCRIBE_LABRI)) {
			state = CampusUB1App.persistence.isSubscribedLabri();
			CampusUB1App.persistence.setFilterLabri(state);
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(PrefFragment.this);
	}
}

