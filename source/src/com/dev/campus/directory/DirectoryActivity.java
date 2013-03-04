package com.dev.campus.directory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;
import com.dev.campus.SettingsActivity;
import com.dev.campus.event.Category;
import com.dev.campus.util.FilterDialog;
import com.dev.campus.util.Persistence;
import com.unboundid.ldap.sdk.LDAPException;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class DirectoryActivity extends ListActivity {

	private ActionBar mActionBar;
	private FilterDialog mFilterDialog;
	private ListView listview;
	private DirectoryAdapter adapter;
	private Resources mResources;
	private Activity mContext;
	private Contact selectedContact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;
		mFilterDialog = new FilterDialog(this);
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mResources = getResources();
		ArrayList<Contact> matchingContacts = new ArrayList<Contact>();

		listview = getListView();
		View header = (View) getLayoutInflater().inflate(R.layout.directory_list_header, listview, false);
		listview.addHeaderView(header, null, true);
		adapter = new DirectoryAdapter(this, matchingContacts);
		listview.setAdapter(adapter);

		registerForContextMenu(listview);
		listview.setOnItemClickListener(new OnItemClickListener() {

		       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    	   selectedContact = (Contact) parent.getItemAtPosition(position);
		    	   listview.showContextMenuForChild(view);
		       }
		});
		
		
		final ImageButton searchButton = (ImageButton) findViewById(R.id.buttonSearchDirectory);
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new SearchResultTask().execute();
			}
		});
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.directory_contextual, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_call:
	        	callContact(selectedContact);
	            return true;
	        case R.id.menu_mail:
	        	emailContact(selectedContact);
	            return true;
	        case R.id.menu_addToContacts:	
	        	addToPhoneContacts(selectedContact);
	            return true;
	        case R.id.menu_webSite:	  
	        	visitContactWebsite(selectedContact);
	            return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
	
	public void callContact(Contact c){
		String ContactNumber = c.getTel();
		if(c.existsTel()){
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:"+ContactNumber));
			startActivity(callIntent);
		}
		else
			Toast.makeText(this, mResources.getString(R.string.no_tel), Toast.LENGTH_SHORT).show();
	}
	
	public void emailContact(Contact c) {
		String ContactEmail = c.getEmail();
		if(c.existsEmail()){
			Intent emailIntent = new Intent(Intent.ACTION_SEND);
			emailIntent.setType("plain/text");  
			emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[] {ContactEmail});
			startActivity(Intent.createChooser(emailIntent, "Choisissez votre client :"));
		}
		else
			Toast.makeText(this, mResources.getString(R.string.no_email), Toast.LENGTH_SHORT).show();
	}
	
	public void addToPhoneContacts(Contact c) {
		String ContactFullName =  c.getFirstName() + " " + c.getLastName();
		String ContactNumber = c.getTel();
		String ContactEmail =  c.getEmail();
		Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT,ContactsContract.Contacts.CONTENT_URI);
		intent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
		intent.putExtra(ContactsContract.Intents.Insert.NAME, ContactFullName);
		if(c.existsTel())
			intent.putExtra(ContactsContract.Intents.Insert.PHONE, ContactNumber);	
		if(c.existsEmail())
			intent.putExtra(ContactsContract.Intents.Insert.EMAIL, ContactEmail);
		startActivity(intent);
	}

	public void visitContactWebsite(Contact c) {
		String ContactWebSite = c.getWebsite();
		if(c.existsWebsite()){
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ContactWebSite));
			startActivity(browserIntent);
		}
		else
			Toast.makeText(this, mResources.getString(R.string.no_webSite), Toast.LENGTH_SHORT).show();
	}
	
	public void reloadContacts(List<Contact> matchingContacts){
		adapter.clear();
		adapter.addAll(matchingContacts);
		adapter.notifyDataSetChanged();
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


	private class SearchResultTask extends AsyncTask<Category, Void, List<Contact>> {

		private ProgressDialog progressDialog = new ProgressDialog(DirectoryActivity.this);

		@Override
		protected void onPreExecute() {
			progressDialog.setTitle(mResources.getString(R.string.contacts_loading));
			progressDialog.setMessage(mResources.getString(R.string.please_wait));
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected List<Contact> doInBackground(Category... params) {
			final TextView firstName = (TextView) findViewById(R.id.editTextFirstName);
			final TextView lastName = (TextView) findViewById(R.id.editTextLastName);

			int searchMinChar = 3;
			boolean join = false; // becomes true if there are 2 or more filters active
			ArrayList<Contact> matchingContacts = new ArrayList<Contact>();
			if (firstName.getText().toString().length() >= searchMinChar || lastName.getText().toString().length() >= searchMinChar) {
				ArrayList<Contact> contacts = new ArrayList<Contact>();
				ArrayList<Contact> contacts2 = null;
				
				if (Persistence.isFilteredUB1() && Persistence.isFilteredLabri() ) {
					join = true;
					try {
						if (Directory_UB1.authenticate() == true) {
							contacts = Directory_UB1.search(lastName.getText().toString(), firstName.getText().toString());
						}
					} catch (LDAPException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					contacts2 = new ArrayList<Contact>();
					try {
						contacts2 = new Directory().labriDirectoryParser();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if (Persistence.isFilteredUB1()) {
					try {
						if (Directory_UB1.authenticate() == true) {
							contacts = Directory_UB1.search(lastName.getText().toString(), firstName.getText().toString());
						}
					} catch (LDAPException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if (Persistence.isFilteredLabri()) {
					try {
						contacts = new Directory().labriDirectoryParser();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (join){ // 2 or more filters are active
					ArrayList<Contact> MergedContacts = new ArrayList<Contact>();
					MergedContacts.addAll(contacts);
					MergedContacts.addAll(contacts2);
					contacts = MergedContacts;
				}	
				matchingContacts = new Directory().directoryFilter(contacts, firstName.getText().toString(), lastName.getText().toString());	
			}
			else {
				//Toast.makeText(mContext, searchMinChar+" charactères minimum!", Toast.LENGTH_SHORT).show();
			}

			Collections.sort(matchingContacts, new ContactComparator());

			return matchingContacts;
		}

		@Override
		protected void onPostExecute(List<Contact> contacts) {
			reloadContacts(contacts);
			progressDialog.dismiss();
		}

		@Override
		protected void onCancelled() {
			progressDialog.dismiss();
			super.onCancelled();
		}
	}

}
