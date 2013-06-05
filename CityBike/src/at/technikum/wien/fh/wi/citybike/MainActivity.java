package at.technikum.wien.fh.wi.citybike;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// set name
		TextView t = (TextView) findViewById(R.id.txtVorname);
		t.setText("Robert");
		
		Button okay = (Button) findViewById(R.id.cmdOkay);
		okay.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	// my Comment / Schrolli

	@Override
	public void onClick(View v) {
		Log.d("onClick", "ID: " + v.getId());
		TextView nachname = (TextView) findViewById(R.id.txtNachname);
		TextView vorname = (TextView) findViewById(R.id.txtVorname);
		vorname.setText(nachname.getText());
		Log.d("onClick", "vorname: " + vorname.getText());
	}

	public int doNothing( int x)
	{
		int a;
		int b;
		int c;
		
		a = 1;
		b = 2;
		c = a+b;
		
		return c;
	}
}
