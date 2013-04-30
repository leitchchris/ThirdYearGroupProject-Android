package com.example.homeautomation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//start listening for notification
		new Notification().execute();

		//is it the first time app is being ran if so set up preferences
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		if(sp.getBoolean("FIRST_TIME", true)){
			Editor edit = sp.edit();
			edit.putBoolean("FIRST_TIME", false);
			edit.putString("IP", "192.168.0.2");
			edit.putInt("PORT", 2001);
			edit.commit();
		}

		//get network info from preferences
		NetworkInfo network = new NetworkInfo();
		network.setIp(sp.getString("IP", "192.168.0.2"));
		network.setPort(sp.getInt("PORT", 2001));
		network.setUrl("http://192.168.0.1/groupProject/accessList.xml");

		//init send ip to server
		IPinit.SendIP();

		// on click watcher for lighting activity
		final ImageButton lightingBtn = (ImageButton) findViewById(R.id.imgBtnLighting);
		lightingBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View V){
				Intent StartLightingActivity = new Intent(MainActivity.this, LightingActivity.class);
				startActivityForResult(StartLightingActivity, 0);
			}
		});

		// on click watcher for security activity
		final ImageButton securityBtn = (ImageButton) findViewById(R.id.imgBtnSecurity);
		securityBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View V) {
				Intent StartSecurityActivity = new Intent(MainActivity.this, SecurityActivity.class);
				startActivityForResult(StartSecurityActivity, 0);
			}
		});  	
	}

	// options menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item) {
		startActivity(new Intent(this, Settings.class));
		return true;
	}
	private class Notification extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			try {
				ServerSocket server = new ServerSocket(2003);
				while(true){
					Socket socket = server.accept();
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					params[0] = in.readLine();
					in.close();
					socket.close();
					return params[0];
				}
				
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if(result.contains("D:notificationKnown")){
				Bundle b = new Bundle();
				String delims = "-";
				String[] details = result.split(delims);
				b.putStringArray("DETAILS", details);
				
				Intent StartNotificationKnown = new Intent(MainActivity.this, NotificationKnown.class);
				StartNotificationKnown.putExtras(b);
				startActivityForResult(StartNotificationKnown, 0);
				

			}
			if(result.contains("D:notificationUnknown")){
				Intent StartNotificationUnknown = new Intent(MainActivity.this, NotificationUnknown.class);
				startActivityForResult(StartNotificationUnknown, 0);
			}
			
			new Notification().execute();
			
		}
		
	}
	
}
