package at.technikum.wien.fh.wi.ma.shitdroid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import at.technikum.wien.fh.wi.ma.shitdroid.entity.WcEntity;
import at.technikum.wien.fh.wi.ma.shitdroid.service.IWcService;
import at.technikum.wien.fh.wi.ma.shitdroid.service.WcService;

public class WcDetailActivity extends Activity implements OnClickListener {

	private IWcService wcService;
	private WcEntity wc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wc_detail);
		wcService = new WcService(this);

		// Uebergebene ID aus Intent holen
		Bundle b = getIntent().getExtras();
		String id = b.getString("standortid");
		prepareDetails(id);

		Button button = (Button) findViewById(R.id.cmdNavigate);
		button.setOnClickListener(this);
	}

	private void prepareDetails(String id) {
		// WC ueber die ID lesen
		wc = wcService.getWcById(id);

		// set name
		TextView t = (TextView) findViewById(R.id.bezirk);
		t.setText(wc.getBezirk());

		t = (TextView) findViewById(R.id.bezirk);
		t.setText(wc.getBezirk());

		t = (TextView) findViewById(R.id.strasse);
		t.setText(wc.getStrasse());

		t = (TextView) findViewById(R.id.onr);
		t.setText(wc.getOrientierungsNr());

		t = (TextView) findViewById(R.id.telefon);
		t.setText(wc.getTelefon());

		t = (TextView) findViewById(R.id.zeit);
		t.setText(wc.getOeffnungszeit());

		t = (TextView) findViewById(R.id.info);
		t.setText(wc.getInformation());

		t = (TextView) findViewById(R.id.abt);
		t.setText(wc.getAbteilung());

		t = (TextView) findViewById(R.id.kategorie);
		t.setText(wc.getKategorie());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wc_detail, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("google.navigation:q=" + wc.getBreitengrad() + ","
						+ wc.getLaengengrad()+"&mode=walking"));
		// mode=walking schaltet auf Fuﬂg‰ngernavigation
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

}
