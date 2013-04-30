package com.example.homeautomation;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		// retreive current values from preferences
		((EditText) findViewById(R.id.editTextIp)).setText(sp.getString("IP", "192.168.0.2"));
		((EditText) findViewById(R.id.editTextPort)).setText(""+sp.getInt("PORT", 2001));

		final Button button = (Button) findViewById(R.id.buttonSaveSettings);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// get values
				EditText Ip = (EditText) findViewById(R.id.editTextIp);
				String ip = Ip.getText().toString();
				EditText Port = (EditText) findViewById(R.id.editTextPort);
				int port = Integer.parseInt(Port.getText().toString());

				// set values
				NetworkInfo network = new NetworkInfo();
				network.setIp(ip);
				network.setPort(port);
				savePrefs(ip, port);

			}
		});
	}
	private void savePrefs(String ip, int port){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		Editor edit = sp.edit();
		edit.putString("IP", ip);
		edit.putInt("PORT", port);
		edit.commit();
		finish();

	}

}
