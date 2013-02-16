package com.dev.campus.ac;

import java.util.ArrayList;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;
import com.dev.campus.event.CategoriesActivity;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EventsActivity extends ListActivity implements OnItemClickListener {

	private final int updateFrequency = 15000; // Update frequency (ms)
	private final int PICK_CATEGORY = 10;
	
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
		
		ArrayList<Event> events = new ArrayList<Event>();
		events.add(new Event(R.drawable.ic_test, "Event1", "News", "The brown fox jumps over the lazy dog"));
		events.add(new Event(R.drawable.ic_test, "Event2", "News", "The brown fox jumps over the lazy dog"));
		events.add(new Event(R.drawable.ic_test, "Event3", "News", "The brown fox jumps over the lazy dog"));
		events.add(new Event(R.drawable.ic_test, "Event4", "News", "The brown fox jumps over the lazy dog"));
		events.add(new Event(R.drawable.ic_test, "Event5", "News", "The brown fox jumps over the lazy dog"));
		events.add(new Event(R.drawable.ic_test, "Event6", "News", "The brown fox jumps over the lazy dog"));
		events.add(new Event(R.drawable.ic_test, "Event7", "News", "The brown fox jumps over the lazy dog"));
		events.add(new Event(R.drawable.ic_test, "Event8", "News", "The brown fox jumps over the lazy dog"));
		events.add(new Event(R.drawable.ic_test, "Event9", "News", "The brown fox jumps over the lazy dog"));
		
		ListView listView = getListView();
		View header = (View)getLayoutInflater().inflate(R.layout.event_list_header, listView, false);
		listView.addHeaderView(header, null, true);
		listView.setOnItemClickListener(this);
        
        //Fetch data from parser
        mEventAdapter = new EventAdapter(this, events);
		listView.setAdapter(mEventAdapter);
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
			//mEventAdapter.add(new Event("Event " + System.currentTimeMillis()));	//For test purposes
			
			//Fetch parsed data from parser
			/*List<Event> parseResult = mEventParser.parse();
			mEventAdapter.clear();
			mEventAdapter.addAll(parseResult);
			mEventAdapter.notifyDataSetChanged();*/
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg2 == 0) {	//Categories index
			startActivityForResult(new Intent(EventsActivity.this, CategoriesActivity.class), PICK_CATEGORY);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PICK_CATEGORY) {
			if (resultCode == RESULT_OK) {
				String category = data.getStringExtra(CategoriesActivity.EXTRA_CATEGORY);
				if (category != null) {
					TextView headerView = (TextView) findViewById(R.id.event_list_header);
					headerView.setText(category);
				}
			}
		}
	}
	
}
