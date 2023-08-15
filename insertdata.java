package com.example.medicine_database;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class insertdata extends AppCompatActivity {
    Button btninsert,btnhomepage,btntrigger;
    EditText medname, meddate;
    Spinner medtime;
    String medicinename,medicinedate,medicinetime;
    private TextView dateTextView;
    private Button dateButton;
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertdata);
        medname = findViewById(R.id.med_name);
        medtime = findViewById(R.id.med_time);
        btninsert = findViewById(R.id.insert_button);
        btnhomepage=findViewById(R.id.homeButton);
        btntrigger = findViewById(R.id.btn_trigger);
        dateTextView = findViewById(R.id.dateTextView);
        dateButton = findViewById(R.id.dateButton);
        final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        calendar = Calendar.getInstance();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    showDatePicker();


            }
        });
        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                medicinename = medname.getText().toString();
                //medicinedate = meddate.getText().toString();
                medicinedate=dateFormat.format(calendar.getTime());
                medicinetime = medtime.getSelectedItem().toString();
                MedicineHelper helper = new MedicineHelper(getBaseContext(), MedicineHelper.DATABASE_NAME, null, 1);
                SQLiteDatabase database = helper.getWritableDatabase();
                ContentValues cv = new ContentValues(); cv.put("name",medicinename);
                cv.put("date",medicinedate);
                cv.put("time",medicinetime);
                database.insert("Medicine",null,cv);
                Toast.makeText(getBaseContext(),"Record inserted successfully",Toast.LENGTH_LONG).show();
            }
        });
        btnhomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(insertdata.this,MainActivity.class);
                startActivity(home);
            }
        });
        btntrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trigger = new Intent(insertdata.this,triggeractivity.class);
                Log.d(TAG,medicinedate+":"+medicinename);
                trigger.putExtra("date",medicinedate);
                trigger.putExtra("time",medicinetime);

                startActivity(trigger);
            }
        });
    }


    private void showDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                String selectedDate = sdf.format(calendar.getTime());
                dateTextView.setText(selectedDate);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                insertdata.this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); // Optional: Set a minimum date (e.g., current date)
        datePickerDialog.show();
    }
    }