package com.example.medicine_database;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class triggeractivity extends AppCompatActivity {
    EditText txtdate;
    Spinner time_of_the_day;
    Button btntrigger;
    Button btnhomepage;
    TimePicker alarmTimePicker;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    String meddate,medtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triggeractivity);
        meddate = getIntent().getStringExtra("date");
        medtime = getIntent().getStringExtra("time");

//        txtdate = findViewById(R.id.med_date);
//        time_of_the_day = findViewById(R.id.med_time);
//        btntrigger = findViewById(R.id.insert_button);
        btnhomepage=findViewById(R.id.homeButton);
        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        btnhomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(triggeractivity.this,MainActivity.class); startActivity(home);
            }
        });
//        btntrigger.setOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View v) {
//                String datetrg = txtdate.getText().toString().trim();
//                String timeofday = time_of_the_day.getSelectedItem().toString().trim();
//                MedicineHelper helper = new MedicineHelper(getBaseContext(), MedicineHelper.DATABASE_NAME, null, 1);
//                SQLiteDatabase database = helper.getWritableDatabase();
//
//                Cursor res = database.rawQuery("select * from Medicine where Medicine.date = ? and Medicine.time = ? COLLATE NOCASE", new String[]{datetrg,timeofday});  if(res.getCount() == 0)
//                {0+
//                    Toast.makeText(getBaseContext(), "No DATA ",Toast.LENGTH_LONG).show();
//                }
//                while (res.moveToNext())
//                {
//                    Intent intent = new Intent(triggeractivity.this, Alarm.class);
//                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 234324243, intent,PendingIntent.FLAG_IMMUTABLE);
//                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (3 * 1000), pendingIntent);
//                    Toast.makeText(getBaseContext(), "Alarm set in 3" +
//                            " seconds",Toast.LENGTH_LONG).show();
//                    Toast.makeText(getBaseContext(), "Time to take "+ res.getString(0)+ " Medicine : "+res.getString(2),Toast.LENGTH_LONG).show();
//                }
//            }
//        });


    }
    public void OnToggleClicked(View view) {
        long time;
        if (((ToggleButton) view).isChecked()) {
            Toast.makeText(triggeractivity.this, "ALARM ON", Toast.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();
            calendar .setTimeInMillis(System.currentTimeMillis());
            AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);

            // calendar is called to get current time in hour and minute
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

            // using intent i have class AlarmReceiver class which inherits
            // BroadcastReceiver
            Intent alarmIntent = new Intent(triggeractivity.this, Alarm_Receiver.class);
            Log.d(TAG,meddate+":"+medtime);
            alarmIntent.putExtra("date",meddate);
            alarmIntent.putExtra("time",medtime);
            // we call broadcast using pendingIntent
            pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE);

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

            Log.d(TAG,String.valueOf(System.currentTimeMillis()));

            time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
            //   time =  calendar.getTimeInMillis() - System.currentTimeMillis();
            Log.d(TAG,String.valueOf(time));
            if (System.currentTimeMillis() > time) {

//                // setting time as AM and PM
                if (Calendar.AM_PM == 0)
                    time = time + (1000 * 60 * 60 * 12);
                else
                    time = time + (1000 * 60 * 60 * 24);
                Log.d(TAG, String.valueOf(time));
            }

            // Alarm rings continuously until toggle button is turned off
           alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
          // alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (1*1000), pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            alarmManager.cancel(pendingIntent);
            Toast.makeText(triggeractivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
        }
    }

}