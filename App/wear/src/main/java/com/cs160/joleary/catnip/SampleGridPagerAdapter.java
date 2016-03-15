package com.cs160.joleary.catnip;
import android.content.Intent;
/**
 * Created by eminArakelian on 3/1/16.
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;

import android.support.wearable.view.FragmentGridPagerAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SampleGridPagerAdapter extends FragmentGridPagerAdapter {

    private  Context mContext;
    private List mRows;

    private String currentZip;

    private  HashMap<String, ArrayList<congressPerson>>  dataSource  ;


    private JSONObject jsonData;

    public void setJsonData(JSONObject jsonData) {
        this.jsonData = jsonData;
    }

    public SampleGridPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;



    }

    static final int[] BG_IMAGES = new int[] {

};


private static class Page {
    // static resources
    int titleRes;
    int textRes;
    int iconRes;

}


    public void setCurrentZip(String currentZip) {
        this.currentZip = currentZip;
    }

    // Create a static set of pages in a 2D array
private final Page[][] PAGES = {  };


    @Override
    public Fragment getFragment(int row, int col) {


        if (row == 0) {
            senatorView fragment = new senatorView();


            JSONObject chosen_senator = null;
            try {
                chosen_senator = jsonData.getJSONArray("people").getJSONObject(col);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            Boolean showPrev = !(col == 0);
            Boolean showNext = false;

            try {
                 showNext = !(col == jsonData.getJSONArray("people").length()-1);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                fragment.initializeWithSenatorName(chosen_senator.getString("name"), R.drawable.senator_1, col, mContext, currentZip,showPrev,showNext,chosen_senator.getString("party"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return fragment;
        } else

        {
            //Load the election view

            electionView fragment = new electionView();

            try {
                fragment.initElection("Obama", "Romney", jsonData.getString("zip"), jsonData.getJSONObject("votes").getInt("obama"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return fragment;


        }

    }


    @Override
    public int getRowCount() {
        return 2;

    }

    // Obtain the number of pages (horizontal)
    @Override
    public int getColumnCount(int rowNum) {

        if (rowNum == 0)
        {



                try {
                    return jsonData.getJSONArray("people").length();
                } catch (JSONException e) {
                    e.printStackTrace();
                    return 0;
                }



        }
        else
        {
            return 1;

        }


    }






        }
