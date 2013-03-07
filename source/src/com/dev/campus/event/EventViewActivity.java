package com.dev.campus.event;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.dev.campus.R;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.with_actionbar_calendar, menu);
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
		Intent calIntent = new Intent(Intent.ACTION_INSERT);
		calIntent.setType("vnd.android.cursor.item/event");
		calIntent.putExtra(Events.TITLE, event.getTitle());
		calIntent.putExtra(Events.DESCRIPTION, details);
		//TODO date and time of beginning
		GregorianCalendar calDate = new GregorianCalendar(2013, 2, 1, 18, 0);
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calDate.getTimeInMillis());
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calDate.getTimeInMillis());
		startActivity(calIntent);
	}
	
}
