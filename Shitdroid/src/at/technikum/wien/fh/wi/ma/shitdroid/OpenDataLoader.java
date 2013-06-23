package at.technikum.wien.fh.wi.ma.shitdroid;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashSet;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import at.technikum.wien.fh.wi.ma.shitdroid.entity.WcEntity;
import at.technikum.wien.fh.wi.ma.shitdroid.service.WcService;

/**
 * Lädt die Daten von wien.gv.at im JSON-Format
 * 
 * @author Robert
 */
public class OpenDataLoader extends AsyncTask<String, Void, String> {
	private static String URL = "http://data.wien.gv.at/daten/geoserver/ows?service=WFS&request=GetFeature&version=1.1.0&typeName=ogdwien:WCANLAGEOGD&srsName=EPSG:4326&outputFormat=json";
	MainActivity mainAct = null;
	private WcService service;

	public OpenDataLoader(MainActivity activity) {
		mainAct = activity;
		service = new WcService(activity);
	}

	@Override
	protected String doInBackground(String... arg0) {
		String MD = "doInBackground";
		Log.d(MD, "start Method");

		// initialize
		InputStream is = null;
		String result = "";
		// JSONObject jArray = null;

		// http post
		try {
			HttpClient httpclient = new DefaultHttpClient();
			// HttpPost httppost = new HttpPost(URL);
			HttpGet httpget = new HttpGet(URL);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			// is = entity.getContent();
			if (response.getEntity() != null) {
				return EntityUtils.toString(entity, HTTP.UTF_8);
			}

			throw new HttpException("Keine Daten aus dem Internet");

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(MD, "Error in http connection " + e.toString(), e);
			return null;
		}

		// // convert response to string
		// try {
		// BufferedReader br = new BufferedReader(new InputStreamReader(is,
		// "iso-8859-1"), 8);
		// StringBuilder sb = new StringBuilder();
		// String line = null;
		// while ((line = br.readLine()) != null) {
		// sb.append(line + "\n");
		// }
		// is.close();
		// result = sb.toString();
		//
		// Log.d(MD, "Gelesene Onlinedaten: " + result);
		//
		// } catch (Exception e) {
		// Log.e(MD, "Error converting result " + e.toString(), e);
		// return null;
		// }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		String MD = "onPostExecute";
		Log.d(MD, "start Method");

		if (result != null) {
			Collection<WcEntity> wcs = null;
			try {
				JSONObject jsonObject = new JSONObject(result);
				wcs = WcEntity.valueOf(jsonObject);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			HashSet<WcEntity> wcsFromWeb = new HashSet<WcEntity>(wcs);
			HashSet<WcEntity> wcsFromDB = new HashSet<WcEntity>(
					service.getWCs());

			if (!wcsFromDB.equals(wcsFromWeb)) {
				service.saveWcs(wcs);
			}
		}

		// Der MainActivity mitteilen, dass die Daten aktualisiert wurden
		mainAct.loadCompleted();
	}
}
