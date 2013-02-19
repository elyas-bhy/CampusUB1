package com.dev.campus.event;

import com.dev.campus.R;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class EventViewActivity extends Activity {
	
	private ActionBar mActionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_view);
		
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		
		ImageView icon = (ImageView) findViewById(R.id.event_view_icon);
		TextView title = (TextView) findViewById(R.id.event_view_title);
		TextView category= (TextView) findViewById(R.id.event_view_category);
		TextView details = (TextView) findViewById(R.id.event_view_details);
		
		Event event = (Event) getIntent().getSerializableExtra(EventsActivity.EXTRA_EVENT);

		icon.setImageResource(R.drawable.ic_test);
		title.setText(event.getTitle());
		category.setText(event.getCategory());
		details.setText(Html.fromHtml(event.getDetails()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.without_actionbar, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			default:
    			return super.onOptionsItemSelected(item);
		}
	}

}
