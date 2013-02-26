package com.dev.campus.directory;

import java.io.IOException;
import java.util.ArrayList;

import com.dev.campus.R;
import com.dev.campus.ac.SettingsActivity;
import com.dev.campus.util.FilterDialog;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DirectoryActivity extends Activity {

	private ActionBar mActionBar;
	private FilterDialog mFilterDialog;
	private ListView listview;

	final String EXTRA_FIRSTNAME = "First Name";
	final String EXTRA_LASTNAME = "Last Name";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_directory);

		mFilterDialog = new FilterDialog(this);
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);

		final TextView firstName = (TextView) findViewById(R.id.editTextFirstName);
		final TextView lastName = (TextView) findViewById(R.id.editTextLastName);
		ArrayList<Contact> matchingContacts = new ArrayList<Contact>(); 
		Intent intent = getIntent();
		if (intent != null) {
			firstName.setText(intent.getStringExtra(EXTRA_FIRSTNAME));
			lastName.setText(intent.getStringExtra(EXTRA_LASTNAME));

			// ----------
			int searchMinChar = 3; 
			if( firstName.getText().toString().length() > searchMinChar || lastName.getText().toString().length() > searchMinChar ) {
				ArrayList<Contact> contacts = new ArrayList<Contact>();
				try {
					contacts = new Directory().labriDirectoryParser();
				} catch (IOException e) {
					e.printStackTrace();
				}
				matchingContacts = new Directory().directoryFilter(contacts, firstName.getText().toString(), lastName.getText().toString());	
			}
			// ----------
		}

		final Button searchButton = (Button) findViewById(R.id.buttonSearchDirectory);
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DirectoryActivity.this, DirectoryActivity.class);
				intent.putExtra(EXTRA_FIRSTNAME, firstName.getText().toString());
				intent.putExtra(EXTRA_LASTNAME, lastName.getText().toString());
				finish();
				startActivity(intent);
			}
		});

		/* Testing directory view
		Contact c1 = new Contact("John", "Smith", "john.smith@email.com", "0623456789", "http://www.google.fr");
		Contact c2 = new Contact("Kyle", "Johnson", "kyle.johnson@email.com", "0623456789", "http://www.google.fr");
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		contacts.add(c1);
		contacts.add(c2);
		//*/

		listview = (ListView)findViewById(R.id.listview);
		DirectoryAdapter adapter = new DirectoryAdapter(this, matchingContacts);
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
