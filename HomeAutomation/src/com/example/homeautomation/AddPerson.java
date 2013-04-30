package com.example.homeautomation;

import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

public class AddPerson extends Activity {
	/** Called when the activity is first created. */

	private boolean photoTaken = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_person);

		final Button button = (Button) findViewById(R.id.buttonTakePicture);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				TakePhoto();
			}
		});
	}
	public void onClickSave (View v)
	{
		if(photoTaken == true)
		{
			//get first name
			EditText firstname = (EditText) findViewById(R.id.editTextFirstName);
			String firstName = firstname.getText().toString() + " ";

			//get last name
			EditText lastname = (EditText) findViewById(R.id.editTextLastName);
			String lastName = lastname.getText().toString();
			
			//get image url
			Urls url = new Urls();
			String imageUrl = url.getUrl();
			
			//get allowed or not
			Switch switchAllowed = (Switch) findViewById(R.id.switch1);
			String allowed = Boolean.valueOf(switchAllowed.isChecked()).toString();

			//send
			ConnectionHandler connection = new ConnectionHandler();
			connection.execute("D:savePerson-" + firstName + "-" + lastName+ "-" + imageUrl + "-" + allowed);
		}
		else{
			Context context = getApplicationContext();
			CharSequence text = "Please take a photo before saving";
			int duration = Toast.LENGTH_LONG;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}

	private void TakePhoto ()
	{
		String murl;
		// setup connection
		ConnectionHandler connection = new ConnectionHandler();
		//send Take Photo message
		connection.execute("D:takePic");
		Urls url = new Urls();
		murl = url.getUrl();
		//show the image
		new DownloadImageTask((ImageView) findViewById(R.id.mImageView)).execute(murl);
		photoTaken = true;

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
