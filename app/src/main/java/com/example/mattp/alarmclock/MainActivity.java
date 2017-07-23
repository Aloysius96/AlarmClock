package com.example.mattp.alarmclock;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;


import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
     //set a alarm manager
    AlarmManager alarm_mgr;
    TimePicker alarm_timepck;
    TextView update_alarm;
    Context context;
    PendingIntent pendingIntent;
    Button alarm_set;
    Button alarm_off;
    int hr, min;
    String hr_string, min_string, am_pm;
    int ringtone_selection;


    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.context = this;
        alarm_mgr = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm_timepck = (TimePicker) findViewById(R.id.timePicker);


        final Calendar calendar = Calendar.getInstance();
        final Intent alarm_intent = new Intent(this.context,ReceiveAlarm.class);

         alarm_set = (Button) findViewById(R.id.alarm_set);
         alarm_off = (Button) findViewById(R.id.alarm_off);

        Spinner spinner = (Spinner) findViewById(R.id.alarm_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.alarm_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //setting a onclicklistener to the onitemselected method
        spinner.setOnItemSelectedListener(this);




        alarm_set.setOnClickListener(new View.OnClickListener(){
            @TargetApi(Build.VERSION_CODES.N)
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v){
                //setting calendar with the hour and minute that is from the time picker
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepck.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepck.getMinute());
                //get int values of hour and minute
                hr = alarm_timepck.getHour();
                min = alarm_timepck.getMinute();
                //convert int values to strings
                hr_string = String.valueOf(hr);
                min_string = String.valueOf(min);
                // convert 24 hour to 12 hour system
                if(hr > 12){
                    hr_string = String.valueOf(hr - 12);
                }
                if(min < 10){
                    //adds another 0 after ":"
                    min_string = "0"+ String.valueOf(min);
                }
                if(hr < 12) {
                    am_pm = " AM";
                } else {
                    am_pm = " PM";
                }

                //method that changes the update text
                Toast.makeText(MainActivity.this,"Alarm is set to " + hr_string + " : " + min_string  + am_pm, Toast.LENGTH_SHORT).show();
                //tells the clock that u pressed the on button
                alarm_intent.putExtra("extra", "alarm on");
                //tells the clock that u wanna add value from the drop down spinner
                alarm_intent.putExtra("ringtone", ringtone_selection);
                //creates a pending intent that delays intent until specified calendar time
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
                        alarm_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                //set the alarm manager
                alarm_mgr.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                        pendingIntent);

            }


        });



        alarm_off.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Toast.makeText(MainActivity.this
                        ,"Alarm is off!" , Toast.LENGTH_SHORT).show();

                //cancels the alarm
                alarm_mgr.cancel(pendingIntent);


                //tells the clock that u pressed the off button
                alarm_intent.putExtra("extra","alarm off" );
                //to prevent a crash in the null pointer exception
                alarm_intent.putExtra("ringtone", ringtone_selection);

                sendBroadcast(alarm_intent);
            }
        });
    }

    private void set_alarm_text(String s) {
        update_alarm.setText(s);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //output whatever id user has selected
        Toast.makeText(parent.getContext()," ringtone number " + id, Toast.LENGTH_SHORT).show();
        ringtone_selection=(int) id;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
