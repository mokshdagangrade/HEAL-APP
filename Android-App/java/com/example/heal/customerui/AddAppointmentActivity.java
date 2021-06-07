package com.example.heal.customerui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.heal.CustomerHomeActivity;
import com.example.heal.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddAppointmentActivity extends AppCompatActivity {
    TextView txtDate1,txtDate2;
    TextView txtTime1,txtTime2;
    private String mDate1,mDate2,mTime1,mTime2;
    private int year1,year2;
    private int month1,month2;
    private int day1,day2;
    private int hour1,hour2;
    private int minute1,minute2;
    int fyear1=0,fyear2=0;
    int fmonth1=0,fmonth2=0;
    int fday1=0,fday2=0;
    int fhour1=0,fhour2=0;
    int fmin1=0,fmin2=0;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private String userID;

    static final int DATE1_DIALOG_ID = 999;
    static final int TIME1_DIALOG_ID = 998;
    static final int DATE2_DIALOG_ID = 997;
    static final int TIME2_DIALOG_ID = 996;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        setCurrentDateandTime();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Volunteers").child(userID);

        getUserInfo();
    }

    private void setCurrentDateandTime() {
        txtDate1 = (TextView)findViewById(R.id.textViewDate1);
        txtTime1 = (TextView)findViewById(R.id.textViewTime1);
        txtDate2 = (TextView)findViewById(R.id.textViewDate2);
        txtTime2 = (TextView)findViewById(R.id.textViewTime2);

        final Calendar c1 = Calendar.getInstance();
        final Calendar c2 = Calendar.getInstance();
        year1 = c1.get(Calendar.YEAR);
        year2=c2.get(Calendar.YEAR);
        month1 = c1.get(Calendar.MONTH);
        month2 = c2.get(Calendar.MONTH);
        day1 = c1.get(Calendar.DAY_OF_MONTH);
        day2 = c2.get(Calendar.DAY_OF_MONTH);

        fyear1=year1;
        fyear2=year2;
        fmonth1=month1;
        fmonth2=month2;
        fday1=day1;
        fday2=day2;

        hour1 = c1.get(Calendar.HOUR_OF_DAY);
        minute1 = c1.get(Calendar.MINUTE);
        hour2 = c2.get(Calendar.HOUR_OF_DAY);
        minute2 = c2.get(Calendar.MINUTE);

        fhour1=hour1;
        fmin1=minute1;
        fhour2=hour2;
        fmin2=minute2;

        // set current time into textview
        txtTime1.setText(new StringBuilder().append(pad(hour1))
                .append(":").append(pad(minute1)));
        // set current date into textview
        txtDate1.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month1 + 1).append("-").append(day1).append("-")
                .append(year1).append(" "));
        txtTime2.setText(new StringBuilder().append(pad(hour2))
                .append(":").append(pad(minute2)));
        // set current date into textview
        txtDate2.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month2 + 1).append("-").append(day2).append("-")
                .append(year2).append(" "));
    }

    public String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return ("0" + String.valueOf(c));

    }

    public void editTextDate1(View view) {
        showDialog(DATE1_DIALOG_ID);
    }
    public void editTextDate2(View view) {
        showDialog(DATE2_DIALOG_ID);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE1_DIALOG_ID:
                //DatePickerDialog.OnDateSetListener datePickerListener = null;
                return new DatePickerDialog(this,
                        date1PickerListener, year1, month1,day1);
            case DATE2_DIALOG_ID:
                //DatePickerDialog.OnDateSetListener datePickerListener = null;
                return new DatePickerDialog(this,
                        date2PickerListener, year2, month2,day2);
            case TIME1_DIALOG_ID:
                // set time picker as current time
                //TimePickerDialog.OnTimeSetListener timePickerListener = null;
                return new TimePickerDialog(this,
                        time1PickerListener, hour1, minute1,false);
            case TIME2_DIALOG_ID:
                // set time picker as current time
                //TimePickerDialog.OnTimeSetListener timePickerListener = null;
                return new TimePickerDialog(this,
                        time2PickerListener, hour2, minute2,false);
        }
        return null;

    }
    private DatePickerDialog.OnDateSetListener date1PickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year1 = selectedYear;
            month1 = selectedMonth;
            day1 = selectedDay;

            txtDate1.setText(new StringBuilder().append(month1 + 1)
                    .append("-").append(day1).append("-").append(year1)
                    .append(" "));
        }
    };
    private DatePickerDialog.OnDateSetListener date2PickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year2 = selectedYear;
            month2 = selectedMonth;
            day2 = selectedDay;

            txtDate2.setText(new StringBuilder().append(month2 + 1)
                    .append("-").append(day2).append("-").append(year2)
                    .append(" "));
        }
    };

    private TimePickerDialog.OnTimeSetListener time1PickerListener
            = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            hour1 = selectedHour;
            minute1 = selectedMinute;
            // set current time into textview

            txtTime1.setText(new StringBuilder().append(pad(hour1))
                    .append(":").append(pad(minute1)));

        }
    };
    private TimePickerDialog.OnTimeSetListener time2PickerListener
            = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            hour2 = selectedHour;
            minute2 = selectedMinute;
            // set current time into textview
            txtTime2.setText(new StringBuilder().append(pad(hour2))
                    .append(":").append(pad(minute2)));

        }
    };

    public void editTextTime1(View view) {
        showDialog(TIME1_DIALOG_ID);
    }
    public void editTextTime2(View view) {
        showDialog(TIME2_DIALOG_ID);
    }
    public void buttonCancel(View view) {

        startActivity(new Intent(AddAppointmentActivity.this, CustomerHomeActivity.class));
        finish();
    }

    public void Confirm(View view) {
           /* Intent intent = new Intent();
            intent.putExtra("monthOfYear", DisplayTheMonthInCharacters(month));
            intent.putExtra("dayOfMonth", day);
            intent.putExtra("year", year);
            intent.putExtra("hour", FormatTheHour(hour));
            intent.putExtra("minute", minute);
            intent.putExtra("AMorPM", AMorPM(hour));
            setResult(RESULT_OK, intent);*/
        saveUserInformation();


        finish();
    }

    private int FormatTheHour(int hour) {
        if (hour > 12){ hour -= 12; }
        return hour;
    }

    private String AMorPM(int hour) {
        if (hour > 12){ return "PM"; }
        else{ return "AM"; }
    }

    private String DisplayTheMonthInCharacters(int month) {
        switch(month){
            case 0:
                return "Jan";
            case 1:
                return"Feb";
            case 2:
                return"Mar";
            case 3:
                return"Apr";
            case 4:
                return"May";
            case 5:
                return"Jun";
            case 6:
                return"Jul";
            case 7:
                return"Aug";
            case 8:
                return"Sept";
            case 9:
                return"Oct";
            case 10:
                return"Nov";
            case 11:
                return"Dec";

        }
        return "";
    }
    private void getUserInfo(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("FromDate")!=null){
                        mDate1 = map.get("FromDate").toString();
                        txtDate1.setText(mDate1);
                    }
                    if(map.get("ToDate")!=null){
                        mDate2 = map.get("ToDate").toString();
                        txtDate2.setText(mDate2);
                    }
                    if(map.get("FromTime")!=null){
                        mTime1 = map.get("FromTime").toString();
                        txtTime1.setText(mTime1);
                    }
                    if(map.get("ToTime")!=null){
                        mTime2 = map.get("ToTime").toString();
                        txtTime2.setText(mTime2);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void saveUserInformation() {
        mDate1=txtDate1.getText().toString();
        mDate2=txtDate2.getText().toString();
        mTime1=txtTime1.getText().toString();
        mTime2=txtTime2.getText().toString();

        Map userInfo = new HashMap();
        userInfo.put("FromDate", mDate1);
        userInfo.put("ToDate", mDate2);
        userInfo.put("FromTime",mTime1);
        userInfo.put("ToTime",mTime2);
        databaseReference.updateChildren(userInfo);
        startActivity(new Intent(AddAppointmentActivity.this, CustomerHomeActivity.class));
        finish();


    }
}