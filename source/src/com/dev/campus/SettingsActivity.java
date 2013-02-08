package com.dev.campus;

import com.dev.campus.util.PrefFragment;

import android.app.Activity;
import android.os.Bundle;


public class SettingsActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefFragment()).commit();
	}
}
