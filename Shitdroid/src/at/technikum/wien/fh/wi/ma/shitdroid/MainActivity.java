package at.technikum.wien.fh.wi.ma.shitdroid;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;


public class MainActivity extends Activity {
	
	private GoogleMap mMap;
	private at.technikum.wien.fh.wi.ma.shitdroid.GPS.GPSTracker gpsTracker;
	
	private double longitude;
	private double latitude;
		
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// mMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		setUpMapIfNeeded();

		OpenDataLoader loader = new OpenDataLoader();
		loader.execute("");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/* zum Positionieren */
	private void setUpMapIfNeeded() {
		gpsTracker = new at.technikum.wien.fh.wi.ma.shitdroid.GPS.GPSTracker(this);
		if (gpsTracker.canGetLocation()) {
			longitude = gpsTracker.gpsCoordinate.getLongitude();
			latitude = gpsTracker.gpsCoordinate.getLatitude();
		} else {
			gpsTracker.showSettingsAlert();
		}
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((MapFragment) getFragmentManager()
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

}
