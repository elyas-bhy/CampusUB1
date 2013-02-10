package com.dev.campus.ac;

import com.dev.campus.R;
import com.dev.campus.SettingsActivity;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends ListActivity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	String item = (String) getListAdapter().getItem(position);
    	Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
    }
    
    @Override
 	public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    		case R.id.menu_settings:
    			Intent intent = new Intent();
    			intent.setClass(HomeActivity.this, SettingsActivity.class);
    			startActivityForResult(intent, 0);
    			return true;
    		default:
    			break;
    	}
    	return false;
    }
    
}