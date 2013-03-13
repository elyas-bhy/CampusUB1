package com.dev.campus;

import com.dev.campus.R;
import com.dev.campus.directory.DirectoryActivity;
import com.dev.campus.event.EventsActivity;
import com.dev.campus.map.MapActivity;
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
import android.widget.Toast;

public class HomeActivity extends ListActivity {
	
	private FilterDialog mFilterDialog;
	private Resources mResources;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		mFilterDialog = new FilterDialog(this);
		mResources= getResources();
		String[] menu = mResources.getStringArray(R.array.main_menu);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, menu);
        getListView().setBackgroundResource(R.color.metal_grey);
        getListView().setDivider(mResources.getDrawable(R.color.dark_metal_grey));
        getListView().setDividerHeight(1);
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
    			break;
    		case 3: // Map
    			if(CampusUB1App.persistence.isOnline())
    				startActivity(new Intent(HomeActivity.this, MapActivity.class));
    			else
    				Toast.makeText(this,mResources.getString(R.string.connection_failed), Toast.LENGTH_SHORT).show();  
    			break;
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
