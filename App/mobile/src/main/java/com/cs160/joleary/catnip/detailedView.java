package com.cs160.joleary.catnip;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class detailedView extends Activity {

    private HashMap<String, ArrayList<congressPerson>> dataSource;

    private String chosenZip;
    private int chosenPosition;
    private congressPerson chosenSenate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view_unique);


        Intent myIntent = getIntent();

        chosenZip = myIntent.getStringExtra("ZIP");

        chosenPosition = myIntent.getIntExtra("POS", 0);




        dataSource_fill();

        chosenSenate = dataSource.get(chosenZip).get(chosenPosition);


        TextView txtName = (TextView) this.findViewById(R.id.textSenatorName);
        txtName.setText(chosenSenate.getName());





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_view_unique, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void dataSource_fill()

    {
        dataSource = new HashMap<String, ArrayList<congressPerson>>() ;




        ArrayList<congressPerson> senators_batch_1 = new ArrayList<congressPerson>();


        senators_batch_1.add(new congressPerson("Emin1","emin@emin.com \n www.emin.com","Senator - Republican","Let's vote California #freedom", R.drawable.rectangle_5,"Bill 1 \n Committee 1"));
        senators_batch_1.add(new congressPerson("Emin2", "emin@emin.com \n www.emin.com", "Senator - Republican", "Let's vote California #freedom", R.drawable.rectangle_5, "Bill 1 \n Committee 1"));

        dataSource.put("94704", senators_batch_1);

        ArrayList<congressPerson> senators_batch_2 = new ArrayList<congressPerson>();


        senators_batch_2.add(new congressPerson("Emin3","emin@emin.com \n www.emin.com","Senator - Republican","Let's vote California #freedom", R.drawable.rectangle_5,"Bill 1 \n Committee 1"));
        senators_batch_2.add(new congressPerson("Emin4","emin@emin.com \n www.emin.com","Senator - Republican","Let's vote California #freedom", R.drawable.rectangle_5,"Bill 1 \n Committee 1"));

        dataSource.put("94703", senators_batch_2);


    }

}





