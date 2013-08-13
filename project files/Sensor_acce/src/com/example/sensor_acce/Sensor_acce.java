package com.example.sensor_acce;
import java.util.List;  

import android.app.Activity;  
import android.hardware.Sensor;  
import android.hardware.SensorEvent;  
import android.hardware.SensorEventListener;  
import android.hardware.SensorManager;  
import android.os.Bundle;  
  

public class Sensor_acce extends Activity {
  
    private SensorManager sensorManager;
    
   @Override
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     
      
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        
   }
   
    @Override
    protected void onResume() {
    	
       int i =0;
       if( i==0){
       Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
       sensorManager.registerListener(listener,sensor, SensorManager.SENSOR_DELAY_NORMAL);     
       }
       else {
       
       Sensor sensor1 = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
       sensorManager.registerListener(listener,sensor1, SensorManager.SENSOR_DELAY_NORMAL);     
       }
    	   
    	   super.onResume();
    }
   
   @Override
    protected void onPause() {
        sensorManager.unregisterListener(listener);
        super.onPause();
    }
   
    private SensorEventListener listener = new SensorEventListener() {       
        @Override
        public void onSensorChanged(SensorEvent event) {       
            float x = event.values[SensorManager.DATA_X];     
            float y = event.values[SensorManager.DATA_Y];     
            float z =event.values[SensorManager.DATA_Z];  
            float x1=999;
            float y1=999;
            float z1=999;
            float x2=999;
            float y2=999;
            float z2=999;
            switch(event.sensor.getType()) {
            
            case Sensor.TYPE_ORIENTATION:
            	x2 = x; y2=y;z2=z;
                break;
            case Sensor.TYPE_ACCELEROMETER:
                x1 = x; y1 =y;z1=z;
                break;
  
           
             
            }
            setTitle("AccelerometerSensor: " + x1 + ", " + y1 + ", " + z1+","+"OrientationSensor: " + x2 + ", " + y2 + ", " + z2);
        }       
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {   
        }
    };
}
 
