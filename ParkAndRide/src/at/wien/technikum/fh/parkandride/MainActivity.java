package at.wien.technikum.fh.parkandride;

import java.util.Collection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import at.wien.technikum.fh.parkandride.domain.ParkAndRide;
import at.wien.technikum.fh.parkandride.service.api.ParkAndRideService;
import at.wien.technikum.fh.parkandride.service.impl.ParkAndRideServiceImpl;
import at.wien.technikum.fh.parkandride.support.AsyncJSONHelper;
import at.wien.technikum.fh.parkandride.support.GPSTracker;
import at.wien.technikum.fh.parkandride.support.OnLoadCompleteListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements
		OnLoadCompleteListener, OnInfoWindowClickListener {
	private GoogleMap mMap;
	private GPSTracker gpsTracker;
	private double longitude;
	private double latitude;
	private ParkAndRideService dbService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUpMapIfNeeded();
		// call async service
		getDataFromWeb();

		// create db service
		dbService = new ParkAndRideServiceImpl(this);

	}

	// fetching Data from web
	private void getDataFromWeb() {
		AsyncJSONHelper asyncJSONHelper = new AsyncJSONHelper(this);
		// set listener for callback
		asyncJSONHelper.setListener(this);

		// ecexute task
		Toast.makeText(this,
				"Start Fetching Data from Web and storing in Database",
				Toast.LENGTH_SHORT).show();
		asyncJSONHelper.execute("");

	}

	private void setUpMapIfNeeded() {
		gpsTracker = new GPSTracker(this);
		if (gpsTracker.canGetLocation()) {
			longitude = gpsTracker.getLongitude();
			latitude = gpsTracker.getLatitude();
		} else {
			gpsTracker.showSettingsAlert();
		}
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	private void setUpMap() {
		mMap.setMyLocationEnabled(true);
		// 2.0 and 21.0 where 2.0 is maximum zoom out and 21.0 is maximum zoom
		// in
		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
				latitude, longitude), 12.0f));
	}



	@Override
	public void loadCompleted() {
		// fetch park and rides from Db
		Toast.makeText(this, "Loading Data from Database", Toast.LENGTH_SHORT)
				.show();
		Collection<ParkAndRide> parkAndRides = dbService.getParkAndRides();
		// create markes on map
		createMarkers(parkAndRides);
	}

	private void createMarkers(Collection<ParkAndRide> parkAndRides) {

		for (ParkAndRide parkAndRide : parkAndRides) {
			MarkerOptions markerOptions = new MarkerOptions();
			markerOptions.title(parkAndRide.getGarageName());
			markerOptions
					.position(new LatLng(parkAndRide.getCoordinates()
							.getLatitude(), parkAndRide.getCoordinates()
							.getLongitude()));
			markerOptions.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.ic_launcher));
			// set the id for detail activity
			markerOptions.snippet(parkAndRide.getId());
			mMap.addMarker(markerOptions);
			// set onclick listener
			mMap.setOnInfoWindowClickListener(this);
		}

	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		Intent intent = new Intent(this, DetailActivity.class);
		Bundle b = new Bundle();
		//set id for detail
		b.putString("id", marker.getSnippet()); 
		intent.putExtras(b);
		startActivity(intent);
	}

}
