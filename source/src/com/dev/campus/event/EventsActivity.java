package com.dev.campus.event;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;
import com.dev.campus.SettingsActivity;
import com.dev.campus.event.Feed.FeedType;
import com.dev.campus.util.FilterDialog;

public class EventsActivity extends ListActivity implements OnItemClickListener {

	public static final String EXTRA_CATEGORY = "com.dev.campus.CATEGORY";
	public static final String EXTRA_EVENT = "com.dev.campus.EVENT";
	
	private final int PICK_CATEGORY = 10;
	private final int updateFrequency = 60000; // Update frequency (ms)
	
	private List<Event> mEvents;
	private Category mCategory;

	private ActionBar mActionBar;
	private Resources mResources;
	private Handler mHandler;
	
	private FilterDialog mFilterDialog;
	private EventAdapter mEventAdapter;
	private EventParser mEventParser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mResources = getResources();

		mHandler = new Handler();
		mFilterDialog = new FilterDialog(this);
		mCategory = Category.MAIN_EVENTS;

		try {
			mEventParser = new EventParser(this);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

		ListView listView = getListView();
		View header = (View)getLayoutInflater().inflate(R.layout.event_list_header, listView, false);
		listView.addHeaderView(header, null, true);
		listView.setOnItemClickListener(this);

		mEventAdapter = new EventAdapter(this, new ArrayList<Event>());
		listView.setAdapter(mEventAdapter);
		startUpdateTimer();
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

	private Runnable mUpdateRunnable = new Runnable() {
		@Override 
		public void run() {
			if (CampusUB1App.persistence.isWifiConnected())
				update();
			mHandler.postDelayed(mUpdateRunnable, updateFrequency);
		}
	};

	private void startUpdateTimer() {
		mUpdateRunnable.run();
	}

	private void pauseUpdateTimer() {
		mHandler.removeCallbacks(mUpdateRunnable);
	}

	public void reloadEvents() {
		ArrayList<Event> sortedEvents = new ArrayList<Event>();
		TextView headerView = (TextView) findViewById(R.id.event_list_header);
		headerView.setText(mCategory.toString());
		mEventAdapter.clear();

		if (mEvents != null) {
			for (Event event : mEvents) {
				if ((event.getSource().equals(FeedType.UB1_FEED) && CampusUB1App.persistence.isFilteredUB1())
				 || (event.getSource().equals(FeedType.LABRI_FEED) && CampusUB1App.persistence.isFilteredLabri()))
					sortedEvents.add(event);
			}
		}
		Collections.sort(sortedEvents, new Comparator<Event>(){
			@Override
			public int compare(Event evt1, Event evt2) {
				return evt2.getDate().compareTo(evt1.getDate());
			}
		});
		
		mEventAdapter.addAll(sortedEvents);
		mEventAdapter.notifyDataSetChanged();
	}
	
	public String getHistoryPath() {
		return getFilesDir() + "/history_" + mCategory.toString().replace(" ", "") + ".dat";
	}
	
	@SuppressWarnings("unchecked")
	private SimpleEntry<List<Event>, List<Date>> readEventsHistory() {
		File file = new File(getHistoryPath());
		SimpleEntry<List<Event>,List<Date>> feedsEntry = null;

		if (file.exists()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				feedsEntry = (SimpleEntry<List<Event>,List<Date>>) ois.readObject();
				ois.close();
			} catch (Exception e) { 
				e.printStackTrace();
			}
		}
		return feedsEntry;
	}

	@SuppressWarnings("unchecked")
	public void update() {
		SimpleEntry<List<Event>,List<Date>> feedsEntry = readEventsHistory();
		
		if (feedsEntry != null) {
			if (CampusUB1App.persistence.isOnline()) {
				new UpdateFeedsTask().execute(feedsEntry.getValue());
			} else {
				mEvents = feedsEntry.getKey();
				reloadEvents();
				Toast.makeText(this, mResources.getString(R.string.showing_history), Toast.LENGTH_SHORT).show();
			}
		}
		else {
			if (CampusUB1App.persistence.isOnline()) {
				new UpdateFeedsTask().execute();
				Toast.makeText(this, mResources.getString(R.string.update_complete), Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, mResources.getString(R.string.connection_failed), Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (position == 0) {
			//Categories index
			startActivityForResult(new Intent(EventsActivity.this, CategoryActivity.class), PICK_CATEGORY);
		} else {
			Event item = (Event) getListView().getAdapter().getItem(position);
			Intent intent = new Intent(EventsActivity.this, EventViewActivity.class);
			intent.putExtra(EXTRA_EVENT, item);
			startActivity(intent);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PICK_CATEGORY) {
			if (resultCode == RESULT_OK) {
				Category category = (Category) data.getSerializableExtra(EXTRA_CATEGORY);
				if (category != null) {
					mCategory = category;
					update();
				}
			}
		}
	}

	private class UpdateFeedsTask extends AsyncTask<List<Date>, Void, Void> {

		private ProgressDialog progressDialog = new ProgressDialog(EventsActivity.this);

		@Override
		protected void onPreExecute() {
			progressDialog.setTitle(mResources.getString(R.string.events_loading));
			progressDialog.setMessage(mResources.getString(R.string.please_wait));
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(List<Date>... dates) {
			try {
				//check if latest version, and update only if needed
				//otherwise, just load history
				if (dates.length > 0) {
					if (mEventParser.isLatestVersion(mCategory, dates[0]))
						return null;
				}
				mEventParser.parseEvents(mCategory);
				mEventParser.saveEvents();
				mEvents = mEventParser.getEvents();
			} catch (Exception e) {
				CampusUB1App.LogD(e.toString());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			reloadEvents();
			progressDialog.dismiss();
		}

		@Override
		protected void onCancelled() {
			progressDialog.dismiss();
			super.onCancelled();
		}
	}
	
}