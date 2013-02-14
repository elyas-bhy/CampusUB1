package com.dev.campus.ac;

import com.dev.campus.util.PrefFragment;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;


public class SettingsActivity extends Activity {
	
	private ActionBar mActionBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefFragment()).commit();
        
        mActionBar = getActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			default:
    			return super.onOptionsItemSelected(item);
		}
	}
}
