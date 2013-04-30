package com.example.homeautomation;

import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificationKnown extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_known);
		//get details
		Bundle b = this.getIntent().getExtras();
		String[] details=b.getStringArray("DETAILS");
		//set first name
		TextView firstName = (TextView) findViewById(R.id.textViewFirstName);
		firstName.setText(details[1]);
		//set second name
		TextView secondName = (TextView) findViewById(R.id.textViewSecondName);
		secondName.setText(details[2]);
		
		new DownloadImageTask((ImageView) findViewById(R.id.imageView1)).execute(details[3]);
		}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage){
			this.bmImage = bmImage;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			String url = params[0];
			Bitmap bm = null;
			try{
				InputStream in = new java.net.URL(url).openStream();
				bm = BitmapFactory.decodeStream(in);
				return bm;
			}
			catch(Exception e){
				Log.e("Error", e.getMessage());
				e.printStackTrace();
				return null;
			}
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}
}

