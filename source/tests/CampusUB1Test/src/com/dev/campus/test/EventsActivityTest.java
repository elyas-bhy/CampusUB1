package com.dev.campus.test;

import com.dev.campus.event.Event;
import com.dev.campus.event.EventAdapter;
import com.dev.campus.event.EventsActivity;
import com.dev.campus.event.Feed.FeedType;
import com.dev.campus.CampusUB1App;
import com.dev.campus.R;
import com.jayway.android.robotium.solo.Solo;

import android.database.DataSetObserver;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.util.Log;
import android.widget.ListView;

public class EventsActivityTest extends 
				ActivityInstrumentationTestCase2<EventsActivity> {

	private Solo mSolo;
	private EventsActivity mActivity;

	public EventsActivityTest() {
		super(EventsActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mActivity = getActivity();
		mSolo = new Solo(getInstrumentation(), mActivity);
		CampusUB1App.persistence.setFilterUB(true);
		CampusUB1App.persistence.setFilterLabri(true);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		//mSolo.finishOpenedActivities();
	}

	public void testInitialCategory() {
		ListView menuView = (ListView) mActivity.findViewById(R.id.slide_menu);
		assertEquals(0, menuView.getCheckedItemPosition());
	}

	@UiThreadTest
	public void testShowOnlyUB1Events() {
		final EventAdapter adapter = (EventAdapter) mActivity.getListView().getAdapter();
		adapter.registerDataSetObserver(new DataSetObserver() {
			
			@Override
			public void onChanged() {
				for(int i = 0; i < adapter.getCount(); i++) {
					Log.d("TestCase", "item: " + i);
					Event event = adapter.getItem(i);
					assertEquals(FeedType.UB1_FEED, event.getSource());
				}
			}
		});

		CampusUB1App.persistence.setFilterLabri(false);
		mActivity.update();
		
		try {
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*mSolo.clickOnView(mSolo.getView(R.id.menu_filters));
		mSolo.waitForDialogToOpen(2000);
		Log.d("TestCase", "before: " + CampusUB1App.persistence.isFilteredLabri());
		Log.d("TestCase", "found: " + mSolo.searchText(mSolo.getString(R.string.labri)));
		mSolo.clickOnText(mSolo.getString(R.string.labri));
		mSolo.clickOnButton(mSolo.getString(R.string.ok));
		mSolo.waitForDialogToClose(2000);
		Log.d("TestCase", "after: " + CampusUB1App.persistence.isFilteredLabri());*/
	}

}
