package com.dev.campus.map;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;
import com.dev.campus.util.Persistence;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

public class MapActivity extends Activity {

	//Used CameraPosition attributes
	private final int MAP_BEARING = 69;
	private final int SETUP_ZOOM = 17;
	private final int AT_POSITION_ZOOM = 19;
	
	private Resources mResources;
	private GoogleMap mMap;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResources = getResources();
        checkInternetConnection();
        checkGooglePlayServicesAvailability();
        setContentView(R.layout.activity_map);
        setUpMap();     
        populateMap();
     }

	public void checkInternetConnection() {
		if(!CampusUB1App.persistence.isOnline())
			Toast.makeText(this,mResources.getString(R.string.connection_failed), Toast.LENGTH_SHORT).show();  
	}
	
	public void checkGooglePlayServicesAvailability()
	{
	    int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	   
	    if(status != ConnectionResult.SUCCESS){
	    	Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,69);
	    	if(status == ConnectionResult.SERVICE_MISSING 
	    	|| status== ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED 
	    	|| status == ConnectionResult.SERVICE_DISABLED)
	    		dialog.show();
	    	else
	    		Toast.makeText(this,mResources.getString(R.string.no_play_services), Toast.LENGTH_SHORT).show();    
	    }
	}
	
	public void setUpMap(){
		LatLng UB1 = new LatLng(Position.CENTRE_CAMPUS.getLat(),Position.CENTRE_CAMPUS.getLng());
		CameraPosition UB1Position = new CameraPosition.Builder()
	    	.target(UB1)
	    	.zoom(SETUP_ZOOM)                   
	    	.bearing(MAP_BEARING)               
	    	.build();                  
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_UB1)).getMap();
		mMap.getUiSettings().setAllGesturesEnabled(false);
		mMap.getUiSettings().setScrollGesturesEnabled(true);
		mMap.getUiSettings().setCompassEnabled(false);
		mMap.getUiSettings().setZoomControlsEnabled(false);
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.moveCamera(CameraUpdateFactory.newCameraPosition(UB1Position));
	}
	
	public void goToPosition(Position pos){
		CameraPosition position  = new CameraPosition.Builder()
	    	.target(new LatLng(pos.getLat(),pos.getLng()))
	    	.zoom(AT_POSITION_ZOOM)                   
	    	.bearing(MAP_BEARING)               
	    	.build();                  
		mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
	}
	
	public void populateMap(){
		for (Position pos : Position.values()) {
			mMap.addMarker(new MarkerOptions()
			.position(new LatLng(pos.getLat(), pos.getLng()))
			.draggable(false)
			.visible(false)
			.title(pos.getName()));	
		}
	}	
}