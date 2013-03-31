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
	private UpcomingEventsDialog mUpcomingEventsDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		mSubscribeDialog = new SubscribeDialog(getActivity());
		mFilterDialog = new FilterDialog(getActivity());
		mUpcomingEventsDialog = new UpcomingEventsDialog(getActivity());
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getPreferenceManager().getSharedPreferences()
		.registerOnSharedPreferenceChangeListener(PrefFragment.this);
	}
	
	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
		if (preference.getKey().equals(Persistence.PREF_SCREEN_SUBSCRIBE)) {
			mSubscribeDialog.showDialog(false);
		}
		if (preference.getKey().equals(Persistence.PREF_SCREEN_FILTERS)) {
			mFilterDialog.showDialog();
		}
		if (preference.getKey().equals(Persistence.PREF_UPCOMING_EVENTS)) {
			mUpcomingEventsDialog.show();
		}
		if (preference.getKey().equals(Persistence.PREF_ABOUT)) {
			new AboutDialog(getActivity());
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
		
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
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
		getPreferenceManager().getSharedPreferences()
		.unregisterOnSharedPreferenceChangeListener(PrefFragment.this);
	}
}

