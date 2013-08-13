package com.example.hciproject;

import java.io.IOException;
import java.net.SocketException;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class videoManager extends MainActivity implements OnClickListener{
	
	private ImageButton addVolume;
	private ImageButton decreaseVolume;
	private ImageButton startPause;
	private ImageButton forwardPlay;
	private ImageButton backwardPlay;
	private ImageButton increaseLight;
	private ImageButton decreaseLight;
	private SensorManager sensorManager;
	private SensorEventListener videoListener;
	private int changeCount;
	private Switch swit;
	private Sensor sensor;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.tv);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		/*
		 * buttons from tv.xml
		 * */
		addVolume = (ImageButton) findViewById(R.id.volume_up);
		decreaseVolume = (ImageButton) findViewById(R.id.volume_down);
		startPause = (ImageButton) findViewById(R.id.start);
		forwardPlay = (ImageButton) findViewById(R.id.fastForward);
		backwardPlay = (ImageButton) findViewById(R.id.fastBackward);
		increaseLight = (ImageButton) findViewById(R.id.lightUp);
		decreaseLight = (ImageButton) findViewById(R.id.lightDown);

		
		addVolume.setOnClickListener(this);
		decreaseVolume.setOnClickListener(this);
		startPause.setOnClickListener(this);
		forwardPlay.setOnClickListener(this);
		backwardPlay.setOnClickListener(this);
		increaseLight.setOnClickListener(this);
		decreaseLight.setOnClickListener(this);
		
		
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
		changeCount = 0;
		videoListener = new SensorEventListener() {
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
						myClient.sendMessages("10 next");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					changeCount = 0;
				}
				
			}
		};
		
		swit = (Switch)findViewById(R.id.switch_tv);
		swit.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
				if (isChecked) {
					sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
					sensorManager.registerListener(videoListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
				} else {
					sensorManager.unregisterListener(videoListener, sensor);
				}
			}
		});


	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			try {
				myClient.sendMessages("10 next");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} 
		else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			try {
				myClient.sendMessages("11 previous");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	
	private void exeAddVolume() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("1 add volume");
	}

	private void exeStartPause() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("2 pause");
	}

	private void exeDecreaseVolume() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("3 decrease volume");
	}

	private void exeDecreaseLight() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("11 previous");
	}

	private void exeIncreaseLight() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("10 next");
	}

	private void exeBackwardPlay() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("6 backward");
	}

	private void exeForwardPlay() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("7 forward");
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if(v == addVolume) {
			System.out.println("HELLOOOO");
			try {

				exeAddVolume();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(v == decreaseVolume) {
			try {
				exeDecreaseVolume();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(v == startPause) {
			try {
				exeStartPause();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(v == forwardPlay) {
			try {
				exeForwardPlay();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(v == backwardPlay) {
			try {
				exeBackwardPlay();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(v == increaseLight) {
			try {
				exeIncreaseLight();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(v == decreaseLight) {
			try {
				exeDecreaseLight();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
