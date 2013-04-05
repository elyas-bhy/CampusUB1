/*
 * Copyright (C) 2013 CampusUB1 Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dev.campus.event;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;
import com.dev.campus.SettingsActivity;
import com.dev.campus.util.FilterDialog;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingListActivity;

/**
 * Class responsible for the events activity UI and lifecycle,
 * handles saving, loading and updating events feeds.
 * 
 * @author CampusUB1 Development Team
 *
 */
public class EventsActivity extends SlidingListActivity implements OnItemClickListener {

	// Extra keys
	public static final String EXTRA_EVENTS = "com.dev.campus.EVENTS";
	public static final String EXTRA_EVENTS_INDEX = "com.dev.campus.EVENTS_POSITION";
	public static final String EXTRA_EVENTS_RESULT = "com.dev.campus.EXTRA_EVENTS_RESULT";

	// Footer action bar filters
	private boolean mShowUpcomingEvents = false;
	private boolean mShowStarredEvents = false;
	private boolean mShowUnreadOnly = false;

	// Current category and related data
	private Category mCategory;
	private ArrayList<Event> mEvents;
	private ArrayList<Event> mSortedEvents;
	private ArrayList<Date> mBuildDates;

	// Utilities
	private EventAdapter mEventAdapter;
	private EventParser mEventParser;
	private FilterDialog mFilterDialog;
	private MenuItem mRefreshMenuItem;
	private SlidingMenu mSlidingMenu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFilterDialog = new FilterDialog(this);
		mEventParser = new EventParser();
		mCategory = Category.MAIN_EVENTS; // Initial category

