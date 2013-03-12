package com.dev.campus.map;

import java.util.ArrayList;
import java.util.List;

import com.dev.campus.R;
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
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class MapActivity extends Activity implements LocationListener {

	//Used CameraPosition attributes
	private final int BEARING = 69;
	private final int ZOOM = 16;
	private final int UPDATE_FREQUENCY = 20000; //Update frequency (ms)
	private final LatLng MAP_CENTER = new LatLng(44.80736, -0.596572);

	private CheckBox mServices, mRestauration, mBuildings;
	private Resources mResources;
	private GoogleMap mMap;
	private LocationManager mLocationManager;
	private Marker mCurrentLocation;
	private ArrayList<Marker> mServicesMarkers, mRestaurationMarkers, mBuildingsMarkers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		mResources = getResources();
		mServices = (CheckBox) findViewById(R.id.services_check);
		mRestauration = (CheckBox) findViewById(R.id.restauration_check);
		mBuildings = (CheckBox) findViewById(R.id.buildings_check); 
		checkGooglePlayServicesAvailability();
		setUpMap();
		setUpLocationServices();
		setUpMarkers();
	}

	public void onCheckboxClicked(View view) {
		switch(view.getId()) {
		case R.id.services_check:
			populateMap(mServicesMarkers, mServices.isChecked());
			break;
		case R.id.restauration_check:
			populateMap(mRestaurationMarkers, mRestauration.isChecked());
			break;
		case R.id.buildings_check:
			populateMap(mBuildingsMarkers, mBuildings.isChecked());
			break;
		}
	}

	public void checkGooglePlayServicesAvailability() {
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

		if (status != ConnectionResult.SUCCESS) {
			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,requestCode);
			if (status == ConnectionResult.SERVICE_MISSING 
			 || status== ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED 
			 || status == ConnectionResult.SERVICE_DISABLED)
				dialog.show();
			else
				Toast.makeText(this, mResources.getString(R.string.no_play_services), Toast.LENGTH_SHORT).show();    
		}
	}

	public void setUpMap() {
		CameraPosition UB1Position = new CameraPosition.Builder()
		.target(MAP_CENTER)
		.zoom(ZOOM)                   
		.bearing(BEARING)               
		.build();  

		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_UB1)).getMap();
		mMap.getUiSettings().setCompassEnabled(false);
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.moveCamera(CameraUpdateFactory.newCameraPosition(UB1Position));
	}

	public void setUpLocationServices() {
		mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		String provider = getNewProvider();
		Location location = mLocationManager.getLastKnownLocation(provider);
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		LatLng currentPosition = new LatLng(latitude, longitude);
		
		mCurrentLocation = mMap.addMarker(new MarkerOptions()
		.position(currentPosition)
		.title("Votre position")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.user_location_marker)));   
		
		if (location != null)
			onLocationChanged(location);
	}

	public void setUpMarkers() {
		mServicesMarkers = new ArrayList<Marker>();
		mRestaurationMarkers = new ArrayList<Marker>();
		mBuildingsMarkers = new ArrayList<Marker>();
		for (Position pos : Position.values()) {
			MarkerOptions options = new MarkerOptions()
			.position(new LatLng(pos.getLat(), pos.getLng()))
			.icon(BitmapDescriptorFactory.fromResource(mResources.getIdentifier(pos.getType().getDrawableId(), "drawable", getPackageName())))
			.draggable(false)
			.title(pos.getName());
			Marker marker = mMap.addMarker(options);
			
			switch (pos.getType()) {
			case BUILDING:
				mBuildingsMarkers.add(marker);
				break;
			case RESTAURATION:
				mRestaurationMarkers.add(marker);
				break;
			case SERVICE:
				mServicesMarkers.add(marker);
			}
		}
	}

	public void populateMap(List<Marker> markers, boolean checked) {
		for (Marker marker : markers)
			marker.setVisible(checked);			
	}

	@Override
	public void onLocationChanged(Location location) {
		mCurrentLocation.remove();
		LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
		mCurrentLocation = mMap.addMarker(new MarkerOptions()
		.position(currentPosition)
		.title("Votre position")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.user_location_marker)));      
	}

	public boolean isGpsEnabled() {
		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		return service.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public String getNewProvider(){
		mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		String provider;
		if (isGpsEnabled()) {
			provider = LocationManager.GPS_PROVIDER;
			mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,  this);
		}
		else {
			provider = LocationManager.NETWORK_PROVIDER;
			mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, UPDATE_FREQUENCY, 0, this);	
		}
		return provider;
	}

	@Override
	public void onProviderDisabled(String provider) {
		getNewProvider(); 		
	}

	@Override
	public void onProviderEnabled(String provider) {
		getNewProvider();		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
	}
}
