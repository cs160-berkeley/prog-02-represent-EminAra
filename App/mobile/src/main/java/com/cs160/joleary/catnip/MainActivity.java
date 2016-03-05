package com.cs160.joleary.catnip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Button btnUseCurrentLocation;
    private Button btnNext;
    private TextView textZipCodeCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textZipCodeCode = (TextView) findViewById(R.id.textZipCode);





        btnNext = (Button) findViewById(R.id.btnGo);



        btnUseCurrentLocation = (Button) findViewById(R.id.btnLoc);

        btnUseCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), CongressionalList.class);

                String sendMessage = "94704";
                intent.putExtra("ZIP", sendMessage);
                startActivity(intent);


                Intent sendIntent = new Intent(getApplicationContext(), PhoneToWatchService.class);
                sendIntent.putExtra("ZIP", "94704");
                startService(sendIntent);





            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (textZipCodeCode.getText().toString().length() > 4)
                {


                    Intent intent = new Intent(getApplicationContext(), CongressionalList.class);

                    String sendMessage = textZipCodeCode.getText().toString();

                    intent.putExtra("ZIP", sendMessage);

                    startActivity(intent);


                    Intent sendIntent = new Intent(getApplicationContext(), PhoneToWatchService.class);
                    sendIntent.putExtra("ZIP", sendMessage);
                    startService(sendIntent);



                }else
                {

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
}
