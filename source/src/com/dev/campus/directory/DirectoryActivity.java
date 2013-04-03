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

package com.dev.campus.directory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;
import com.dev.campus.SettingsActivity;
import com.dev.campus.directory.Contact.ContactType;
import com.dev.campus.util.FilterDialog;
import com.unboundid.ldap.sdk.LDAPException;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;


public class DirectoryActivity extends ListActivity implements OnItemClickListener {

	private Resources mResources;
	private ProgressBar mProgressBar;
	private FilterDialog mFilterDialog;

	private Contact mContact;
	private List<Contact> mSearchResult;
	private List<Contact> mSortedContacts;
	private DirectoryAdapter mDirectoryAdapter;
	private DirectoryManager mDirectoryManager;
	
	private EditText mViewFirstName;
	private EditText mViewLastName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		mFilterDialog = new FilterDialog(this);
		mResources = getResources();
		
		mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
		mProgressBar.setVisibility(View.GONE);
		mProgressBar.setIndeterminate(true);
		
		mDirectoryManager = new DirectoryManager();
		mDirectoryAdapter = new DirectoryAdapter(this, new ArrayList<Contact>());

		ListView listview = getListView();
		View header = (View) getLayoutInflater().inflate(R.layout.directory_list_header, listview, false);
		listview.addHeaderView(header, null, false);
		listview.setAdapter(mDirectoryAdapter);
		listview.setOnItemClickListener(this);

		initSearchButton();
	}

	private void initSearchButton() {
		OnEditorActionListener onSearchAction = new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					startSearchTask();
					return true;
				}
				return false;
			}
		};

		mViewFirstName = (EditText) findViewById(R.id.edit_text_first_name);
		mViewLastName = (EditText) findViewById(R.id.edit_text_last_name);
		mViewFirstName.setOnEditorActionListener(onSearchAction);
		mViewLastName.setOnEditorActionListener(onSearchAction);

		ImageButton searchButton = (ImageButton) findViewById(R.id.button_search_directory);
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startSearchTask();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.filters_actionbar, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mContact = (Contact) parent.getItemAtPosition(position);
		registerForContextMenu(view);
		view.setLongClickable(false);
		openContextMenu(view);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.directory_contextual, menu);
		if (!mContact.hasTel())
			menu.getItem(0).setEnabled(false);
		if (!mContact.hasEmail())
			menu.getItem(1).setEnabled(false);
		if (!mContact.hasWebsite())
			menu.getItem(3).setEnabled(false);
	}

	public void startSearchTask() {
		if (mViewFirstName.getText().toString().length() + mViewLastName.getText().length() > 0) {
			new SearchResultTask().execute();
			//Hide soft keyboard
			if (getCurrentFocus() != null) {
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
			}
		}
	}

	public void callContact() {
		Intent callIntent = new Intent(Intent.ACTION_DIAL);
		callIntent.setData(Uri.parse("tel:" + mContact.getTel()));
		startActivity(callIntent);
	}

	public void emailContact() {
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setType("plain/text");  
		emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {mContact.getEmail()});
		startActivity(Intent.createChooser(emailIntent, mResources.getString(R.string.menu_complete_action)));
	}

	public void addToContacts() {
		String contactFullName =  mContact.getFirstName() + " " + mContact.getLastName();
		Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT, ContactsContract.Contacts.CONTENT_URI);
		intent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
		intent.putExtra(ContactsContract.Intents.Insert.NAME, contactFullName);	
		if (mContact.hasTel())
			intent.putExtra(ContactsContract.Intents.Insert.PHONE, mContact.getTel());	
		if (mContact.hasEmail())
			intent.putExtra(ContactsContract.Intents.Insert.EMAIL, mContact.getEmail());
		startActivity(intent);
	}

	public void visitContactWebsite() {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mContact.getWebsite()));
		startActivity(browserIntent);
	}
	
	public void sortContacts() {
		ArrayList<Contact> sortedContacts = new ArrayList<Contact>();
		if (mSearchResult != null) {
			for (Contact contact: mSearchResult) {
				if ((contact.getType().equals(ContactType.UB1_CONTACT) && CampusUB1App.persistence.isFilteredUB1())
				 || (contact.getType().equals(ContactType.LABRI_CONTACT) && CampusUB1App.persistence.isFilteredLabri()))
					sortedContacts.add(contact);
			}
		}
		Collections.sort(sortedContacts, new Contact.ContactComparator());
		mSortedContacts = sortedContacts;
	}
	
	public void clearContent() {
		mDirectoryAdapter.clear();
		mDirectoryAdapter.notifyDataSetChanged();
	}

	public void reloadContent() {
		sortContacts();
		mDirectoryAdapter.clear();
		mDirectoryAdapter.addAll(mSortedContacts);
		mDirectoryAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_call:
			callContact();
			return true;
		case R.id.menu_mail:
			emailContact();
			return true;
		case R.id.menu_add_to_contacts:	
			addToContacts();
			return true;
		case R.id.menu_website:	  
			visitContactWebsite();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
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


	private class SearchResultTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			clearContent();
			mProgressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			final String firstName = mViewFirstName.getText().toString();
			final String lastName = mViewLastName.getText().toString();
			List<Contact> searchResult = new ArrayList<Contact>();

			try {
				searchResult = mDirectoryManager.searchContact(firstName, lastName);
			} catch (LDAPException e) {
				e.printStackTrace();
				cancel(true);
			} catch (IOException e) {
				e.printStackTrace();
				cancel(true);
			}
			mSearchResult = searchResult;
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			if(mSearchResult.size() < 1)
				Toast.makeText(getBaseContext(), R.string.no_matching_contacts, Toast.LENGTH_SHORT).show();
			else
				reloadContent();
			mProgressBar.setVisibility(View.GONE);
		}

		@Override
		protected void onCancelled() {
			mProgressBar.setVisibility(View.GONE);
			Toast.makeText(getBaseContext(), R.string.connection_failed, Toast.LENGTH_SHORT).show();
			super.onCancelled();
		}
	}
}
