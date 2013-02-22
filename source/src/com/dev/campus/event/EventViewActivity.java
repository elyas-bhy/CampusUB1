package com.dev.campus.event;

import java.util.GregorianCalendar;

import com.dev.campus.R;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class EventViewActivity extends Activity {
	
	private ActionBar mActionBar;
	private Event mEvent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_view);

		mEvent = (Event) getIntent().getSerializableExtra(EventsActivity.EXTRA_EVENT);
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		
		ImageView icon = (ImageView) findViewById(R.id.event_view_icon);
		TextView title = (TextView) findViewById(R.id.event_view_title);
		TextView category= (TextView) findViewById(R.id.event_view_category);
		TextView date= (TextView) findViewById(R.id.event_view_date);
		TextView details = (TextView) findViewById(R.id.event_view_details);

		icon.setImageResource(R.drawable.ic_test);
		title.setText(mEvent.getTitle());
		category.setText(mEvent.getCategory());
		date.setText(mEvent.getDate());
		details.setText(Html.fromHtml(mEvent.getDetails()));
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
				addToCalendar();
				return true;
			default:
    			return super.onOptionsItemSelected(item);
		}
	}

	public void addToCalendar() {
		//Strip HTML tags and carriage returns from event details
		String details = Html.fromHtml(mEvent.getDetails()).toString().replace("\n", " ");
		Intent calIntent = new Intent(Intent.ACTION_INSERT);
		calIntent.setType("vnd.android.cursor.item/event");
		calIntent.putExtra(Events.TITLE, mEvent.getTitle());
		calIntent.putExtra(Events.DESCRIPTION, details);
		//TODO date and time of beginning
		GregorianCalendar calDate = new GregorianCalendar(2013, 2, 1, 18, 0);
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calDate.getTimeInMillis());
		calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calDate.getTimeInMillis());
		startActivity(calIntent);
	}
	
}