		setupActionBar();
		setupSlidingMenu();
		setupListAdapter();
	}

	/**
	 * Initializes the top custom ActionBar
	 */
	private void setupActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setSplitBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.holo_dark_black)));
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME
								   | ActionBar.DISPLAY_SHOW_CUSTOM);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View customActionBarView = inflater.inflate(R.layout.custom_actionbar, null);
		customActionBarView.findViewById(R.id.menu_settings).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(EventsActivity.this, SettingsActivity.class));
			}
		});
		customActionBarView.findViewById(R.id.menu_filters).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mFilterDialog.showDialog();
			}
		});
		actionBar.setCustomView(customActionBarView);
	}

	/**
	 * Initializes the category sliding menu.
	 */
	private void setupSlidingMenu() {
		// Initialize parameters
		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		mSlidingMenu.setShadowDrawable(R.drawable.shadow);
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		mSlidingMenu.setFadeDegree(0.35f);
		setSlidingActionBarEnabled(false);

		// Create and populate a Category adapter
		ArrayList<Category> categories = new ArrayList<Category>();
		categories.addAll(Arrays.asList(Category.values()));
		ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this, R.layout.slidemenu_list_item, categories);

		// Assign adapter to slide_menu list view
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View slideMenu = inflater.inflate(R.layout.slide_menu, null);
		ListView menuView = (ListView) slideMenu.findViewById(R.id.slide_menu);
		menuView.setAdapter(adapter);

		menuView.setItemChecked(0, true);
		menuView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				saveEvents();
				mCategory = (Category) parent.getItemAtPosition(position);
				clearContent();
				update();
				mSlidingMenu.showContent();
			}
		});

		setBehindContentView(menuView);
	}
	
	/**
	 * Initializes the list adapter in charge of displaying
	 * and populating event items.
	 */
	private void setupListAdapter() {
		mEventAdapter = new EventAdapter(this, new ArrayList<Event>());
		ListView listView = getListView();
		listView.setOnItemClickListener(this);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		listView.setMultiChoiceModeListener(new EventMultiChoiceModeListener(listView));
		listView.setAdapter(mEventAdapter);
	}

	@Override
	public void onPause() {
		saveEvents();
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		if (mSlidingMenu.isMenuShowing())
			mSlidingMenu.showContent();
		else
			super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar_footer, menu);
		mRefreshMenuItem = menu.findItem(R.id.menu_refresh);
		mSlidingMenu.showMenu();
		/* Delayed call to update method until getting a reference
		to mRefreshMenuItem */
		update();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			saveEvents();
			update();
			mSlidingMenu.showContent();
			return true;

		case android.R.id.home:
			if (mSlidingMenu.isMenuShowing())
				mSlidingMenu.showContent();
			else
				finish();
			return true;
			
		case R.id.checkbox_show_starred_events:
			if (item.isChecked()) {
				item.setIcon(R.drawable.ic_half_star);
				item.setChecked(false);
				Toast.makeText(this, R.string.showing_all_events, Toast.LENGTH_SHORT).show();
			} else {
				item.setIcon(R.drawable.ic_star_highlight);
				item.setChecked(true);
				Toast.makeText(this, R.string.showing_starred_events, Toast.LENGTH_SHORT).show();
			}
			mShowStarredEvents = item.isChecked();
			reloadContent();
			mSlidingMenu.showContent();
			return true;

		case R.id.checkbox_show_unread_only:
			if (item.isChecked()) {
				item.setIcon(R.drawable.ic_content_read);
				item.setChecked(false);
				Toast.makeText(this, R.string.showing_all_events, Toast.LENGTH_SHORT).show();
			} else {
				item.setIcon(R.drawable.ic_content_unread_highlight);
				item.setChecked(true);
				Toast.makeText(this, R.string.showing_unread_events, Toast.LENGTH_SHORT).show();
			}
			mShowUnreadOnly = item.isChecked();
			reloadContent();
			mSlidingMenu.showContent();
			return true;

		case R.id.checkbox_show_upcoming_events:
			if (item.isChecked()) {
				item.setIcon(R.drawable.btn_check_off_holo_light);
				item.setChecked(false);
				Toast.makeText(this, R.string.showing_all_events, Toast.LENGTH_SHORT).show();
			} else {
				item.setIcon(R.drawable.btn_check_on_holo_light);
				item.setChecked(true);
				Toast.makeText(this, R.string.showing_upcoming_events, Toast.LENGTH_SHORT).show();
			}
			mShowUpcomingEvents = item.isChecked();
			reloadContent();
			mSlidingMenu.showContent();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Sorts the list of displayed events based on current
	 * activated filters
	 */
	private void sortEvents() {
		ArrayList<Event> sortedEvents = new ArrayList<Event>();
		if (mEvents != null) {
			for (Event event : mEvents) {
				if (event.getSource().isFiltered() &&
				   (!mShowUpcomingEvents || (mShowUpcomingEvents && event.getStartDate().getTime() >= System.currentTimeMillis())) &&
				   (!mShowUnreadOnly 	|| (mShowUnreadOnly && !event.isRead())) &&
				   (!mShowStarredEvents || (mShowStarredEvents && event.isStarred()))) {
					sortedEvents.add(event);
				}
			}
		}
		Collections.sort(sortedEvents, new Event.EventComparator());
		mSortedEvents = sortedEvents;
	}

	/**
	 * Clears all items from the adapter view
	 */
	public void clearContent() {
		mEventAdapter.clear();
		mEventAdapter.notifyDataSetChanged();
	}
	
	/**
	 * Updates view of current items
	 */
	public void reloadContent() {
		sortEvents();
		mEventAdapter.clear();
		mEventAdapter.addAll(mSortedEvents);
		mEventAdapter.notifyDataSetChanged();
	}

	/**
	 * Returns a path to a history file based on current subscriptions
	 */
	private String getHistoryPath() {
		int sub1 = CampusUB1App.persistence.isSubscribedUB1()? 1 : 0;
		int sub2 = CampusUB1App.persistence.isSubscribedLabri()? 1 : 0;
		return getFilesDir() + "/history_" + mCategory.toString().replace(" ", "") + sub1 + sub2 + ".dat";
	}
	
	/**
	 * Handles the update procedure based on current connectivity state
	 */
	@SuppressWarnings("unchecked")
	public void update() {
		SimpleEntry<ArrayList<Date>, ArrayList<Event>> feedsEntry = readEventsHistory();

		if (feedsEntry != null) {
			if (CampusUB1App.persistence.isConnected()) {
				new UpdateFeedsTask().execute(feedsEntry);
			} else {
				mBuildDates = feedsEntry.getKey();
				mEvents = feedsEntry.getValue();
				reloadContent();
				Toast.makeText(this, R.string.showing_history, Toast.LENGTH_SHORT).show();
			}
		}
		else {
			if (CampusUB1App.persistence.isConnected()) {
				new UpdateFeedsTask().execute();
				Toast.makeText(this, R.string.updating_events, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, R.string.connection_failed, Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(EventsActivity.this, EventViewActivity.class);
		intent.putExtra(EXTRA_EVENTS, mSortedEvents);
		intent.putExtra(EXTRA_EVENTS_INDEX, position);
		startActivityForResult(intent, 1);
	}

	/**
	 * Toggles the state of an event's star
	 * @param view reference to the clicked view
	 */
	public void toggleStar(View view) {
		ListView listView = getListView();
		int position = listView.getPositionForView(view);
		Event event = (Event) listView.getItemAtPosition(position);
		event.setStarred(!event.isStarred());
		mEventAdapter.notifyDataSetChanged();
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {

			if (resultCode == RESULT_OK) {      
				ArrayList<Event> result = (ArrayList<Event>) data.getSerializableExtra(EXTRA_EVENTS_RESULT);
				for (Event event : result) {
					if (event.isRead()) {
						if (mEvents.contains(event))
							mEvents.get(mEvents.indexOf(event)).setRead(true);
					}
				}
				mEventAdapter.notifyDataSetChanged();
			}
		}
	}
	
	/**
	 * Worker thread responsible for fetching updates of specified category
	 * and displaying them
	 * @author CampusUB1 Development Team
	 *
	 */
	private class UpdateFeedsTask extends AsyncTask<SimpleEntry<ArrayList<Date>, ArrayList<Event>>, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			mRefreshMenuItem.setActionView(R.layout.progressbar_refresh);
		}

		@Override
		protected Boolean doInBackground(SimpleEntry<ArrayList<Date>, ArrayList<Event>>... entries) {
			try {
				//check if latest version, and update only if needed
				//otherwise, just load history
				ArrayList<Event> existingEvents = new ArrayList<Event>(); // so far, no existing events

				if (entries.length > 0 && entries[0].getKey() != null && entries[0].getValue() != null) {
					if (entries[0].getKey().size() > 0 && entries[0].getValue().size() > 0) {
						existingEvents = entries[0].getValue();
						mEvents = existingEvents;
						mBuildDates = entries[0].getKey();
						// Display existing events and continue updating task
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								reloadContent();
							}
						});
						if (mEventParser.isLatestVersion(mCategory, mBuildDates)) {
							return false;
						}
					}
				}
				mEventParser.parseEvents(mCategory, existingEvents);
				mEvents = mEventParser.getParsedEvents();
				mBuildDates = mEventParser.getParsedBuildDates();
			} catch (Exception e) {
				e.printStackTrace();
				cancel(true);
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean needsReload) {
			if (needsReload)
				reloadContent();
			mRefreshMenuItem.setActionView(null);
		}

		@Override
		protected void onCancelled() {
			clearContent();
			mEvents.clear();
			mBuildDates.clear();
			mRefreshMenuItem.setActionView(null);
		}
	}

	/**
	 * Reads list of events from device memory
	 * @return saved data if found, else returns null
	 */
	@SuppressWarnings("unchecked")
	private SimpleEntry<ArrayList<Date>, ArrayList<Event>> readEventsHistory() {
		File file = new File(getHistoryPath());
		SimpleEntry<ArrayList<Date>, ArrayList<Event>> feedsEntry = null;

		if (file.exists()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				feedsEntry = (SimpleEntry<ArrayList<Date>, ArrayList<Event>>) ois.readObject();
				ois.close();
			} catch (Exception e) { 
				e.printStackTrace();
			}
		}
		return feedsEntry;
	}

	/**
	 * Writes list of events to device memory
	 */
	public void saveEvents() {
		ObjectOutputStream oos = null;
		try {
			File history = new File(getHistoryPath());
			history.getParentFile().createNewFile();
			FileOutputStream fout = new FileOutputStream(history);
			oos = new ObjectOutputStream(fout);
			SimpleEntry<ArrayList<Date>, ArrayList<Event>> map = new SimpleEntry<ArrayList<Date>, ArrayList<Event>>(mBuildDates, mEvents);
			oos.writeObject(map);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();  
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.flush();
					oos.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}