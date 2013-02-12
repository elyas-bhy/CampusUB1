package com.dev.campus.ac;

import com.dev.campus.R;
import com.dev.campus.SettingsActivity;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class EventsActivity extends Activity {
	
	private ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);
		
        mActionBar = getActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.with_actionbar, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_settings:
				startActivity(new Intent(EventsActivity.this, SettingsActivity.class));
				return true;
			case R.id.menu_filters:
				//TODO start filter dialog
				return true;
			case android.R.id.home:
				finish();
				return true;
			default:
    			return super.onOptionsItemSelected(item);
		}
	}

}