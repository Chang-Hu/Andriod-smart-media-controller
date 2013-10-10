package com.example.hciproject;

import java.io.IOException;
import java.net.SocketException;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class pptManager extends MainActivity implements OnClickListener{
	
	private ImageButton nextPage;
	private ImageButton previousPage;
	private Button escape;
	private Button project;
	private Switch swit;
	private SensorManager sensorManager;
	private Sensor sensor;
	private SensorEventListener pptListener;
	private int changeCount;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.ppt);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		
		System.err.println("Created new activity");
		nextPage = (ImageButton) findViewById(R.id.button_next);
		previousPage = (ImageButton) findViewById(R.id.button_previous);
		escape = (Button) findViewById(R.id.button_esc);
		project = (Button) findViewById(R.id.button_project);
		
		nextPage.setOnClickListener(this);
		previousPage.setOnClickListener(this);
		escape.setOnClickListener(this);
		project.setOnClickListener(this);
		
		changeCount = 0;
		
		Intent intent = getIntent();
		String ip = intent.getStringExtra("ip");
		int port = Integer.parseInt(intent.getStringExtra("port"));
		try {
			myClient.setClient(ip, port);
		} catch (SocketException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		
		pptListener = new SensorEventListener() {
			public void onAccuracyChanged(Sensor sensor, int accuracy)
			{
			}

			public void onSensorChanged(SensorEvent event)
			{
				float x = event.values[0];
				float y = event.values[1];
				float z = event.values[2];
				changeCount++;
				if ((Math.abs(x) > 15 || Math.abs(y) > 15 || Math.abs(z) > 15) && changeCount >= 5) {
					try {
						myClient.sendMessages("7 ppt next");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					changeCount = 0;
				}
				
			}
		};

		swit = (Switch)findViewById(R.id.switch_ppt);
		swit.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
				if (isChecked) {
					sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
					sensorManager.registerListener(pptListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
				} else {
					sensorManager.unregisterListener(pptListener, sensor);
				}
			}
		});
		
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			try {
				myClient.sendMessages("7 ppt next");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} 
		else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			try {
				myClient.sendMessages("6 ppt previous");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void exeNextPage() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("7 next page");
	}

	private void exePreviousPage() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("6 previous page");
	}

	private void exeEscape() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("8 escape");
	}

	private void exeProject() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("9 project");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == nextPage) {
			try {
				exeNextPage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(v == previousPage) {
			try {
				exePreviousPage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(v == escape) {
			try {
				exeEscape();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(v == project) {
			try {
				exeProject();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
}
