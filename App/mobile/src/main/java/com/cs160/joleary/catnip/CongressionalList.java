package com.cs160.joleary.catnip;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;


import android.widget.ImageView;
import android.widget.ListView;

import android.widget.RelativeLayout;
import android.widget.TextView;


import android.widget.AdapterView.OnItemClickListener;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


import java.util.ArrayList;

import android.content.Intent;

import android.content.IntentFilter;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;


//

public class CongressionalList extends Activity {

    private List<congressPerson> mySenators = new ArrayList<congressPerson>();

    private IntentFilter mIntentFilter;
    BroadcastReceiver myReceive;

    private ProgressDialog mySpinner;

    private JSONArray representativesList;
    private JSONObject dataFillerFromServer;

    private String currentZip;


    private boolean shakeHappened = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);








        Intent intent_i = getIntent();

        currentZip = intent_i.getStringExtra("ZIP");


        setContentView(R.layout.senators_list_unique);

        mySpinner  = new ProgressDialog(CongressionalList.this);


        callServerWithRequestDetails(false,0.0,0.0,currentZip);

//        mySenators = dataSource.get(currentZip);
//
//        populateListView();


        myReceive = new BroadcastReceiver(){



            @Override
            public void onReceive(Context context, Intent intent) {




                int watchPosition = intent.getIntExtra("POS", 0);



                if (watchPosition != -1) {
                    Intent intent_ = new Intent(getApplicationContext(), detailedView.class);


                    finishActivity(2);
                    int sendMessage = 1;
                    intent_.putExtra("POS", watchPosition);
                    try {
                        intent_.putExtra("DETAILS", representativesList.get(watchPosition).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    startActivityForResult(intent_, 2);
                }
                else
                {


                    if (shakeHappened == false) {
                        shakeHappened = true;


                        callServerWithRequestDetails(false,0.0,0.0,"99999");


                    }

                }


            }
        };

        IntentFilter mIntentFilter=new IntentFilter("com.example.Broadcast");
        registerReceiver(myReceive, mIntentFilter);

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_senators_list_unique, menu);
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



    private void populateListView() {
        ArrayAdapter<congressPerson> adapter= new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.list_senators);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


                Intent intent = new Intent(getApplicationContext(), detailedView.class);



                //here we are sending the senator details from the representatives list right away

                try {
                    intent.putExtra("DETAILS", representativesList.get(position).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                startActivityForResult(intent, 2);


            }
        });


    }









    private class MyListAdapter extends ArrayAdapter<congressPerson>{
        public MyListAdapter()
        {
            super(CongressionalList.this, R.layout.senator_list_item );

        }


        @Override
        public int getCount() {
            return representativesList.length();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;


            if (itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.senator_list_item,parent,false);
            }



            //Encapsulate the json object statement with try/catch

            JSONObject currentRep = null ;
            try {
                currentRep = (JSONObject) representativesList.get(position);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                if (currentRep.getString("party").equals("D"))
                {
                    RelativeLayout card_ = (RelativeLayout) itemView.findViewById(R.id.insidecard) ;
                    card_.setBackgroundColor(Color.parseColor("#2980b9"));
                }else
                {
                    RelativeLayout card_ = (RelativeLayout) itemView.findViewById(R.id.insidecard) ;
                    card_.setBackgroundColor(Color.parseColor("#c0392b"));


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Once we have the object we can start populating our view here
            //Adding all the design embelishments that we have in the world!!!


            TextView text_name = (TextView) itemView.findViewById(R.id.textName);
            try {
                text_name.setText(currentRep.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

//... TO BE CONTINUED





            ImageView imgView = (ImageView) itemView.findViewById(R.id.imgSen);
            try {
                Picasso.with(getContext()).load(currentRep.getString("picture")).fit().centerCrop().into(imgView);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            TextView text_title = (TextView) itemView.findViewById(R.id.textTitle);


            String partyStr = "";


            try {
                 partyStr = currentRep.getString("party");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (partyStr.equals("D"))
            {
                partyStr = "Democrat";
            }else if (partyStr.equals("R"))
            {
                partyStr = "Republican";
            }


            String typeStr = "";

            try {
                typeStr = currentRep.getString("type");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (typeStr.equals("house"))
            {
                typeStr = "Representative";
            }else
            {
                typeStr = "Senator";
            }

            try {
                text_title.setText(partyStr + " " + typeStr+"\n" +currentRep.getString("state"));
            } catch (JSONException e) {
                e.printStackTrace();
            }



            TextView text_twitter = (TextView) itemView.findViewById(R.id.textTwitter);
            try {
                text_twitter.setText("@"+currentRep.getString("twitter")+": " + currentRep.getString("last_tweet"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            TextView text_comm = (TextView) itemView.findViewById(R.id.textComm);
            try {
                text_comm.setText(currentRep.getString("email") + "\n" + currentRep.getString("website"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return itemView;






        }






    }



    public void callServerWithRequestDetails(Boolean isLocation, double lat, double longitude, String zip_ )
    {
        HttpResponse g;





        AsyncHttpClient client = new AsyncHttpClient();
        String json_;


        if (isLocation) {

            json_ = " {\n" +
                    " \t\"type\": \"geo\",\n" +
                    " \t\"data\": [" + new Double(lat).toString() +","+ new Double(longitude).toString() + "]\n" +
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



        //Here we need to enable the spinner

        mySpinner.setMessage("Loading ...");
        mySpinner.show();




        ///
        client.post(getApplicationContext(), "http://tagjr.co/represent", entity_, "application/json", new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("tag", "Login success");
                //here is the SUCCESS

                dataFillerFromServer = response;


                //here we should call the


                try {
                    processServerResponseFromDataSource();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }






            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("tag", "Login success");
                //HERE IS THE FAIL

                if (mySpinner.isShowing()) {
                    mySpinner.dismiss();
                }

                Toast.makeText(getApplicationContext(), "Unknown Location", Toast.LENGTH_SHORT).show();



            }


        });





    }


    void processServerResponseFromDataSource() throws JSONException {

        if (mySpinner.isShowing()) {
            mySpinner.dismiss();
        }


        JSONObject all_data = (JSONObject) dataFillerFromServer.get("all_data") ;

        currentZip = new Integer(all_data.getInt("zip")).toString();






        representativesList = (JSONArray)all_data.get("people");



        populateListView();


        Intent sendIntent = new Intent(getApplicationContext(), PhoneToWatchService.class);
        sendIntent.putExtra("ZIP", all_data.toString());
        startService(sendIntent);






    }








}
