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

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.campus.R;

/**
 * Activity responsible for displaying detailed views of events
 * @author CampusUB1 Development Team
 *
 */
public class EventViewActivity extends FragmentActivity {
	
	private ActionBar mActionBar;
	private ArrayList<Event> mEvents;
	private EventPagerAdapter mEventPagerAdapter;
	private ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_view);
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);

		int currentIndex = getIntent().getIntExtra(EventsActivity.EXTRA_EVENTS_INDEX, 0);
		mEvents = (ArrayList<Event>) getIntent().getSerializableExtra(EventsActivity.EXTRA_EVENTS);
		mEventPagerAdapter = new EventPagerAdapter(getSupportFragmentManager(), mEvents);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mEventPagerAdapter);
		mViewPager.setCurrentItem(currentIndex);
		setEventRead(currentIndex);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				setEventRead(position);
			}
			
			@Override
			public void onPageScrolled(int position, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	
	public void setEventRead(int position) {
		mEvents.get(position).setRead(true);
	}
	
	@Override
	public void finish() {
		Intent intent = new Intent();
		intent.putExtra(EventsActivity.EXTRA_EVENTS_RESULT, mEvents);
		setResult(RESULT_OK, intent);
		super.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.calendar_actionbar, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			case R.id.menu_calendar:
				addToCalendar(mEvents.get(mViewPager.getCurrentItem()));
				return true;
			default:
    			return super.onOptionsItemSelected(item);
		}
	}

	public void addToCalendar(Event event) {
		//Strip HTML tags and carriage returns from event details
		String details = Html.fromHtml(event.getDetails()).toString().replace("\n", " ");
		Intent calIntent = new Intent(Intent.ACTION_EDIT);
		calIntent.setType("vnd.android.cursor.item/event");
		calIntent.putExtra(Events.TITLE, event.getTitle());
		calIntent.putExtra(Events.DESCRIPTION, details);
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, event.getStartDate().getTime());
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, event.getEndDate().getTime());
		startActivity(calIntent);
	}
	
}
