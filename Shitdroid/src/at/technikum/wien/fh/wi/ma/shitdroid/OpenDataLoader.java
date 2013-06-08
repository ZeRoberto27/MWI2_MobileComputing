package at.technikum.wien.fh.wi.ma.shitdroid;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Lädt die Daten von wien.gv.at im JSON-Format
 * 
 * @author Robert
 */
public class OpenDataLoader extends AsyncTask<String, Void, String> {
	private static String URL = "http://data.wien.gv.at/daten/geoserver/ows?service=WFS&request=GetFeature&version=1.1.0&typeName=ogdwien:WCANLAGEOGD&srsName=EPSG:4326&outputFormat=json";

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
			HttpPost httppost = new HttpPost(URL);
			HttpGet httpget = new HttpGet(URL);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(MD, "Error in http connection " + e.toString(), e);
			return null;
		}

		// convert response to string
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();

			Log.d(MD, "Gelesene Onlinedaten: " + result);

		} catch (Exception e) {
			Log.e(MD, "Error converting result " + e.toString(), e);
			return null;
		}

		return "YES";
	}
}
