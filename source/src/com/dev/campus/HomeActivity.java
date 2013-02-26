package com.dev.campus;

import com.dev.campus.R;
import com.dev.campus.directory.DirectoryActivity;
import com.dev.campus.event.EventsActivity;
import com.dev.campus.util.FilterDialog;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeActivity extends ListActivity {
	
	private FilterDialog mFilterDialog;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mFilterDialog = new FilterDialog(this);
        Resources res = getResources();
        String[] values = new String[] { res.getString(R.string.events), 
        								 res.getString(R.string.directory),
        								 res.getString(R.string.schedule),
        								 res.getString(R.string.map)};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
