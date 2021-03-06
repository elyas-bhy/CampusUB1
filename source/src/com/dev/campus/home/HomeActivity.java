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

package com.dev.campus.home;

import java.util.ArrayList;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;
import com.dev.campus.SettingsActivity;
import com.dev.campus.directory.DirectoryActivity;
import com.dev.campus.event.EventsActivity;
import com.dev.campus.map.MapActivity;
import com.dev.campus.schedule.ScheduleActivity;
import com.dev.campus.util.FilterDialog;

import android.net.Uri;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Class responsible for the home activity UI and lifecycle,
 * handles launch of implemented services
 * @author CampusUB1 Development Team
 *
 */
public class HomeActivity extends ListActivity {
	
	// Homepages URLs
	private final String UB1_HOMEPAGE = "http://www.u-bordeaux1.fr/";
	private final String LABRI_HOMEPAGE = "http://www.labri.fr/";

	// Utilities
	private FilterDialog mFilterDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFilterDialog = new FilterDialog(this);

		//ListView styling
		ListView listView = getListView();
		listView.setBackgroundResource(R.color.white_light);
		listView.setDivider(getResources().getDrawable(R.color.grey_divider));
		listView.setDividerHeight(1);
		
		View header = (View) getLayoutInflater().inflate(R.layout.home_header, listView, false);
		listView.addHeaderView(header, null, false);
		
		//Setup adapter items
		ArrayList<HomeItem> items = new ArrayList<HomeItem>();
		items.add(new HomeEntryItem(R.drawable.ic_menu_events, R.string.events));
		items.add(new HomeEntryItem(R.drawable.ic_menu_directory, R.string.directory));
		items.add(new HomeEntryItem(R.drawable.ic_menu_schedule, R.string.schedule));
		items.add(new HomeEntryItem(R.drawable.ic_menu_maps, R.string.map));
		items.add(new HomeSeparatorItem());	//separator
		items.add(new HomeEntryItem(R.drawable.ic_menu_bdx1, R.string.ub1));
		items.add(new HomeEntryItem(R.drawable.ic_menu_labri, R.string.labri));
		
		setListAdapter(new HomeAdapter(this, items));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.filters_actionbar, menu);
		return true;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		switch (position) {
		case 1: // Events
			startActivity(new Intent(HomeActivity.this, EventsActivity.class));
			break;
		case 2: // Directory
			startActivity(new Intent(HomeActivity.this, DirectoryActivity.class));
			break;
		case 3: // Schedule
			startActivity(new Intent(HomeActivity.this, ScheduleActivity.class));
			break;
		case 4: // Map
			if (CampusUB1App.persistence.isConnected())
				startActivity(new Intent(HomeActivity.this, MapActivity.class));
			else
				Toast.makeText(this, R.string.connection_required, Toast.LENGTH_SHORT).show();  
			break;
		case 6: // UB1 website
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(UB1_HOMEPAGE)));
			break;
		case 7: // LaBRI website
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(LABRI_HOMEPAGE)));
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
