package com.dev.campus.map;

import java.util.ArrayList;
import java.util.List;

import com.dev.campus.CampusUB1App;
import com.dev.campus.R;
import com.dev.campus.map.Position.PositionType;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class MapActivity extends Activity {

	//Used CameraPosition attributes
	private final int MAP_BEARING = 69;
	private final int SETUP_ZOOM = 17;
	private final int AT_POSITION_ZOOM = 19;
	private final LatLng MAP_CENTER = new LatLng(44.80736,-0.596572);
	
	private CheckBox mServices, mRestauration, mBuildings;
	private Resources mResources;
	private GoogleMap mMap;
	private ArrayList<Marker> mServicesMarkers, mRestaurationMarkers, mBuildingsMarkers;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkInternetConnection();
        checkGooglePlayServicesAvailability();
        setContentView(R.layout.activity_map);
        mResources = getResources();
        mServicesMarkers = new ArrayList<Marker>();
        mRestaurationMarkers = new ArrayList<Marker>();
        mBuildingsMarkers = new ArrayList<Marker>(); 
	    mServices = (CheckBox)findViewById(R.id.services_check);
	    mRestauration = (CheckBox)findViewById(R.id.restauration_check);
	    mBuildings = (CheckBox)findViewById(R.id.buildings_check); 
        setUpMap();  
     }

	public void onCheckboxClicked(View view) {
	    switch(view.getId()) {
	        case R.id.services_check:
	            if (mServices.isChecked())
	            	populateMap(PositionType.SERVICE,mServicesMarkers,"services_marker");
	            else
	            	unPopulateMap(mServicesMarkers);
	            	break;
	        case R.id.restauration_check:
	            if (mRestauration.isChecked())
	            	populateMap(PositionType.RESTAURATION,mRestaurationMarkers,"restauration_marker");
	            else
	            	unPopulateMap(mRestaurationMarkers);
	            break;
	        case R.id.buildings_check:
	        	 if (mBuildings.isChecked())
	        		 populateMap(PositionType.BUILDING,mBuildingsMarkers,"building_marker");
	 	         else
	 	        	 unPopulateMap(mBuildingsMarkers);
	 	         break;
	    }
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
		CameraPosition UB1Position = new CameraPosition.Builder()
	    	.target(MAP_CENTER)
	    	.zoom(SETUP_ZOOM)                   
	    	.bearing(MAP_BEARING)               
	    	.build();                  
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_UB1)).getMap();
		mMap.getUiSettings().setCompassEnabled(false);
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
	
	public void populateMap(PositionType positionType,List<Marker> container,String icon_name){
			for (Position pos : Position.values()) {
				if(pos.getType().equals(positionType)){
					 Marker marker = mMap.addMarker(new MarkerOptions()
					.position(new LatLng(pos.getLat(), pos.getLng()))
					.draggable(false)
					.icon(BitmapDescriptorFactory.fromResource(mResources.getIdentifier(icon_name,"drawable", getPackageName())))
					.title(pos.getName()));	
					container.add(marker);
				}
			}
		}
	
	public void unPopulateMap(List<Marker> container){
		for(int i = 0; i <container.size();i++)
				container.get(i).remove();
		container.clear();
		}
}
	