package com.dev.campus.schedule;


import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.dev.campus.R;
import com.dev.campus.SettingsActivity;
import com.dev.campus.util.FilterDialog;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.IntentService;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


public class ScheduleActivity extends ListActivity implements OnItemClickListener {

	private final String LS1_URL = "http://www.disvu.u-bordeaux1.fr/et/edt_etudiants2/Licence/Semestre1/finder.xml";
	private final String LS2_URL = "http://www.disvu.u-bordeaux1.fr/et/edt_etudiants2/Licence/Semestre2/finder.xml";
	private final String MS1_URL = "http://www.disvu.u-bordeaux1.fr/et/edt_etudiants2/Master/Semestre1/finder.xml";
	private final String MS2_URL = "http://www.disvu.u-bordeaux1.fr/et/edt_etudiants2/Master/Semestre2/finder.xml";

	private ActionBar mActionBar;
	private Context mContext;
	private FilterDialog mFilterDialog;

	private ScheduleParser mScheduleParser;
	private ScheduleAdapter mScheduleAdapter;
	private ScheduleGroup mScheduleGroup;
	private List<ScheduleGroup> mScheduleGroups;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFilterDialog = new FilterDialog(this);
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);

		mContext = this;
		mScheduleParser = new ScheduleParser();
		mScheduleAdapter = new ScheduleAdapter(this, new ArrayList<ScheduleGroup>());

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
		mScheduleGroup = (ScheduleGroup) parent.getItemAtPosition(position);
		new ScheduleConfirmDialog(mContext);
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

	public void reloadContent() {
		mScheduleAdapter.clear();
		mScheduleAdapter.addAll(mScheduleGroups);
		mScheduleAdapter.notifyDataSetChanged();
	}

	private class ListGroupTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... urls) {
			if (urls.length > 0) {
				try {
					mScheduleGroups = mScheduleParser.parseFeed(urls[0]);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			reloadContent();
		}
	}

	private class SpinnerOnItemSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			String url = "";
			switch (position) {
			case 0:	// dummy item
				mScheduleGroups = new ArrayList<ScheduleGroup>();
				reloadContent();
				return;
			case 1:
				url = LS1_URL;
				break;
			case 2:
				url = LS2_URL;
				break;
			case 3:
				url = MS1_URL;
				break;
			case 4:
				url = MS2_URL;
				break;
			}
			new ListGroupTask().execute(url);
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
					importService.setData(Uri.parse(mScheduleGroup.getUrl()));
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
