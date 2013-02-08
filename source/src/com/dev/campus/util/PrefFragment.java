package com.dev.campus.util;

import com.dev.campus.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceFragment;

public class PrefFragment extends PreferenceFragment 
implements SharedPreferences.OnSharedPreferenceChangeListener {

	private CheckBoxPreference mPrefSubUB1;
	private CheckBoxPreference mPrefSubLabri;
	private CheckBoxPreference mPrefFilterUB1;
	private CheckBoxPreference mPrefFilterLabri;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		init();
	}
	
	private void init() {
		mPrefSubUB1 = (CheckBoxPreference) findPreference(Persistence.PREF_SUBSCRIBE_UB1);
		mPrefSubLabri = (CheckBoxPreference) findPreference(Persistence.PREF_SUBSCRIBE_LABRI);
		mPrefFilterUB1 = (CheckBoxPreference) findPreference(Persistence.PREF_FILTER_UB1);
		mPrefFilterLabri = (CheckBoxPreference) findPreference(Persistence.PREF_FILTER_LABRI);
		
		if (!mPrefSubUB1.isChecked()) {
			mPrefFilterUB1.setChecked(false);
			mPrefFilterUB1.setEnabled(false);
		}
		
		if (!mPrefSubLabri.isChecked()) {
			mPrefFilterLabri.setChecked(false);
			mPrefFilterLabri.setEnabled(false);
		}
		
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(PrefFragment.this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals(Persistence.PREF_SUBSCRIBE_UB1)) {
			if (mPrefSubUB1.isChecked()) {
				mPrefFilterUB1.setEnabled(true);
				mPrefFilterUB1.setChecked(true);
			}
			else {
				mPrefFilterUB1.setChecked(false);
				mPrefFilterUB1.setEnabled(false);
			}
		}
		
		if (key.equals(Persistence.PREF_SUBSCRIBE_LABRI)) {
			if (mPrefSubLabri.isChecked()) {
				mPrefFilterLabri.setEnabled(true);
				mPrefFilterLabri.setChecked(true);
			}
			else {
				mPrefFilterLabri.setChecked(false);
				mPrefFilterLabri.setEnabled(false);
			}
		}
	}
}

