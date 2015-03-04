package com.example.android.accelerationmeasuring.app;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.FileNotFoundException;
import java.io.IOException;


public class AccelerationActivity extends Activity implements SensorEventListener{

    Sensor accelerometer;
    SensorManager sManager;
    TextView acceleration;

    File root = android.os.Environment.getExternalStorageDirectory();
    File dir = new File(root.getAbsolutePath() + "/download");
    File file;

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private void writeToSDFile(long ts, float v0, float v1, float v2) {


        try {
            FileOutputStream f =  new FileOutputStream(file, true);
            OutputStreamWriter pw = new OutputStreamWriter(f);
            pw.append(ts + " " + v0 + " " + v1 + " " + v2 + " ");
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceleration);
        if (isExternalStorageWritable()) {
            file = new File(dir, "myData.txt");
            dir.mkdirs();
        }


        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        sManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        acceleration = (TextView) findViewById(R.id.acceleration);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    @Override
    public void onSensorChanged(SensorEvent event){


        acceleration.setText("TS: "+event.timestamp+"\nX: "+event.values[0]+"\nY: "+event.values[1]+"\nZ: "+event.values[2]);
        writeToSDFile(event.timestamp, event.values[0], event.values[1], event.values[2]);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_acceleration, container, false);
            return rootView;
        }
    }
}
