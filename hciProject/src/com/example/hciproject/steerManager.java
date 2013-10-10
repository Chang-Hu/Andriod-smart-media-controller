package com.example.hciproject;

import java.io.IOException;
import java.net.SocketException;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

public class steerManager extends MainActivity implements OnClickListener{

	private float x;
	private float y;
	private float z;
	private int signal_acce;

	private int orientCount;
	private int orientFlag;
	private int lastFlag;

	protected ImageButton acce;
	protected ImageButton brake;
	private Switch swit;

	private SensorManager sensorManager;
	private Sensor sensor;
	private SensorEventListener steerListener;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.steer);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 

		orientCount = 0;
		orientFlag = 0;
		lastFlag = 0;

		steerListener = new SensorEventListener() {

			public void onAccuracyChanged(Sensor sensor, int accuracy)
			{
			}

			public void onSensorChanged(SensorEvent event)
			{
				x = event.values[0];
				y = event.values[1];
				z = event.values[2];

				// send to server
				orientCount++;
				orientFlag = 0;

				if (y <= -3.5) {
					orientFlag = 1;
					if (lastFlag != -1) {
						try {
							myClient.sendMessages("31 left pressed");
							orientCount = 0;
							orientFlag = 1;
							lastFlag = -1;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				if (y >= 3.5) {
					orientFlag = 1;
					if (lastFlag != 1) {
						try {
							myClient.sendMessages("32 right pressed");
							orientCount = 0;
							lastFlag = 1;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				if (orientFlag == 0 && lastFlag != 0) {
					try {
						myClient.sendMessages("33 all released");
						lastFlag = 0;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				setTitle("AccelerometerSensor: " + x + ", " + y + ", " + z + ", " + orientCount);

			}
		};

		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		acce = (ImageButton) findViewById(R.id.button_acce);
		brake = (ImageButton) findViewById(R.id.button_brake);
		swit = (Switch)findViewById(R.id.switch_steer);

		acce.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					System.out.println("steer button pressed");
					((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.pedal2));
					try {
						myClient.sendMessages("21 accelerator pressed");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					System.out.println("steer button released");
					((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.pedal1));
					try {
						myClient.sendMessages("22 accelerator released");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return false;
			}
		});

		brake.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					System.out.println("steer button pressed");
					((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.pedal2));
					try {
						myClient.sendMessages("23 brake pressed");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					System.out.println("steer button released");
					((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.pedal1));
					try {
						myClient.sendMessages("24 brake released");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return false;
			}
		});

		swit.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
				if (isChecked) {
					sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
					sensorManager.registerListener(steerListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
				} else {
					sensorManager.unregisterListener(steerListener, sensor);
				}
			}
		});

		Intent intent = getIntent();
		String ip = intent.getStringExtra("ip");
		int port = Integer.parseInt(intent.getStringExtra("port"));
		try {
			myClient.setClient(ip, port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {

	}

}