package com.cs160.joleary.catnip;

import android.content.BroadcastReceiver;
import android.content.Context;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;


import android.widget.ImageView;
import android.widget.ListView;

import android.widget.TextView;


import android.widget.AdapterView.OnItemClickListener;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


import java.util.ArrayList;

import android.content.Intent;

import android.content.IntentFilter;



//

public class CongressionalList extends Activity {

    private List<congressPerson> mySenators = new ArrayList<congressPerson>();

    private IntentFilter mIntentFilter;
    BroadcastReceiver myReceive;

    private Map<String, ArrayList<congressPerson>> dataSource;

    private String currentZip;


    private boolean shakeHappened = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        dataSource_fill();






        Intent intent_i = getIntent();

        currentZip = intent_i.getStringExtra("ZIP");


        setContentView(R.layout.senators_list_unique);


        mySenators = dataSource.get(currentZip);

        populateListView();


        myReceive = new BroadcastReceiver(){



            @Override
            public void onReceive(Context context, Intent intent) {




                int watchPosition = intent.getIntExtra("POS", 0);



                if (watchPosition != -1) {
                    Intent intent_ = new Intent(getApplicationContext(), detailedView.class);


                    finishActivity(2);
                    int sendMessage = 1;
                    intent_.putExtra("POS", watchPosition);
                    intent_.putExtra("ZIP", currentZip);


                    startActivityForResult(intent_, 2);
                }
                else
                {


                    if (shakeHappened == false) {
                        shakeHappened = true;

                        if (currentZip.equals("94704")) {
                            currentZip = "94703";
                        } else {
                            currentZip = "94704";
                        }



                        mySenators = dataSource.get(currentZip);

                        populateListView();

                        Intent sendIntent = new Intent(getApplicationContext(), PhoneToWatchService.class);
                        sendIntent.putExtra("ZIP", currentZip);
                        startService(sendIntent);
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

                int sendMessage = position;

                intent.putExtra("POS", position);

                intent.putExtra("ZIP", currentZip);

                startActivityForResult(intent, 2);







            }
        });


    }









    private class MyListAdapter extends ArrayAdapter<congressPerson>{
        public MyListAdapter()
        {
            super(CongressionalList.this, R.layout.senator_list_item, mySenators );

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;


            if (itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.senator_list_item,parent,false);
            }


            congressPerson currentPerson = mySenators.get(position);




            TextView text_name = (TextView) itemView.findViewById(R.id.textName);
            text_name.setText(currentPerson.getName());




            ImageView imgView = (ImageView) itemView.findViewById(R.id.imgSen);
            imgView.setImageResource(currentPerson.getIconID());


            TextView text_title = (TextView) itemView.findViewById(R.id.textTitle);
            text_title.setText(currentPerson.getTitle());









            return itemView;






        }






    }




    private void dataSource_fill()

    {
        dataSource = new HashMap<String, ArrayList<congressPerson>>() ;




        ArrayList<congressPerson> senators_batch_1 = new ArrayList<congressPerson>();


        senators_batch_1.add(new congressPerson("Emin1","emin@emin.com \n www.emin.com","Senator - Republican","Let's vote California #freedom", R.drawable.rectangle_5,"Bill 1 \n Committee 1"));
        senators_batch_1.add(new congressPerson("Emin2", "emin@emin.com \n www.emin.com", "Senator - Republican", "Let's vote California #freedom", R.drawable.new_senator, "Bill 1 \n Committee 1"));

        dataSource.put("94704", senators_batch_1);

        ArrayList<congressPerson> senators_batch_2 = new ArrayList<congressPerson>();


        senators_batch_2.add(new congressPerson("Emin3","emin@emin.com \n www.emin.com","Senator - Republican","Let's vote California #freedom", R.drawable.rectangle_5,"Bill 1 \n Committee 1"));
        senators_batch_2.add(new congressPerson("Emin4","emin@emin.com \n www.emin.com","Senator - Republican","Let's vote California #freedom", R.drawable.new_senator,"Bill 1 \n Committee 1"));

        dataSource.put("94703", senators_batch_2);


    }


}
