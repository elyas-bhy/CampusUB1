package com.dev.campus.event;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;
import com.dev.campus.SettingsActivity;
import com.dev.campus.util.FilterDialog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EventsActivity extends ListActivity implements OnItemClickListener {


	public static final String EXTRA_CATEGORY = "com.dev.campus.CATEGORY";
	public static final String EXTRA_EVENT = "com.dev.campus.EVENT";

	private final int updateFrequency = 60000; // Update frequency (ms)
	private final int PICK_CATEGORY = 10;
	private Category mCategory;

	private ActionBar mActionBar;
	private FilterDialog mFilterDialog;
	private Resources mResources;

	private Handler mHandler;
	private EventAdapter mEventAdapter;
	private EventParser mEventParser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mResources = getResources();
		mFilterDialog = new FilterDialog(this);
		mHandler = new Handler();
		mCategory = Category.MAIN_EVENTS;

		try {
			mEventParser = new EventParser();
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

	public void reloadEvents(List<Event> events) {
		TextView headerView = (TextView) findViewById(R.id.event_list_header);
		headerView.setText(mCategory.toString());
		mEventAdapter.clear();
		mEventAdapter.addAll(events);
		mEventAdapter.notifyDataSetChanged();
	}

	@SuppressWarnings("unchecked")
	public void update() { 
		File file = new File(Environment.getExternalStorageDirectory() + "/history_" + mCategory.toString().replace(" ", "") + ".dat");
		SimpleEntry<List<Event>,List<Date>> feedsEntry = null;

		if (file.exists()) {
			try {
				FileInputStream fint = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fint);
				feedsEntry = (SimpleEntry<List<Event>,List<Date>>)ois.readObject();
				ois.close();
			} catch (Exception e) { 
				e.printStackTrace();
			}

			if (CampusUB1App.persistence.isOnline()) {
				//TODO
				//check if latest version, and start update task only if needed
				//otherwise, just load history
				new UpdateFeedsTask().execute(); //temporary
			} else {
				reloadEvents(feedsEntry.getKey());
				Toast.makeText(this, "Affichage de l'historique!", Toast.LENGTH_SHORT).show();
			}
		}

		else {
			if (CampusUB1App.persistence.isOnline()) {
				new UpdateFeedsTask().execute();
				Toast.makeText(this, "Mise à jour effectuée!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Echec de connection!", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
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

	private class UpdateFeedsTask extends AsyncTask<Category, Void, List<Event>> {

		private ProgressDialog progressDialog = new ProgressDialog(EventsActivity.this);

		@Override
		protected void onPreExecute() {
			progressDialog.setTitle(mResources.getString(R.string.events_loading));
			progressDialog.setMessage(mResources.getString(R.string.events_please_wait));
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected List<Event> doInBackground(Category... params) {
			try {
				mEventParser.parseEvents(mCategory);
				mEventParser.saveEvents();
				return mEventParser.getEvents();
			} catch (Exception e) {
				CampusUB1App.LogD(e.toString());
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<Event> events) {
			reloadEvents(events);
			progressDialog.dismiss();
		}

		@Override
		protected void onCancelled() {
			progressDialog.dismiss();
			super.onCancelled();
		}
	}
}