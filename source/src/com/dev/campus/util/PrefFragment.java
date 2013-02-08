package com.dev.campus.util;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceFragment;

public class PrefFragment extends PreferenceFragment {

	private CheckBoxPreference mPrefSubUB1;
	private CheckBoxPreference mPrefSubLabri;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		CampusUB1App.LogD("PrefFragment created");
		init();
	}
	
	private void init() {
		mPrefSubUB1 = (CheckBoxPreference) findPreference(Persistence.PREF_SUBSCRIBE_UB1);
		mPrefSubLabri = (CheckBoxPreference) findPreference(Persistence.PREF_SUBSCRIBE_LABRI);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}

