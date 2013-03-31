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

package com.dev.campus.schedule;

import java.util.ArrayList;
import java.util.List;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;
import com.dev.campus.SettingsActivity;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;


public class ScheduleActivity extends ListActivity implements OnItemClickListener {

	private final String[] SEMESTER_URLS = {
		"http://www.disvu.u-bordeaux1.fr/et/edt_etudiants2/Licence/Semestre1/finder.xml", // LS1
		"http://www.disvu.u-bordeaux1.fr/et/edt_etudiants2/Licence/Semestre2/finder.xml", // LS2
		"http://www.disvu.u-bordeaux1.fr/et/edt_etudiants2/Master/Semestre1/finder.xml",  // MS1
		"http://www.disvu.u-bordeaux1.fr/et/edt_etudiants2/Master/Semestre2/finder.xml"}; // MS2
	
	private Context mContext;
	private ProgressBar mProgressBar;

	private Group mSelectedGroup;
	private List<Group> mGroups;
	private ScheduleParser mScheduleParser;
	private ScheduleAdapter mScheduleAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		mContext = this;
		mScheduleParser = new ScheduleParser();
		mScheduleAdapter = new ScheduleAdapter(this, new ArrayList<Group>());

		mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
		mProgressBar.setVisibility(View.GONE);
		mProgressBar.setIndeterminate(true);

		ListView listview = getListView();
		View header = (View) getLayoutInflater().inflate(R.layout.schedule_list_header, listview, false);
		listview.addHeaderView(header, null, false);
		listview.setAdapter(mScheduleAdapter);
		listview.setOnItemClickListener(this);

		Spinner spinner = (Spinner) findViewById(R.id.schedule_spinner);
		spinner.setOnItemSelectedListener(new SpinnerOnItemSelectedListener());
		spinner.performClick();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.default_actionbar, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mSelectedGroup = (Group) parent.getItemAtPosition(position);
		registerForContextMenu(view);
		view.setLongClickable(false);
		openContextMenu(view);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			startActivity(new Intent(ScheduleActivity.this, SettingsActivity.class));
			return true;
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.schedule_contextual, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		String urlXml = mSelectedGroup.getUrl();
		switch (item.getItemId()) {
		case R.id.menu_schedule_view_online:
			String urlHtml = urlXml.substring(0, urlXml.length()-3) + "html"; // replace extension from xml to html
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlHtml)));
			return true;
		case R.id.menu_schedule_download:
			String urlPdf = urlXml.substring(0, urlXml.length()-3) + "pdf"; // replace extension from xml to pdf
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPdf)));
			return true;
		case R.id.menu_schedule_import:
			new ScheduleConfirmDialog(mContext);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	public void clearContent() {
		mScheduleAdapter.clear();
		mScheduleAdapter.notifyDataSetChanged();
	}

	public void reloadContent() {
		mScheduleAdapter.clear();
		mScheduleAdapter.addAll(mGroups);
		mScheduleAdapter.notifyDataSetChanged();
	}


	private class FetchGroupsTask extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			clearContent();
			mProgressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(String... urls) {
			if (urls.length > 0) {
				try {
					mGroups = mScheduleParser.fetchGroups(urls[0]);
				} catch (Exception e) {
					cancel(true);
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			reloadContent();
			mProgressBar.setVisibility(View.GONE);
		}

		@Override
		protected void onCancelled() {
			mGroups = new ArrayList<Group>();
			CampusUB1App.LogD("Failed to retrieve schedule");
			Toast.makeText(mContext, R.string.schedule_import_failed_fetchgroup, Toast.LENGTH_SHORT).show();
			clearContent();
			mProgressBar.setVisibility(View.GONE);
		}
	}

	private class SpinnerOnItemSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			if (position == 0) {  // dummy item
				clearContent();
				return;
			}
			// offset position by 1 to compensate for dummy item
			new FetchGroupsTask().execute(SEMESTER_URLS[position-1]);
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}
	}

	private class ScheduleConfirmDialog extends AlertDialog.Builder {

		public ScheduleConfirmDialog(Context context) {
			super(context);
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			builder.setMessage(R.string.schedule_confirm_content)
			.setTitle(R.string.warning)
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Intent importService = new Intent(ScheduleActivity.this, ScheduleImportService.class);
					importService.setData(Uri.parse(mSelectedGroup.getUrl()));
					mContext.startService(importService);
					finish();
				}
			})
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {

				}
			});
			builder.show();
		}
	}
}
