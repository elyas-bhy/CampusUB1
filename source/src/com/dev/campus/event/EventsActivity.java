package com.dev.campus.event;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import org.xmlpull.v1.XmlPullParserException;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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

public class EventsActivity extends SlidingListActivity implements OnItemClickListener {

	public static final String EXTRA_EVENTS = "com.dev.campus.EVENTS";
	public static final String EXTRA_EVENTS_INDEX = "com.dev.campus.EVENTS_POSITION";
	
	private boolean mShowUpcomingEvents = false;
	private boolean mShowUnreadOnly = false;
	
	private ArrayList<Event> mEvents;
	private ArrayList<Event> mSortedEvents;
	private Category mCategory;
	
	private FilterDialog mFilterDialog;
	private EventAdapter mEventAdapter;
	private EventParser mEventParser;

	private ActionBar mActionBar;
	private Resources mResources;
	private MenuItem mRefreshMenuItem;
	private SlidingMenu mSlidingMenu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mResources = getResources();
		mFilterDialog = new FilterDialog(this);
		mCategory = Category.MAIN_EVENTS;

		try {
			mEventParser = new EventParser(this);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		
		setupActionBar();
		setupSlidingMenu();

		mEventAdapter = new EventAdapter(this, new ArrayList<Event>());
		ListView listView = getListView();
		listView.setOnItemClickListener(this);
		listView.setAdapter(mEventAdapter);
	}
	
	private void setupActionBar() {
		mActionBar = getActionBar();
		mActionBar.setSplitBackgroundDrawable(new ColorDrawable(mResources.getColor(R.color.holo_dark_black)));
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME
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
		mActionBar.setCustomView(customActionBarView);
	}
	
	private void setupSlidingMenu() {
		//Initialize SlidingMenu parameters
		mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        mSlidingMenu.setShadowDrawable(R.drawable.shadow);
        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        mSlidingMenu.setFadeDegree(0.35f);
        setSlidingActionBarEnabled(false);

        //Create and populate a Category adapter
		ArrayList<Category> categories = new ArrayList<Category>();
		categories.addAll(Arrays.asList(Category.values()));
        ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this, R.layout.simple_list_item, categories);
        
        //Assign adapter to slide_menu list view
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideMenu = inflater.inflate(R.layout.slide_menu, null);
        ListView menuView = (ListView) slideMenu.findViewById(R.id.slide_menu);
        menuView.setAdapter(adapter);
        
        menuView.setItemChecked(0, true);
        menuView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mCategory = (Category) parent.getItemAtPosition(position);
				update();
				mSlidingMenu.showContent();
			}
		});
        setBehindContentView(menuView);
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
		update();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			update();
			mSlidingMenu.showContent();
			return true;
			
		case android.R.id.home:
			if (mSlidingMenu.isMenuShowing())
				mSlidingMenu.showContent();
			else
				finish();
			return true;
			
		case R.id.checkbox_show_unread_only:
			if (item.isChecked()) {
				item.setIcon(R.drawable.ic_content_read);
				item.setChecked(false);
			} else {
				item.setIcon(R.drawable.ic_content_unread);
				item.setChecked(true);
			}
			mShowUnreadOnly = item.isChecked();
			//TODO update view
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
			reloadEvents();
			mSlidingMenu.showContent();
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void sortEvents() {
		ArrayList<Event> sortedEvents = new ArrayList<Event>();
		if (mEvents != null) {
			for (Event event : mEvents) {
				if (event.getSource().isFiltered()) {
					if (!mShowUpcomingEvents || (mShowUpcomingEvents && event.getDate().getTime() >= System.currentTimeMillis()))
						sortedEvents.add(event);
				}
			}
		}
		Collections.sort(sortedEvents, new Event.EventComparator());
		mSortedEvents = sortedEvents;
	}

	public void reloadEvents() {
		sortEvents();
		mEventAdapter.clear();
		mEventAdapter.addAll(mSortedEvents);
		mEventAdapter.notifyDataSetChanged();
	}
	
	public String getHistoryPath() {
		int sub1 = CampusUB1App.persistence.isSubscribedUB1()? 1 : 0;
		int sub2 = CampusUB1App.persistence.isSubscribedLabri()? 1 : 0;
		return getFilesDir() + "/history_" + mCategory.toString().replace(" ", "") + sub1 + sub2 + ".dat";
	}
	
	@SuppressWarnings("unchecked")
	private SimpleEntry<ArrayList<Event>, ArrayList<Date>> readEventsHistory() {
		File file = new File(getHistoryPath());
		SimpleEntry<ArrayList<Event>, ArrayList<Date>> feedsEntry = null;

		if (file.exists()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				feedsEntry = (SimpleEntry<ArrayList<Event>, ArrayList<Date>>) ois.readObject();
				ois.close();
			} catch (Exception e) { 
				e.printStackTrace();
			}
		}
		return feedsEntry;
	}

	@SuppressWarnings("unchecked")
	public void update() {
		SimpleEntry<ArrayList<Event>, ArrayList<Date>> feedsEntry = readEventsHistory();
		
		if (feedsEntry != null) {
			if (CampusUB1App.persistence.isOnline()) {
				new UpdateFeedsTask().execute(feedsEntry);
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
		Intent intent = new Intent(EventsActivity.this, EventViewActivity.class);
		intent.putExtra(EXTRA_EVENTS, mSortedEvents);
		intent.putExtra(EXTRA_EVENTS_INDEX, position);
		startActivity(intent);
	}
	

	private class UpdateFeedsTask extends AsyncTask<SimpleEntry<ArrayList<Event>, ArrayList<Date>>, Void, Void> {

		@Override
		protected void onPreExecute() {
			mRefreshMenuItem.setActionView(R.layout.actionar_refresh_progress);
		}

		@Override
		protected Void doInBackground(SimpleEntry<ArrayList<Event>, ArrayList<Date>>... entries) {
			try {
				//check if latest version, and update only if needed
				//otherwise, just load history
				if (entries.length > 0) {
					if (mEventParser.isLatestVersion(mCategory, entries[0].getValue())) {
						mEvents = entries[0].getKey();
						return null;
					}
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
			mRefreshMenuItem.setActionView(null);
		}

		@Override
		protected void onCancelled() {
			mRefreshMenuItem.setActionView(null);
			super.onCancelled();
		}
	}
	
}