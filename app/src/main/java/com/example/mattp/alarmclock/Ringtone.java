package com.example.mattp.alarmclock;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by mattp on 20/7/2017.
 */

public class Ringtone extends Service {

    MediaPlayer songs;
    int start_ID;
    boolean isRun;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags,int start_ID){
        Log.i("LocalService", "Receive id" + start_ID + ": " + intent);
        //to get extra string values
        String state = intent.getExtras().getString("extra");
        //get the ringtone integer values
        Integer ringtone_choice = intent.getExtras().getInt("ringtone");

        //set up the notification process
        NotificationManager notify_mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //set up intent that goes to main
        Intent intent_main = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent pending_intent_main = PendingIntent.getActivity(this,0, intent_main, 0);
         //makes the notification parameters
        Notification notify_popup = new Notification.Builder(this)
                .setContentTitle("Alarm is going off!!!!")
                .setContentIntent(pending_intent_main)
                .setSmallIcon(R.drawable.bell_icon)
                .setAutoCancel(true)
                .build();


        //converts extra string from intent and to start ID from value 0 or 1
        assert state !=null;
        if (state.equals("alarm on")) {
            start_ID = 1;

        } else if (state.equals("alarm off")) {
            start_ID = 0;


        } else {
            start_ID = 0;


        }


        //if no music is playing and user pressed set alarm, the music will play
        if(!this.isRun && start_ID == 1){
            this.isRun = true;
            this.start_ID = 0;

            //set up the command for the notification
            notify_mgr.notify(0,notify_popup);

            if(ringtone_choice == 0){
                  Log.e("pick a sound", "ringtone");
            }
            else if(ringtone_choice == 1){
                //create a instance of the media player
                songs = MediaPlayer.create(this,R.raw.buzzer);
                //starts the ringtone
                songs.start();
                songs.setLooping(true);

            }
            else if(ringtone_choice == 2){
                //create a instance of the media player
                songs = MediaPlayer.create(this,R.raw.door_bell);
                //starts the ringtone
                songs.start();
                songs.setLooping(true);

            }
            else if(ringtone_choice == 3){
                //create a instance of the media player
                songs = MediaPlayer.create(this,R.raw.foghorn);
                //starts the ringtone
                songs.start();
                songs.setLooping(true);

            }
            else if(ringtone_choice == 4){
                //create a instance of the media player
                songs = MediaPlayer.create(this,R.raw.hell);
                //starts the ringtone
                songs.start();
                songs.setLooping(true);

            }
            else if(ringtone_choice == 5){
                //create a instance of the media player
                songs = MediaPlayer.create(this,R.raw.school_bell);
                //starts the ringtone
                songs.start();
                songs.setLooping(true);

            }
            else if(ringtone_choice == 6){
                //create a instance of the media player
                songs = MediaPlayer.create(this,R.raw.submarine);
                //starts the ringtone
                songs.start();
                songs.setLooping(true);

            }
            else if(ringtone_choice == 7){
                //create a instance of the media player
                songs = MediaPlayer.create(this,R.raw.watch_alarm);
                //starts the ringtone
                songs.start();
                songs.setLooping(true);

            }
            else {

                Log.e("do nothing","ringtone");

            }


        }
        //if there is music playing and user press off alarm, then music will stop playing
        else if (this.isRun && start_ID == 0){
            //to stop the ringtone
            songs.stop();
            songs.reset();

            this.isRun = false;
            this.start_ID = 0;

        }


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        this.isRun = false;

    }



}
