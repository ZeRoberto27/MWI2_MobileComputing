package at.technikum.wien.fh.wi.ma.shitdroid;

import java.util.Collection;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import at.technikum.wien.fh.wi.ma.shitdroid.GPS.GPSTracker;
import at.technikum.wien.fh.wi.ma.shitdroid.entity.WcEntity;
import at.technikum.wien.fh.wi.ma.shitdroid.service.IWcService;
import at.technikum.wien.fh.wi.ma.shitdroid.service.WcService;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity implements OnInfoWindowClickListener {

	private GoogleMap mMap;
	private GPSTracker gpsTracker;
	private IWcService wcService;

	private double longitude;
	private double latitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// mMap =
		// ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
		setUpMapIfNeeded();

		OpenDataLoader loader = new OpenDataLoader(this);
		Toast.makeText(
				this,
				"WC-Anlagen werden geladen und in der Datenbank abgespeichert!",
				Toast.LENGTH_SHORT).show();
		loader.execute("");

		wcService = new WcService(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/* zum Positionieren */
	private void setUpMapIfNeeded() {
		gpsTracker = new GPSTracker(this);
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
			mMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				setUpMap();
			}
		}
	}

	protected void setUpMap() {
		mMap.setMyLocationEnabled(true);
		// 2.0 and 21.0 where 2.0 is maximum zoom out and 21.0 is maximum zoom
		// in
		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
				latitude, longitude), 12.0f));
	}

	public void loadCompleted() {
		// fetch park and rides from Db
		Toast.makeText(this, "Daten in der DB aktualisiert - werden geladen",
				Toast.LENGTH_SHORT).show();
		Collection<WcEntity> wcs = wcService.getWCs();
		Toast.makeText(this, "Daten aus der DB geladen: " + wcs.size(),
				Toast.LENGTH_SHORT).show();
		// Die WCs auf die MAP zeichnen
		wcsAufDerMapEinzeichnen(wcs);
	}

	private void wcsAufDerMapEinzeichnen(Collection<WcEntity> wcs) {
		Toast.makeText(this, "WCs werden eingezeichnet", Toast.LENGTH_SHORT)
				.show();
		for (WcEntity wc : wcs) {
			MarkerOptions markerOptions = new MarkerOptions();
			markerOptions.title(wc.getStrasse());
			markerOptions.position(new LatLng(wc.getBreitengrad(), wc
					.getLaengengrad()));
			markerOptions.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.ic_launcher));
			// set the id for detail activity
			markerOptions.snippet(wc.getStandortId());
			mMap.addMarker(markerOptions);
			// set onclick listener
			mMap.setOnInfoWindowClickListener(this);
		}

	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		Log.d("onInfoWindowClick", "Method started");
	}
}
