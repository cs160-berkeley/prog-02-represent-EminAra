package com.cs160.joleary.catnip;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class detailedView extends Activity {

    private HashMap<String, ArrayList<congressPerson>> dataSource;

    private String chosenZip;
    private int chosenPosition;
    private congressPerson chosenSenate;


    JSONObject representativeDetails;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view_unique);


        Intent myIntent = getIntent();


        try {
            representativeDetails =  new JSONObject(myIntent.getStringExtra("DETAILS"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView txtName = (TextView) this.findViewById(R.id.textSenatorName);

        try {
            txtName.setText(representativeDetails.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }



        TextView txtTitle = (TextView) this.findViewById(R.id.txtSenatorTitle);


        String partyStr = "";


        try {
            partyStr = representativeDetails.getString("party");
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
            typeStr = representativeDetails.getString("type");
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
            txtTitle.setText(partyStr + " " + typeStr+" - " +representativeDetails.getString("state") + "\n" + "End date of term: " + representativeDetails.getString("term_end"));
        } catch (JSONException e) {
            e.printStackTrace();
        }



        ImageView imgView = (ImageView) this.findViewById(R.id.imgSenator);
        try {
            Picasso.with(this).load(representativeDetails.getString("picture")).fit().centerCrop().into(imgView);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String billsAndCommittees = "No Bills / Committees details found";


        try {

            if (representativeDetails.getJSONArray("commities").length() > 0)
            {
                billsAndCommittees = "Committees: ";

                JSONArray myCommitteesArray = representativeDetails.getJSONArray("commities");

                for (int i = 0; i < myCommitteesArray.length(); i++)
                {
                    billsAndCommittees = billsAndCommittees + "\n- " +  myCommitteesArray.getString(i) ;
                }

            }


            if (representativeDetails.getJSONArray("bills").length() > 0  )
            {

                billsAndCommittees = billsAndCommittees + "\n\nBills: ";

                JSONArray myBillsArray = representativeDetails.getJSONArray("bills");

                for (int i = 0; i < myBillsArray.length(); i++) {
                    billsAndCommittees = billsAndCommittees + "\n- " + myBillsArray.getString(i);
                }





            }




        } catch (JSONException e) {
            e.printStackTrace();
        }


        TextView txtDetailsCommitteesAndBills = (TextView) this.findViewById(R.id.txtSenDetails);

        txtDetailsCommitteesAndBills.setText(billsAndCommittees);





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



}





