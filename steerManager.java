package com.example.hciproject;

import java.io.IOException;
import java.net.SocketException;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.pm.ActivityInfo;

public class steerManager extends Activity implements OnClickListener{
	
	protected ImageButton acce;
	protected ImageButton brake;
	protected Button homeSteer;
	protected ImageButton startButton;
	protected EditText IPText;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.steer);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		/*
		 * buttons from tv.xml
		 * */
		System.err.println("Created new activity");
		acce = (ImageButton) findViewById(R.id.button_acce);
		brake = (ImageButton) findViewById(R.id.button_brake);
		homeSteer = (Button) findViewById(R.id.button_steerhome);
		startButton = (ImageButton)findViewById(R.id.start);

		
		acce.setOnClickListener(this);
		brake.setOnClickListener(this);
		homeSteer.setOnClickListener(this);
		startButton.setOnClickListener(this);

		
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
	
	private void exeAcce() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("1 accelerate");
	}

	private void exeBrake() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("2 brake");
	}
	
	private MobileClient myClient;
	
	public steerManager(MobileClient myClient) {
		this.myClient = myClient;		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		System.out.println(1234);
		// TODO Auto-generated method stub
		if(v == startButton) {
			System.err.println("TEST111");
			IPText= (EditText)findViewById(R.id.IPText);
			String msgText =IPText.getText().toString();
			if (msgText.length() == 0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Please Type in IP Address:Port")
				       .setTitle("IP Error");
				AlertDialog dialog = builder.create();
				dialog.show();
				return;
			}
			String[] data = msgText.split(":");
			String ip = data[0].trim();
			int port = Integer.parseInt(data[1]);
			try {
				myClient.setClient(ip, port);
				System.out.println("ip:  " + ip);
				System.out.println("port:  " + port);
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				myClient.sendMessages("Connecting.....");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("TEST");
		}

		if(v == acce) {
			System.out.println("HELLOOOO");
			try {

				exeAcce();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(v == brake) {
			try {
				exeBrake();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(v == homeSteer){
			setContentView(R.layout.mainui);
		}
		
	}
	
}
