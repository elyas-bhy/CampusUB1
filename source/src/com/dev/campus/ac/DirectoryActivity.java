package com.dev.campus.ac;

import java.util.ArrayList;
import java.util.List;

import com.dev.campus.R;
import com.dev.campus.directory.Contact;
import com.dev.campus.directory.DirectoryAdapter;
import com.dev.campus.util.FilterDialog;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class DirectoryActivity extends Activity {

	private ActionBar mActionBar;
	private FilterDialog mFilterDialog;
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_directory);
		
		mFilterDialog = new FilterDialog(this);
        mActionBar = getActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

		// Testing directory view
		Contact c1 = new Contact("John", "Smith", "john.smith@email.com", "0623456789", "http://www.google.fr");
		Contact c2 = new Contact("Kyle", "Johnson", "kyle.johnson@email.com", "0623456789", "http://www.google.fr");
		List<Contact> contacts = new ArrayList<Contact>();
		contacts.add(c1);
		contacts.add(c2);
		
        listview = (ListView)findViewById(R.id.listview);
		DirectoryAdapter adapter = new DirectoryAdapter(this, contacts);
		listview.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.with_actionbar, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_settings:
				startActivity(new Intent(DirectoryActivity.this, SettingsActivity.class));
				return true;
			case R.id.menu_filters:
    			mFilterDialog.showDialog();
				return true;
			case android.R.id.home:
				finish();
				return true;
			default:
    			return super.onOptionsItemSelected(item);
		}
	}

}
