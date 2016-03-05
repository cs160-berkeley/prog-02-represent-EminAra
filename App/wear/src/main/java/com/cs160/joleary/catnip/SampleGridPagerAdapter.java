package com.cs160.joleary.catnip;
import android.content.Intent;
/**
 * Created by eminArakelian on 3/1/16.
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;

import android.support.wearable.view.FragmentGridPagerAdapter;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SampleGridPagerAdapter extends FragmentGridPagerAdapter {

    private  Context mContext;
    private List mRows;

    private String currentZip;

    private  HashMap<String, ArrayList<congressPerson>>  dataSource  ;


    public SampleGridPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;
        dataSource_fill();


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



            congressPerson chosen_senator = dataSource.get(currentZip).get(col);


            fragment.initializeWithSenatorName(String.format("%s \n %s", chosen_senator.getName(),chosen_senator.getTitle()), R.drawable.senator_1, col, mContext, currentZip);











            return fragment;
        } else

        {
            //Load the election view

            electionView fragment = new electionView();

            if (currentZip.equals( "94704")) {
                fragment.initElection("Obama", "Romney", "Alameda", 66);
            }else

            {
                fragment.initElection("Obama", "Romney", "Los Angeles", 33);
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
            if (dataSource.get(currentZip) == null) {
                dataSource_fill();
            }

            if (dataSource.get(currentZip) != null)
            {
                return dataSource.get(currentZip).size();
            }else
            {
                return 2;
            }

        }
        else
        {
            return 1;

        }


    }


    private void dataSource_fill()

    {
        dataSource = new HashMap<String, ArrayList<congressPerson>>() ;




        ArrayList<congressPerson> senators_batch_1 = new ArrayList<congressPerson>();


        senators_batch_1.add(new congressPerson("Emin1","emin@emin.com \n www.emin.com","Senator - Republican","Let's vote California #freedom", R.drawable.senator_1,"Bill 1 \n Committee 1"));
        senators_batch_1.add(new congressPerson("Emin2", "emin@emin.com \n www.emin.com", "Senator - Republican", "Let's vote California #freedom", R.drawable.senator_1, "Bill 1 \n Committee 1"));

        dataSource.put("94704", senators_batch_1);

        ArrayList<congressPerson> senators_batch_2 = new ArrayList<congressPerson>();


        senators_batch_2.add(new congressPerson("Emin3","emin@emin.com \n www.emin.com","Senator - Republican","Let's vote California #freedom", R.drawable.senator_1,"Bill 1 \n Committee 1"));
        senators_batch_2.add(new congressPerson("Emin4","emin@emin.com \n www.emin.com","Senator - Republican","Let's vote California #freedom", R.drawable.senator_1,"Bill 1 \n Committee 1"));

        dataSource.put("94703", senators_batch_2);


    }



        }
