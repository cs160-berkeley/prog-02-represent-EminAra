package com.cs160.joleary.catnip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.support.wearable.view.GridViewPager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity {

    private TextView mTextView;
    private Button mFeedBtn;

    private HashMap<String, ArrayList<congressPerson>> dataSource  ;


    private String currentZip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            currentZip = extras.getString("ZIP");

            JSONObject data_ = null;
            try {
                data_ = new JSONObject(currentZip);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);

            SampleGridPagerAdapter adapter_ = new SampleGridPagerAdapter(this, getFragmentManager());

            adapter_.setJsonData(data_);

            pager.setAdapter(adapter_);
            TextView txtShake = (TextView) this.findViewById(R.id.lblShake);
            txtShake.setText("");
        }

           /* do this in onCreate */
        mSensorManager = (SensorManager) getSystemService(getApplicationContext().SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

    }

    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;

            if (mAccel > 12 ) {


                Intent sendIntent = new Intent(getApplicationContext(), WatchToPhoneService.class);
                sendIntent.putExtra("POS", -1);

                getApplicationContext().startService(sendIntent);
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

}
