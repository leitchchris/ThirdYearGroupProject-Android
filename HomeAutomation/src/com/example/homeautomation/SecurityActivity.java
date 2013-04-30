package com.example.homeautomation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SecurityActivity extends ListActivity {
	NetworkInfo network = new NetworkInfo();
	// All static variables
	final String URL = network.getUrl();
	// XML node keys
	final String KEY_ITEM = "person"; // parent node
	final String KEY_FNAME = "firstName";
	final String KEY_SNAME = "lastName";
	final String KEY_IMAGE = "imageLocation";
	static final String KEY_ID = "id";

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.security_layout);

		new DownloadXml().execute(URL);	

		// selecting single ListView item
		ListView lv = getListView();

		// listening to single listitem click
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// getting values from selected ListItem
				final String id = ((TextView) arg1.findViewById(R.id.id)).getText().toString();
				//set up connection handler
				final ConnectionHandler connection = new ConnectionHandler();
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(SecurityActivity.this);
				// Setting Dialog Title
				alertDialog.setTitle("Edit User");
				// Setting Dialog Message
				alertDialog.setMessage("What would you like to do?");
				// Setting Icon to Dialog
				alertDialog.setIcon(R.drawable.app_icon);
				// Setting Positive "Yes" Button
				alertDialog.setPositiveButton("Delete Person", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// User pressed Delete User button.
						connection.execute("D:Delete-Person-" + id);
					}
				});
				// Setting Allow User Button
				alertDialog.setNegativeButton("Allow Person", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// User pressed Allow user button. Write Logic Here
						connection.execute("D:Allow-Person-" + id);
					}
				});
				// Setting Disallow Person Button
				alertDialog.setNeutralButton("Disallow Person", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// User pressed Disallow Person button
						connection.execute("D:Disallow-Person-" + id);
					}
				});
				// Showing Alert Message
				alertDialog.show();
				return false;
			}
		});
	}
	public void onClickAddPerson(View v)
	{
		Intent StartAddPersonActivity = new Intent(SecurityActivity.this, AddPerson.class);
		startActivityForResult(StartAddPersonActivity, 0);

	}
	private class DownloadXml extends AsyncTask<String, String, String> {
		// The following code was taken and modified from 
		// http://www.androidhive.info/2011/11/android-xml-parsing-tutorial/
		@Override
		protected String doInBackground(String... params) {
			String xml = null;
			try {
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(params[0]);

				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				xml = EntityUtils.toString(httpEntity);

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// return XML
			return xml;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();
			XMLParser parser = new XMLParser();
			String xml = result; // getting XML   
			Document doc = parser.getDomElement(xml); // getting DOM element
			NodeList nl = doc.getElementsByTagName(KEY_ITEM);
			// looping through all item nodes <item>
			for (int i = 0; i < nl.getLength(); i++) {
				// creating new HashMap
				HashMap<String, String> map = new HashMap<String, String>();
				Node personNode = nl.item(i);
				// adding each child node to HashMap key => value
				map.put(KEY_ID, parser.getNodeValueByTagName(personNode, KEY_ID));
				map.put(KEY_FNAME, " " + parser.getNodeValueByTagName(personNode, KEY_FNAME));
				map.put(KEY_SNAME, " " + parser.getNodeValueByTagName(personNode, KEY_SNAME));
				map.put(KEY_IMAGE, parser.getNodeValueByTagName(personNode, KEY_IMAGE));
				// adding HashList to ArrayList
				menuItems.add(map);
			}
			// Adding menuItems to ListView
			ListAdapter adapter = new SimpleAdapter(SecurityActivity.this, menuItems,
					R.layout.access_list_item,
					new String[] { KEY_ID, KEY_FNAME, KEY_SNAME}, new int[] {R.id.id,
					R.id.firstname, R.id.lastname });
			setListAdapter(adapter);
		}
	}
}