package com.cs160.joleary.catnip;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class MainActivity extends Activity {

    private Button btnUseCurrentLocation;
    private Button btnNext;
    private TextView textZipCodeCode;


    private ProgressDialog loadingSpinner;

    LocationManager locationManager;
    LocationListener locationListener;

    JSONObject data_filler;


    double latitude_ = 0.0;
    double longitude_ = 0.0;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 33:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
                }
        }


    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadingSpinner = new ProgressDialog(this);

        textZipCodeCode = (TextView) findViewById(R.id.textZipCode);



        btnNext = (Button) findViewById(R.id.btnGo);



        btnUseCurrentLocation = (Button) findViewById(R.id.btnLoc);



        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                latitude_ = location.getLatitude();
                longitude_ = location.getLongitude();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.

                requestPermissions(new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION}, 33);
                return;
            }


        } else

        {


            locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
        }



        btnUseCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                //check to see if we actually have a location
                if (longitude_ != 0)
                {
                    callServer(true, longitude_, latitude_, "");
                }







            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (textZipCodeCode.getText().toString().length() > 4)
                {


                    String zipCode = textZipCodeCode.getText().toString();

                    callServer(false, 0.0, 0.0, zipCode);

//                    Intent intent = new Intent(getApplicationContext(), CongressionalList.class);
//
//                    String sendMessage = textZipCodeCode.getText().toString();
//
//                    intent.putExtra("ZIP", sendMessage);
//
//                    startActivity(intent);
//
//
//                    Intent sendIntent = new Intent(getApplicationContext(), PhoneToWatchService.class);
//                    sendIntent.putExtra("ZIP", sendMessage);
//                    startService(sendIntent);



                }else
                {

                }


            }
        });





    }



    public void callServer(Boolean isLocation, double lati, double longi, String zip_ )
    {

        AsyncHttpClient client = new AsyncHttpClient();
        String json_;


        if (isLocation) {

            json_ = " {\n" +
                    " \t\"type\": \"geo\",\n" +
                    " \t\"data\": [" + new Double(longi).toString() +","+ new Double(lati).toString() + "]\n" +
                    " }";
        }else

        {

            json_ = " {\n" +
                    " \t\"type\": \"zip\",\n" +
                    " \t\"data\": " +zip_.toString()+"\n" +
                    " }";


        }






        //ByteArrayEntity entity = new ByteArrayEntity(json_.getBytes("UTF-8"));

        StringEntity entity = null;
        try {
            entity = new StringEntity(json_);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ByteArrayEntity entity_ = null;
        try {
            entity_ = new ByteArrayEntity(json_.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }




        entity_.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));


        loadingSpinner.setMessage("Loading ...");
        loadingSpinner.show();

        client.post(getApplicationContext(), "http://tagjr.co/represent", entity_, "application/json", new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);


                data_filler = response;


                try {
                    loadIntentFromServerData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                Toast.makeText(getApplicationContext(), "Unable to detect location", Toast.LENGTH_SHORT).show();

                if (loadingSpinner.isShowing()) {
                    loadingSpinner.dismiss();
                }


            }


        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    void loadIntentFromServerData() throws JSONException {

        if (loadingSpinner.isShowing()) {
            loadingSpinner.dismiss();
        }


        JSONObject all_data = (JSONObject) data_filler.get("all_data") ;





        if (all_data.getInt("zip") > 0) {


            Intent intent = new Intent(getApplicationContext(), CongressionalList.class);

            intent.putExtra("ZIP", new Integer(all_data.getInt("zip")).toString());

            startActivity(intent);



        }








    }

}
