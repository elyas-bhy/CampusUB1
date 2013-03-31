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

package com.dev.campus;

import com.dev.campus.home.HomeActivity;
import com.dev.campus.util.SubscribeDialog;
import com.dev.campus.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class LauncherActivity extends Activity {

	private SubscribeDialog mSubscribeDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		mSubscribeDialog = new SubscribeDialog(this);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.default_actionbar, menu);
        return true;
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
		if (CampusUB1App.persistence.isSubscribedUB1() || CampusUB1App.persistence.isSubscribedLabri()) {
			startActivity(new Intent(this, HomeActivity.class));
			finish();
		}
		else {
			mSubscribeDialog.showDialog(true);
		}
    }
    
    @Override
 	public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    		case R.id.menu_settings:
    			startActivity(new Intent(LauncherActivity.this, SettingsActivity.class));
    			return true;
    		default:
    			break;
    	}
    	return false;
    }
	 
}
