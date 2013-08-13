package com.example.sensor_acce;

import android.os.Bundle;
import android.app.Activity;
import android.hardware.SensorManager;    
import android.hardware.Sensor;    
import android.hardware.SensorEventListener;    
import android.hardware.SensorEvent; 

public class Sensor_acce extends Activity {

	private SensorManager sensorMgr;   
	Sensor sensor;
	private float x, y, z; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		   sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
		   sensor = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);    
	       SensorEventListener lsn = new SensorEventListener() {    
	           public void onSensorChanged(SensorEvent e) {    
	             x = e.values[SensorManager.DATA_X];       
	             y = e.values[SensorManager.DATA_Y];       
	             z = e.values[SensorManager.DATA_Z];    
	             setTitle("x="+(int)x+","+"y="+(int)y+","+"z="+(int)z);
	             
	             //todo: I will add x,y,z value range for different kinds of media control.
	          }    
	              
	           public void onAccuracyChanged(Sensor s, int accuracy) {    
	           }    
	       };      
	       sensorMgr.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_NORMAL); 
	}
}


