package com.example.homeautomation;


import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;


public class LightingActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.lighting_layout);

        // get toggle button
        Switch livingRoom = (Switch) findViewById(R.id.switchLightLivingRoom);
        // attach onCheckedChangeListener
        livingRoom.setOnCheckedChangeListener( new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton Switch,
					boolean isChecked) {
				if(isChecked){
					// setup connection
					ConnectionHandler connection = new ConnectionHandler();
					//send light on message
					connection.execute("D:L-Lights-ON");
				}
				else{
					// setup connection
					ConnectionHandler connection = new ConnectionHandler();
					//send light off message
					connection.execute("D:L-Lights-OFF");
				}
			}
        	
        });
        // get toggle button
        Switch hall = (Switch) findViewById(R.id.switchLightHall);
        // attach onCheckedChangeListener
        hall.setOnCheckedChangeListener( new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton Switch,
					boolean isChecked) {
				if(isChecked){
					// setup connection
					ConnectionHandler connection = new ConnectionHandler();
					//send light on message
					connection.execute("D:H-Lights-ON");
				}
				else{
					// setup connection
					ConnectionHandler connection = new ConnectionHandler();
					//send light off message
					connection.execute("D:H-Lights-OFF");
				}
			}
        });
        
        // get toggle button
        Switch bedroom = (Switch) findViewById(R.id.switchLightBedroom);
        // attach onCheckedChangeListener
        bedroom.setOnCheckedChangeListener( new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton Switch,
					boolean isChecked) {
				if(isChecked){
					// setup connection
					ConnectionHandler connection = new ConnectionHandler();
					//send light on message
					connection.execute("D:B-Lights-ON");
				}
				else{
					// setup connection
					ConnectionHandler connection = new ConnectionHandler();
					//send light off message
					connection.execute("D:B-Lights-OFF");
				}
			}
        });
        // get toggle button
        Switch bathroom = (Switch) findViewById(R.id.switchLightBathroom);
        // attach onCheckedChangeListener
        bathroom.setOnCheckedChangeListener( new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton Switch,
					boolean isChecked) {
				if(isChecked){
					// setup connection
					ConnectionHandler connection = new ConnectionHandler();
					//send light on message
					connection.execute("D:WC-Lights-ON");
				}
				else{
					// setup connection
					ConnectionHandler connection = new ConnectionHandler();
					//send light off message
					connection.execute("D:WC-Lights-OFF");
				}
			}
        });
        }
}
