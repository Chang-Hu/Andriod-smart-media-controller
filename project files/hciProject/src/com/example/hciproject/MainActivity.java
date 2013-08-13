package com.example.hciproject;

import java.io.IOException;
import java.net.SocketException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends Activity implements OnClickListener {
	/** Properties **/		
	private ImageButton scanButton;
	private ImageButton videoButton;
	private ImageButton pptButton;
	private ImageButton gameButton;
	private ImageButton steerButton;
	private Button connectButton;
	private EditText IPText;
	private String ip;
	private int port;
	
	public static MobileClient myClient;
	private videoManager myVideo;
	private pptManager myPPT;
	private gameManager myGame;
	private steerManager mySteer;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.mainui);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		/*
		 * buttons from mainUI.xml
		 * */
		scanButton = (ImageButton) findViewById(R.id.button1_scan);
		videoButton = (ImageButton) findViewById(R.id.button4_video);
		pptButton = (ImageButton) findViewById(R.id.button3_ppt);
		gameButton = (ImageButton) findViewById(R.id.button5_game);
		steerButton = (ImageButton) findViewById(R.id.button2_steer);
		connectButton = (Button) findViewById(R.id.button0_connect);		
		
		scanButton.setOnClickListener(this);
		videoButton.setOnClickListener(this);
		pptButton.setOnClickListener(this);
		gameButton.setOnClickListener(this);
		steerButton.setOnClickListener(this);
		connectButton.setOnClickListener(this);		

		myClient = new MobileClient();
		try {
			myClient.setClient("127.0.0.1", 8888);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/** Interface Implementations **/
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */

	public void onClick(View v) {		
		
		if(v == connectButton) {
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
			ip = data[0].trim();
			port = Integer.parseInt(data[1]);
			try {
				myClient.setClient(ip, port);
				System.out.println("ip:  " + ip);
				System.out.println("port:  " + port);
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("OK");
			System.out.println("TEST");
		}
		
		if(v == scanButton) {
			
			Log.d("test", "button works!");
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
		}
		
		if( v == videoButton) {

			Intent i = new Intent();
			i.putExtra("ip", ip);
			String temp = "" + port;
			i.putExtra("port", temp);
			i.setClass(MainActivity.this, videoManager.class);
			startActivity(i);
		}
		
		if( v == pptButton) {
			Intent i = new Intent();
			i.putExtra("ip", ip);
			String temp = "" + port;
			i.putExtra("port", temp);
			i.setClass(MainActivity.this, pptManager.class);
			startActivity(i);
		}
		
		if ( v == steerButton) {
			Intent i = new Intent();
			i.putExtra("ip", ip);
			String temp = "" + port;
			i.putExtra("port", temp);
			i.setClass(MainActivity.this, steerManager.class);
			startActivity(i);
		}
		
		if( v == gameButton) {
			Intent i = new Intent();
			i.putExtra("ip", ip);
			String temp = "" + port;
			i.putExtra("port", temp);
			i.setClass(MainActivity.this, gameManager.class);
			startActivity(i);
		}
		
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) 
	{
	    if (requestCode == 0) 
	    {
	        if (resultCode == RESULT_OK)
	        {
	        	IPText= (EditText)findViewById(R.id.IPText);
	            String contents = intent.getStringExtra("SCAN_RESULT");
	            String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
	            Log.i("xZing", "contents: "+contents+" format: "+format);
	            IPText.setText(contents);
	            String msgText = contents;
				if (msgText.length() == 0) {
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setMessage("Please Type in IP Address:Port")
					.setTitle("IP Error");
					AlertDialog dialog = builder.create();
					dialog.show();
					return;
				}
				String[] data = msgText.split(":");
				ip = data[0].trim();
				port = Integer.parseInt(data[1]);
				try {
					myClient.setClient(ip, port);
					System.out.println("ip:  " + ip);
					System.out.println("port:  " + port);
				} catch (SocketException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("OK");
				System.out.println("TEST");
	        } 
	        else if (resultCode == RESULT_CANCELED)
	        {
	            // Handle cancel
	            Log.i("xZing", "Cancelled");
	        }
	    }
	}

}
