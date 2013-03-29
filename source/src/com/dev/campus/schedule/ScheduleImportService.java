package com.dev.campus.schedule;

import com.dev.campus.R;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

public class ScheduleImportService extends IntentService {

	private Handler mHandler;

	public ScheduleImportService() {
		super("ScheduleImportService");
		mHandler = new Handler();
	}

	private void showToast(final int resid) {
		mHandler.post(new Runnable() {	
			@Override
			public void run() {
				Toast.makeText(ScheduleImportService.this, resid, Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		showToast(R.string.schedule_importing);
		try {
			ScheduleParser.parseSchedule(intent.getDataString());
		} catch (Exception e) {
			showToast(R.string.schedule_import_failed_parse);
			return ;
		}
		showToast(R.string.schedule_import_complete);
	}

}