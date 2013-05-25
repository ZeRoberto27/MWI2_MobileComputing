package at.wien.technikum.fh.parkandride;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import at.wien.technikum.fh.parkandride.domain.ParkAndRide;
import at.wien.technikum.fh.parkandride.service.api.ParkAndRideService;
import at.wien.technikum.fh.parkandride.service.impl.ParkAndRideServiceImpl;

public class DetailActivity extends Activity implements OnClickListener {

	private ParkAndRideService dbService;
	private ParkAndRide parkAndRide;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		// set the db service
		dbService = new ParkAndRideServiceImpl(this);

		// get the id
		Bundle b = getIntent().getExtras();
		String id = b.getString("id");

		prepareDetails(id);
	}

	private void prepareDetails(String id) {
		// fetch details by id
		parkAndRide = dbService.getParkAndRideById(id);

		// set name
		TextView t = (TextView) findViewById(R.id.name_value);
		t.setText(parkAndRide.getGarageName());
		// set district
		t = (TextView) findViewById(R.id.district_value);
		t.setText(parkAndRide.getDisctrict());
		// set operator
		t = (TextView) findViewById(R.id.operator_value);
		t.setText(parkAndRide.getGarageOperator());
		// set address
		t = (TextView) findViewById(R.id.address_value);
		t.setText(parkAndRide.getAddress());
		// set homepage
		t = (TextView) findViewById(R.id.homepage_value);
		t.setText(parkAndRide.getHomepage());

		Button button = (Button) findViewById(R.id.navigate);
		button.setOnClickListener(this);

		// set button link

	}

	@Override
	public void onClick(View arg0) {
		//start nagivagtion
		Intent intent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("google.navigation:q="
						+ parkAndRide.getCoordinates().getLatitude() + ","
						+ parkAndRide.getCoordinates().getLongitude()));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);

	}

}
