package com.dev.campus;

import com.dev.campus.util.PrefFragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;


public class SettingsActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefFragment()).commit();
    }
	
	private void init() {
		SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	}
}
