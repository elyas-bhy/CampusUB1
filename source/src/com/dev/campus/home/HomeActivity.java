package com.dev.campus.home;

import java.util.ArrayList;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;
import com.dev.campus.SettingsActivity;
import com.dev.campus.directory.DirectoryActivity;
import com.dev.campus.event.EventsActivity;
import com.dev.campus.map.MapActivity;
import com.dev.campus.util.FilterDialog;

import android.net.Uri;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends ListActivity {
	
	private final String UB1_HOMEPAGE = "http://www.u-bordeaux1.fr/";
	private final String LABRI_HOMEPAGE = "http://www.labri.fr/";

	private FilterDialog mFilterDialog;
	private Resources mResources;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFilterDialog = new FilterDialog(this);
		mResources= getResources();

		//ListView styling
		ListView listView = getListView();
		listView.setBackgroundResource(R.color.grey_metal_dark);
		listView.setDivider(mResources.getDrawable(R.color.grey_metal_dark_divider));
		listView.setDividerHeight(1);
		
		//Setup adapter items
		ArrayList<HomeEntryItem> items = new ArrayList<HomeEntryItem>();
		items.add(new HomeEntryItem(mResources.getString(R.string.events)));
		items.add(new HomeEntryItem(mResources.getString(R.string.directory)));
		items.add(new HomeEntryItem(mResources.getString(R.string.schedule)));
		items.add(new HomeEntryItem(mResources.getString(R.string.map)));
		items.add(new HomeEntryItem());	//separator
		items.add(new HomeEntryItem(mResources.getString(R.string.ub1)));
		items.add(new HomeEntryItem(mResources.getString(R.string.labri)));
		
		setListAdapter(new HomeEntryAdapter(this, items));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.with_actionbar, menu);
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		switch (position) {
		case 0: // Events
			startActivity(new Intent(HomeActivity.this, EventsActivity.class));
			break;
		case 1: // Directory
			startActivity(new Intent(HomeActivity.this, DirectoryActivity.class));
			break;
		case 2: // Schedule
			//TODO
			break;
		case 3: // Map
			if(CampusUB1App.persistence.isOnline())
				startActivity(new Intent(HomeActivity.this, MapActivity.class));
			else
				Toast.makeText(this,mResources.getString(R.string.connection_failed), Toast.LENGTH_SHORT).show();  
			break;
		case 5: // UB1 website
			Intent browserUB1 = new Intent(Intent.ACTION_VIEW, Uri.parse(UB1_HOMEPAGE));
			startActivity(browserUB1);
			break;
		case 6: // LaBRI website
			Intent browserLabri = new Intent(Intent.ACTION_VIEW, Uri.parse(LABRI_HOMEPAGE));
			startActivity(browserLabri);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menu_settings:
			startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
			return true;
		case R.id.menu_filters:
			mFilterDialog.showDialog();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
