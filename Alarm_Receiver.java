package com.example.medicine_database;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class Alarm_Receiver extends BroadcastReceiver {
    String meddate,medtime,medname;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    // implement onReceive() method
    public void onReceive(Context context, Intent intent) {

        meddate = intent.getStringExtra("date");
        medtime = intent.getStringExtra("time");



        MedicineHelper helper = new MedicineHelper(context.getApplicationContext(), MedicineHelper.DATABASE_NAME, null, 1);
        SQLiteDatabase database = helper.getWritableDatabase();

        Cursor res = database.rawQuery("select * from Medicine where Medicine.date = ? and Medicine.time = ? ", new String[]{meddate,medtime});

        res.moveToNext();
        medname= res.getString(0);




        // we will use vibrator first
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(4000);
        Toast.makeText(context, "Time to Take your Medicine:"+medname, Toast.LENGTH_LONG).show();
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        // setting default ringtone
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        // play ringtone
        ringtone.play();
    }
}
