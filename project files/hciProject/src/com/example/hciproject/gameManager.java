package com.example.hciproject;

import java.io.IOException;
import java.net.SocketException;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class gameManager extends MainActivity implements OnClickListener{
	
	private ImageButton up1;
	private ImageButton up2;
	private ImageButton down1;
	private ImageButton down2;
	private ImageButton left1;
	private ImageButton left2;
	private ImageButton right1;
	private ImageButton right2;
	private Button homeGame;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.game);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		/*
		 * buttons from tv.xml
		 * */
		System.err.println("Created new activity");
		up1 = (ImageButton) findViewById(R.id.buttong5);
		up2 = (ImageButton) findViewById(R.id.buttong4);
		down1 = (ImageButton) findViewById(R.id.buttong6);
		down2 = (ImageButton) findViewById(R.id.buttong1);
		left1 = (ImageButton) findViewById(R.id.buttong3);
		left2 = (ImageButton) findViewById(R.id.buttong8);
		right1 = (ImageButton) findViewById(R.id.buttong2);
		right2 = (ImageButton) findViewById(R.id.buttong7);
		
		up1.setOnClickListener(this);
		up2.setOnClickListener(this);
		down1.setOnClickListener(this);
		down2.setOnClickListener(this);
		left1.setOnClickListener(this);
		left2.setOnClickListener(this);
		right1.setOnClickListener(this);
		right2.setOnClickListener(this);
		
		
		
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

	private void exeUp() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("1 up");
	}
	
	private void exeUpL() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("18 up");
	}

	private void exeDown() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("3 down");
	}
	
	private void exeDownL() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("19 down");
	}

	private void exeLeft() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("6 left");
	}
	
	private void exeLeftL() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("16 left");
	}

	private void exeRight() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("7 right");
	}
	private void exeRightL() throws IOException {
		// TODO Auto-generated method stub
		myClient.sendMessages("17 right");
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == up1) {
			System.out.println("HELLOOOO");
			try {
				exeUpL();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(v == down1) {
			try {
				exeDownL();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(v == left1) {
			try {
				exeLeftL();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(v == right1) {
			try {
				exeRightL();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(v == up2) {
			System.out.println("HELLOOOO");
			try {

				exeUp();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(v == down2) {
			try {
				exeDown();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(v == left2) {
			try {
				exeLeft();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(v == right2) {
			try {
				exeRight();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(v == homeGame){
			setContentView(R.layout.mainui);
		}


	}
	
}