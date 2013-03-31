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

package com.dev.campus.test;

import com.dev.campus.event.Event;
import com.dev.campus.event.EventAdapter;
import com.dev.campus.event.EventsActivity;
import com.dev.campus.event.Feed.FeedType;
import com.dev.campus.CampusUB1App;
import com.dev.campus.R;
import com.jayway.android.robotium.solo.Condition;
import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

public class EventsActivityTest extends 
ActivityInstrumentationTestCase2<EventsActivity> {

	private Solo mSolo;
	private EventsActivity mActivity;
	private EventAdapter mEventAdapter;

	public EventsActivityTest() {
		super(EventsActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		mActivity = getActivity();
		mEventAdapter = (EventAdapter) mActivity.getListView().getAdapter();
		mSolo = new Solo(getInstrumentation(), mActivity);
		CampusUB1App.persistence.setFilterUB(true);
		CampusUB1App.persistence.setFilterLabri(true);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testInitialCategory() {
		ListView menuView = (ListView) mActivity.findViewById(R.id.slide_menu);
		assertEquals(0, menuView.getCheckedItemPosition());
	}

	public void testShowOnlyUB1Events() {
		// Emulate toggling off all filters except UB1
		mSolo.clickOnView(mSolo.getView(R.id.menu_filters));
		mSolo.waitForDialogToOpen(1000);
		mSolo.clickOnText(mSolo.getString(R.string.labri));
		mSolo.clickOnButton(mSolo.getString(R.string.ok));
		mSolo.waitForDialogToClose(1000);
		mSolo.waitForCondition(new Condition() {

			@Override
			public boolean isSatisfied() {
				return mEventAdapter.getCount() > 0;
			}
		}, 10000);

		for (int i = 0; i < mEventAdapter.getCount(); i++) {
			Event event = mEventAdapter.getItem(i);
			assertEquals(FeedType.UB1_FEED, event.getSource());
		}
	}

	public void testMarkEventAsRead() {
		mSolo.waitForCondition(new Condition() {

			@Override
			public boolean isSatisfied() {
				return mEventAdapter.getCount() > 0;
			}
		}, 10000);
		mSolo.clickInList(1);
		mSolo.waitForActivity("EventViewActivity");
		mSolo.goBack();
		mSolo.waitForActivity("EventsActivity");

		// Need to trigger saveEvents() first in UI thread
		//assertTrue(mEventAdapter.getItem(0).isRead());
	}

}
