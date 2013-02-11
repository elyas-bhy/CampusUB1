package com.dev.campus.util;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

public class PrefFragment extends PreferenceFragment 
implements SharedPreferences.OnSharedPreferenceChangeListener {

	private CheckBoxPreference mPrefFilterUB1;
	private CheckBoxPreference mPrefFilterLabri;
	private SubscribeDialog mSubscribeDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		mPrefFilterUB1 = (CheckBoxPreference) findPreference(Persistence.PREF_FILTER_UB1);
		mPrefFilterLabri = (CheckBoxPreference) findPreference(Persistence.PREF_FILTER_LABRI);
		mSubscribeDialog = new SubscribeDialog(getActivity());
		
		mPrefFilterUB1.setEnabled(CampusUB1App.persistence.isSubscribedUB1());
		mPrefFilterLabri.setEnabled(CampusUB1App.persistence.isSubscribedLabri());
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
		return super.onPreferenceTreeClick(preferenceScreen, preference);
		
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		boolean state;
		if (key.equals(Persistence.PREF_SUBSCRIBE_UB1)) {
			state = CampusUB1App.persistence.isSubscribedUB1();
			mPrefFilterUB1.setEnabled(state);
			mPrefFilterUB1.setChecked(state);
		}
		
		if (key.equals(Persistence.PREF_SUBSCRIBE_LABRI)) {
			state = CampusUB1App.persistence.isSubscribedLabri();
			mPrefFilterLabri.setEnabled(state);
			mPrefFilterLabri.setChecked(state);
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(PrefFragment.this);
	}
}

