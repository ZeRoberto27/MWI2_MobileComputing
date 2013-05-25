package at.wien.technikum.fh.parkandride.support;

import java.net.URI;
import java.util.Collection;
import java.util.HashSet;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import at.wien.technikum.fh.parkandride.domain.ParkAndRide;
import at.wien.technikum.fh.parkandride.service.api.ParkAndRideService;
import at.wien.technikum.fh.parkandride.service.impl.ParkAndRideServiceImpl;

public class AsyncJSONHelper extends AsyncTask<String, Void, String> {

	private static final String URL = "http://data.wien.gv.at/daten/geoserver/ows?service=WFS&request=GetFeature&version=1.1.0&typeName=ogdwien:PARKANDRIDEOGD&srsName=EPSG:4326&outputFormat=json";
	private ParkAndRideService service;
	private OnLoadCompleteListener listener;

	public AsyncJSONHelper(Context context) {
		super();
		service = new ParkAndRideServiceImpl(context);
	}

	@Override
	protected String doInBackground(String... arg0) {
		try {
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
					"android");
			HttpGet request = new HttpGet();
			request.setURI(new URI(URL));
			request.setHeader("Content-Type", "text/plain; charset=utf-8");
			HttpResponse response = client.execute(request);

			if (response.getEntity() != null) {
				return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
			}

			throw new HttpException("Response is empty");

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(String result) {
		if (result != null) { //if getting data from web worked
			Collection<ParkAndRide> parkAndRides = null;
			// create json object from string
			try {
				JSONObject jsonObject = new JSONObject(result);
				parkAndRides = ParkAndRide.valueOf(jsonObject);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			// create two hashsets for comparing (order is not important!)
			HashSet<ParkAndRide> parkAndRidesFromWeb = new HashSet<ParkAndRide>(
					parkAndRides);
			HashSet<ParkAndRide> parkAndRidesFromDB = new HashSet<ParkAndRide>(
					service.getParkAndRides());

			// compare the hashsets
			if (!parkAndRidesFromDB.equals(parkAndRidesFromWeb)) {
				// save or update park and rides in DB
				service.saveOrUpdateParkAndRides(parkAndRides);
			}
		}

		// inform main activity that task is finished
		listener.loadCompleted();

	}

	public OnLoadCompleteListener getListener() {
		return listener;
	}

	public void setListener(OnLoadCompleteListener listener) {
		this.listener = listener;
	}

}
