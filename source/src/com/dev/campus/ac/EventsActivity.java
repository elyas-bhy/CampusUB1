package com.dev.campus.ac;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;
import com.dev.campus.event.CategoriesActivity;
import com.dev.campus.event.Event;
import com.dev.campus.event.EventAdapter;
import com.dev.campus.event.EventParser;
import com.dev.campus.util.FilterDialog;

import android.os.AsyncTask;
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
	
	public static final String UB1_ALL_NEWS = "http://www.u-bordeaux1.fr/index.php?type=114";
	
	private ActionBar mActionBar;
	private FilterDialog mFilterDialog;
	
	private Handler mHandler;
	private EventAdapter mEventAdapter;
	private EventParser mEventParser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mFilterDialog = new FilterDialog(this);
		mHandler = new Handler();
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);

		try {
			mEventParser = new EventParser();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ListView listView = getListView();
		View header = (View)getLayoutInflater().inflate(R.layout.event_list_header, listView, false);
		listView.addHeaderView(header, null, true);
		listView.setOnItemClickListener(this);
        
        mEventAdapter = new EventAdapter(this, new ArrayList<Event>());
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
			//If history exists, load it for offline use
		}
		else {
			Toast.makeText(this, "Mise à jour effectuée!", Toast.LENGTH_SHORT).show();
			//Fetch a fresh version of feed
			new UpdateFeedsTask().execute(UB1_ALL_NEWS);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		if (position == 0) {	//Categories index
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
	
	private class UpdateFeedsTask extends AsyncTask<String, Void, List<Event>> {

		@Override
		protected List<Event> doInBackground(String... params) {
			//if (currentVersion < newVersion)
			try {
				mEventParser.setInput(params[0]);
				return mEventParser.getEvents();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			//else retrieve history
		}
		
		@Override
		protected void onPostExecute(List<Event> events) {
			mEventAdapter.clear();
			mEventAdapter.addAll(events);
			mEventAdapter.notifyDataSetChanged();
		}
	 }
	
}
