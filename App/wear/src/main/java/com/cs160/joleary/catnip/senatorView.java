package com.cs160.joleary.catnip;

import android.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.widget.TextView;

/**
 * Created by eminArakelian on 3/1/16.
 */
public class senatorView extends Fragment {


    private View my_view;
    private String senator_name;
    private int senator_bg_id;
    private int row_i;
    private Context context_i;

    private String CurrentZip;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        my_view = inflater.inflate(R.layout.senator_item, container, false);

        Button moreInfo = (Button) my_view.findViewById(R.id.btnMoreInfo);



        moreInfo.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {



                Intent sendIntent = new Intent(context_i, WatchToPhoneService.class);
                sendIntent.putExtra("POS", row_i);

                context_i.startService(sendIntent);
            }

        });



        TextView txtSenator = (TextView) my_view.findViewById(R.id.textSenatorName);
        txtSenator.setText(senator_name);


        return my_view;

    }

    public void initializeWithSenatorName(String senator, int bg_id, int row_i_, Context context_i_, String zip_) {


        senator_bg_id = bg_id;
        senator_name = senator;
        row_i = row_i_;
        context_i = context_i_;
        CurrentZip = zip_;

    }



}
