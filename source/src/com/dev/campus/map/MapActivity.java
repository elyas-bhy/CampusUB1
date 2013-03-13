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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.Toast;

public class MapActivity extends Activity implements LocationListener {

	//Used CameraPosition attributes
	private final int BEARING = 69;
	private final int DEFAULT_ZOOM = 16;
	private final int SEARCH_ZOOM = 18;
	private final int UPDATE_FREQUENCY = 20000; //Update frequency (ms)
	private final LatLng MAP_CENTER = new LatLng(44.80736, -0.596572);

	private LocationManager mLocationManager;
	private Resources mResources;
	private GoogleMap mMap;
	
	private ArrayList<Marker> mServicesMarkers, mRestaurationMarkers, mBuildingsMarkers;
	private CheckBox mServices, mRestauration, mBuildings;
	
	private Marker mCurrentLocation;
	private SearchView mSearchView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		mResources = getResources();
		mServices = (CheckBox) findViewById(R.id.services_check);
		mRestauration = (CheckBox) findViewById(R.id.restauration_check);
		mBuildings = (CheckBox) findViewById(R.id.buildings_check); 
		checkGooglePlayServicesAvailability();
		setupMap();
		setupMarkers();
		setupLocationServices();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,  this);
		getNewProvider();
	}
	
	@Override     
    protected void onPause() {  
		super.onPause(); 
		mLocationManager.removeUpdates(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.map_menu, menu);
		mSearchView = (SearchView) menu.findItem(R.id.map_search).getActionView();
		mSearchView.setOnQueryTextListener(mQueryTextListener);		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_campus_pos:
			goToPosition(MAP_CENTER,DEFAULT_ZOOM);
			return true;	
		case R.id.menu_my_pos:
			goToPosition(mCurrentLocation.getPosition(),DEFAULT_ZOOM);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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

	@SuppressLint("DefaultLocale")
	public void searchPosition(String input){
		boolean found = false;
		for (Position pos : Position.values()){
			if((pos.getName().toLowerCase().equals(input.toLowerCase()) || (pos.getName().toLowerCase().contains(input.toLowerCase())))){
				ArrayList<Marker> markerType = null;
				switch(pos.getType()) {
				case BUILDING:
					markerType = mBuildingsMarkers;
					break;
				case RESTAURATION:
					markerType= mRestaurationMarkers;
					break;
				case SERVICE:
					markerType = mServicesMarkers;	
					break;
				}
				for(Marker marker : markerType)
					if(pos.getmID().equals(marker.getId())){
						marker.setVisible(true);
						marker.showInfoWindow();
						break;
					}
				goToPosition(new LatLng(pos.getLat(),pos.getLng()),SEARCH_ZOOM);
				found = true;
				break;
			}
		}
		if(!found)
			Toast.makeText(this, mResources.getString(R.string.map_not_found), Toast.LENGTH_SHORT).show();
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

	public void setupMap() {
		CameraPosition UB1Position = new CameraPosition.Builder()
		.target(MAP_CENTER)
		.zoom(DEFAULT_ZOOM)                   
		.bearing(BEARING)               
		.build();  

		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_UB1)).getMap();
		mMap.getUiSettings().setCompassEnabled(false);
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.moveCamera(CameraUpdateFactory.newCameraPosition(UB1Position));
	}

	public void setupLocationServices() {
		mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		String provider = getNewProvider();
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,  this);
		Location location = mLocationManager.getLastKnownLocation(provider);
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		LatLng currentPosition = new LatLng(latitude, longitude);
		
		mCurrentLocation = mMap.addMarker(new MarkerOptions()
		.position(currentPosition)
		.title(mResources.getString(R.string.my_position))
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.user_location_marker)));   
		
		if (location != null)
			onLocationChanged(location);
	}

	public void setupMarkers() {
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

	public void goToPosition(LatLng pos,int zoom) {
		CameraPosition position  = new CameraPosition.Builder()
		.target(pos)
		.zoom(zoom)                   
		.bearing(BEARING)               
		.build();                  
		mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
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
	public void onLocationChanged(Location location) {
		mCurrentLocation.remove();
		LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
		mCurrentLocation = mMap.addMarker(new MarkerOptions()
		.position(currentPosition)
		.title(mResources.getString(R.string.my_position))
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.user_location_marker)));      
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
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

	final SearchView.OnQueryTextListener mQueryTextListener = new SearchView.OnQueryTextListener() {
		@Override
		public boolean onQueryTextChange(String text) {
			//Do something
			return true;
		}

		@Override
		public boolean onQueryTextSubmit(String query) {
			searchPosition(query);
			//Hide soft keyboard
			if (getCurrentFocus() != null) {
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
				mSearchView.clearFocus();
			}
			return true;
		}
	};
}
