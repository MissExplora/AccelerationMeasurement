package com.example.android.datacollection.app;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.lang.Object;




public class accService extends Service implements SensorEventListener{

    private static final String LOG_TAG = "aktivita" ;
    String root = Environment.getExternalStorageDirectory().toString();
    protected static SensorManager sensorManager;

    protected static Sensor accelerometer;

    private FileWriter writer;
    private PowerManager.WakeLock wakeLock;

    Long timestamp;
    Boolean set;

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "SERVICE - ON BIND");
        return null;
    }

    @Override
    public void onCreate(){
        // accelerometer
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);


        Toast.makeText(this, "Zapisujem . . . ", Toast.LENGTH_LONG).show();
        // register accelerometer sensor
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);


        Log.d(LOG_TAG, "SERVICE - ON CREATE");

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "funguj");
        wakeLock.acquire();
        set = false;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sensorManager.unregisterListener(this);
        Log.d(LOG_TAG, "SERVICE - ON DESTROY");
        wakeLock.release();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
        try {
            writer = new FileWriter(root + "/data_acc.csv",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(LOG_TAG, "SERVICE - ON START COMMAND");
        return START_STICKY;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double ts = 0;
        if (!set) {
            timestamp = event.timestamp;
            set = true;
        }
        else {
            ts = (event.timestamp - timestamp) * 0.000000001;
        }

        try {
            writer.write(ts + "," + event.values[0] + "," + event.values[1] + "," + event.values[2]+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}