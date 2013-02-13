package com.dev.campus.ac;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;
import com.dev.campus.SettingsActivity;
import com.dev.campus.util.FilterDialog;

import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class EventsActivity extends Activity {

	private final int update_interval = 15000; // Temps mise à jour automatique
	
	private ActionBar mActionBar;
	private FilterDialog mFilterDialog;
	private Handler update_handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);
		mFilterDialog = new FilterDialog(this);
        mActionBar = getActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        update_handler = new Handler();
        startUpdateTimer();
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
    			mFilterDialog.showDialog();
				return true;
			case R.id.event_update:
				update();
				return true;
			case android.R.id.home:
				finish();
				return true;
			default:
    			return super.onOptionsItemSelected(item);
		}
		
	}

	
	Runnable update_statusChecker = new Runnable()
	{
	     @Override 
	     public void run() {
	    	 if (CampusUB1App.persistence.isWifiConnected())
	    		 update();
	         update_handler.postDelayed(update_statusChecker, update_interval);
	     }
	};
	

	void startUpdateTimer()
	{
	    update_statusChecker.run(); 
	}

	void stopUpdateTimer()
	{
	    update_handler.removeCallbacks(update_statusChecker);
	}
	

	public void update(){ 
		if (!CampusUB1App.persistence.isWifiConnected()	&& !CampusUB1App.persistence.isMobileConnected()){
			Toast t = Toast.makeText(this, "Echec lors de la mise à jour!", Toast.LENGTH_SHORT);
			t.show();
		}
		else{ //TODO
			Toast t = Toast.makeText(this, "Mise à jour effectuée!", Toast.LENGTH_SHORT);
			t.show();
		}
	}
	
}
