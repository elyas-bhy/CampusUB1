package com.dev.campus.map;

import com.dev.campus.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

public class MapActivity extends Activity {

	private final LatLng CAMPUS_BORDEAUX1 = new LatLng(44.80736,-0.596572);
	
	private GoogleMap mMap;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkGooglePlayServicesAvailability();
        setContentView(R.layout.activity_map);
        setUpMap();     
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
	    		Toast.makeText(this,"Error. Please make sure that you have the Play Store Service installed" , Toast.LENGTH_SHORT).show();    
	    }
	}
	
	public void setUpMap(){
		CameraPosition UB1Position = new CameraPosition.Builder()
	    	.target(CAMPUS_BORDEAUX1) 
	    	.zoom(17)                   // Sets the zoom
	    	.bearing(90)                // Sets the orientation of the camera to east
	    	.build();                   // Creates a CameraPosition from the builder
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_UB1)).getMap();
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.moveCamera(CameraUpdateFactory.newCameraPosition(UB1Position));
		
	}
}