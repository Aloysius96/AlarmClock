package com.example.mattp.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by mattp on 20/7/2017.
 */

public class ReceiveAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        //fetch strings from the intent and tells the app whether user press the on or off button
        String get_String = intent.getExtras().getString("extra");
        //tells app which value user pick from the menu spinner
        Integer ring_tone = intent.getExtras().getInt("ringtone");

        //creates a intent to the ringtone service
        Intent serv_intent = new Intent(context, Ringtone.class);
         //pass the extra string from receiver to ringtone class
        serv_intent.putExtra("extra", get_String);
        //pass the extra integer from receiver to ringtone class
        serv_intent.putExtra("ringtone", ring_tone);

        //starts the ringtone service
        context.startService(serv_intent);





    }
}
