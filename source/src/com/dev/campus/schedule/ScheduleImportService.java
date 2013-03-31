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