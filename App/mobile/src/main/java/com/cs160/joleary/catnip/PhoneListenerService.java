package com.cs160.joleary.catnip;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;


public class PhoneListenerService extends WearableListenerService {


private static final String TOAST = "/send_toast";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        if( messageEvent.getPath().equalsIgnoreCase(TOAST) ) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);


            final Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;

            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            intent.setAction("com.example.Broadcast");
            intent.putExtra("POS", Integer.parseInt(value));
            sendBroadcast(intent);

        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}
