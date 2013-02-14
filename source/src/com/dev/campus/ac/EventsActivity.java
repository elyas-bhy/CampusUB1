package com.dev.campus.ac;

import java.util.ArrayList;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;
import com.dev.campus.event.Event;
import com.dev.campus.event.EventAdapter;
import com.dev.campus.util.FilterDialog;

import android.os.Bundle;
import android.os.Handler;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class EventsActivity extends ListActivity {

	private final int updateFrequency = 15000; // Update frequency (ms)
	
	private ActionBar mActionBar;
	private FilterDialog mFilterDialog;
	
	private Handler mHandler;
	private EventAdapter mEventAdapter;
	//private EventParser mEventParser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFilterDialog = new FilterDialog(this);
        mHandler = new Handler();
        mActionBar = getActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        
        //Fetch data from parser
        mEventAdapter = new EventAdapter(this, new ArrayList<Event>());
        setListAdapter(mEventAdapter);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
        startUpdateTimer();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		pauseUpdateTimer();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.with_actionbar_refresh, menu);
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
			case R.id.menu_refresh:
				update();
				return true;
			case android.R.id.home:
				finish();
				return true;
			default:
    			return super.onOptionsItemSelected(item);
		}
	}

	private Runnable update_statusChecker = new Runnable() {
	     @Override 
	     public void run() {
	    	 if (CampusUB1App.persistence.isWifiConnected())
	    		 update();
	         mHandler.postDelayed(update_statusChecker, updateFrequency);
	     }
	};
	
	private void startUpdateTimer() {
	    update_statusChecker.run(); 
	}

	private void pauseUpdateTimer() {
	    mHandler.removeCallbacks(update_statusChecker);
	}
	

	public void update() { 
		if (!CampusUB1App.persistence.isWifiConnected()	&& !CampusUB1App.persistence.isMobileConnected()){
			Toast.makeText(this, "Echec lors de la mise à jour!", Toast.LENGTH_SHORT).show();
			//if history exists, load it for offline use
		}
		else { //TODO
			Toast.makeText(this, "Mise à jour effectuée!", Toast.LENGTH_SHORT).show();
			mEventAdapter.add(new Event("Event " + System.currentTimeMillis()));	//For test purposes
			
			//Fetch parsed data from parser
			/*List<Event> parseResult = mEventParser.parse();
			mEventAdapter.clear();
			mEventAdapter.addAll(parseResult);
			mEventAdapter.notifyDataSetChanged();*/
		}
	}
	
}
