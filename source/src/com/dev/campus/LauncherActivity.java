package com.dev.campus;

import com.dev.campus.util.SubscribeDialog;
import com.dev.campus.HomeActivity;
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
		setContentView(R.layout.activity_main);
		mSubscribeDialog = new SubscribeDialog(this);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.without_actionbar, menu);
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
