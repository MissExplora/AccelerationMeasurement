package com.example.android.datacollection.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private static final String LOG_TAG = "aktivita" ;
//    private Button btnStart, btnStop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnStart.setEnabled(true);
        btnStop.setEnabled(true); */
        Log.d(LOG_TAG, "ON CREATE");

        //   }

//   public void onStartClick(View view) {
        Intent serviceIntentAcc = new Intent(this, accService.class);
        startService(serviceIntentAcc);
/*        btnStop.setEnabled(true);
        btnStart.setEnabled(false);
        Log.d(LOG_TAG, "ON START CLICK");

    }

    public void onStopClick(View view) {
        stopService(new Intent(this, accService.class));
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
        Log.d( LOG_TAG, "ON STOP CLICK" );
    }
*/

    }
}
