package com.dev.campus.schedule;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.dev.campus.R;
import com.dev.campus.SettingsActivity;
import com.dev.campus.util.FilterDialog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;


public class ScheduleActivity extends ListActivity implements OnItemClickListener {

	private ActionBar mActionBar;
	private Resources mResources;
	private FilterDialog mFilterDialog;
	private ScheduleAdapter mScheduleAdapter;
	private ScheduleGroup mScheduleGroup;
	private List<ScheduleGroup> mListScheduleGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mFilterDialog = new FilterDialog(this);
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mResources = getResources();
		mScheduleAdapter = new ScheduleAdapter(this, new ArrayList<ScheduleGroup>());

		ListView listview = getListView();
		View header = (View) getLayoutInflater().inflate(R.layout.schedule_list_header, listview, false);
		listview.addHeaderView(header, null, false);
		listview.setAdapter(mScheduleAdapter);
		listview.setOnItemClickListener(this);

		Spinner schedule_spinner = (Spinner) findViewById(R.id.schedule_spinner);
		schedule_spinner.setOnItemSelectedListener(new spinnerOnItemSelectedListener());
		// schedule_spinner.performClick();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.with_actionbar, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mScheduleGroup = (ScheduleGroup) parent.getItemAtPosition(position);
		Log.d("LogTag", mScheduleGroup.getGroup());
		Log.d("LogTag", mScheduleGroup.getUrl());
		//TODO Export selected schedule
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			startActivity(new Intent(ScheduleActivity.this, SettingsActivity.class));
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

	public void reloadScheduleGroup() {
		mScheduleAdapter.clear();
		mScheduleAdapter.addAll(mListScheduleGroup);
		mScheduleAdapter.notifyDataSetChanged();
	}

	private class ListGroupTask extends AsyncTask<Void, Void, Void> {

		private String url;

		public ListGroupTask(String url) {
			this.url = url;
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			try {
				mListScheduleGroup = new ScheduleParser().parseFeed(url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}


		@Override
		protected void onPostExecute(Void result) {
			reloadScheduleGroup();
		}

	}

	private class spinnerOnItemSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			String url = "";
			if (pos == 0) { // Licence Semestre 1
				url = "http://www.disvu.u-bordeaux1.fr/et/edt_etudiants2/Licence/Semestre1/finder.xml";
			}
			else if (pos == 1) { // Licence Semestre 2
				url = "http://www.disvu.u-bordeaux1.fr/et/edt_etudiants2/Licence/Semestre2/finder.xml";
			}
			else if (pos == 2) { // Master Semestre 1
				url = "http://www.disvu.u-bordeaux1.fr/et/edt_etudiants2/Master/Semestre1/finder.xml";
			}
			else if (pos == 3) { // Master Semestre 2
				url = "http://www.disvu.u-bordeaux1.fr/et/edt_etudiants2/Master/Semestre2/finder.xml";
			}
			new ListGroupTask(url).execute();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}

	}
}
