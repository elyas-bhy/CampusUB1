package com.dev.campus;

import com.dev.campus.ac.HomeActivity;
import com.dev.campus.util.SubscribeDialog;
import com.dev.campus.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class HelperActivity extends Activity {

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
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
		if (CampusUB1App.persistence.isUBSubscribed() || CampusUB1App.persistence.isLabriSubscribed()) {
			startActivity(new Intent(this, HomeActivity.class));
			finish();
		}
		else {
			mSubscribeDialog.show();
		}
    }
    
    @Override
 	public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    		case R.id.menu_settings:
    			Intent intent = new Intent();
    			intent.setClass(HelperActivity.this, SettingsActivity.class);
    			startActivityForResult(intent, 0);
    			return true;
    		default:
    			break;
    	}
    	return false;
    }
	 
}