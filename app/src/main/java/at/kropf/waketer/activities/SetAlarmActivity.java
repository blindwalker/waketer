package at.kropf.waketer.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.zenkun.datetimepicker.time.RadialPickerLayout;
import com.zenkun.datetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import at.kropf.android.morphingbutton.MorphingButton;
import at.kropf.waketer.R;
import at.kropf.waketer.helper.AlarmDBHelper;
import at.kropf.waketer.helper.AlarmManagerHelper;
import at.kropf.waketer.helper.ClickToggleHelper;
import at.kropf.waketer.model.AlarmModel;

public class SetAlarmActivity extends FragmentActivity implements View.OnClickListener {    Ringtone rt;
    RingtoneManager mRingtoneManager;
    Button setAlarm;
    Cursor mcursor;
    Intent Mringtone;
    String title;
    static final int DEFAULTDATESELECTOR_ID = 0;
    private Boolean pickerOpen = false;
    TimePickerDialog time;
    private AlarmDBHelper dbHelper = new AlarmDBHelper(this);

    private AlarmModel alarmDetails;

    private TextView alarmText;

    private int currentHour;

    private int currentMinute;

    private HashMap<TextView, Boolean> repeatDay;

    @Override
    protected void onPause() {
        super.onPause();
    }

    private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
            alarmText.setText(String.format("%02d", hourOfDay)+":"+
                    String.format("%02d", minute));
            currentHour = hourOfDay;
            currentMinute = minute;
            pickerOpen = false;

        }
    };

    private void setAlarm(){
        alarmDetails.timeMinute = currentMinute;
        alarmDetails.timeHour = currentHour;
        alarmDetails.isEnabled = true;
        alarmDetails.name = "a";
        alarmDetails.setRepeatingDay(0, true);
        alarmDetails.setRepeatingDay(1, true);
        alarmDetails.setRepeatingDay(2, true);
        alarmDetails.setRepeatingDay(3, true);
        alarmDetails.setRepeatingDay(4, true);
        alarmDetails.setRepeatingDay(5, true);
        alarmDetails.setRepeatingDay(6, true);

        if (alarmDetails.id < 0) {
            dbHelper.createAlarm(alarmDetails);
        } else {
            dbHelper.updateAlarm(alarmDetails);
        }

        AlarmManagerHelper.setAlarms(this);

    }

    private void openTimePicker(){
        if(currentMinute == 0 && currentHour == 0){
            Calendar c = Calendar.getInstance();
            currentHour =  c.get(Calendar.HOUR);
            currentMinute =  c.get(Calendar.MINUTE);
        }
        time = TimePickerDialog.newInstance(listener , currentHour, currentMinute, true);
        if(!pickerOpen){
            time.show(getSupportFragmentManager(), "Choose wisely");
            pickerOpen = true;

        }


    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        alarmText = (TextView) findViewById(R.id.alarmTime);

        alarmText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker();
            }
        });

        openTimePicker();

        repeatDay = new HashMap<TextView, Boolean>();

        repeatDay.put((TextView)findViewById(R.id.monday), false);
        repeatDay.put((TextView)findViewById(R.id.tuesday), false);
        repeatDay.put((TextView)findViewById(R.id.wednesday), false);
        repeatDay.put((TextView)findViewById(R.id.thursday), false);
        repeatDay.put((TextView)findViewById(R.id.friday), false);
        repeatDay.put((TextView)findViewById(R.id.saturday), false);
        repeatDay.put((TextView)findViewById(R.id.sunday), false);

        findViewById(R.id.monday).setOnClickListener(toggleTextView);
        findViewById(R.id.tuesday).setOnClickListener(toggleTextView);
        findViewById(R.id.wednesday).setOnClickListener(toggleTextView);
        findViewById(R.id.thursday).setOnClickListener(toggleTextView);
        findViewById(R.id.friday).setOnClickListener(toggleTextView);
        findViewById(R.id.saturday).setOnClickListener(toggleTextView);
        findViewById(R.id.sunday).setOnClickListener(toggleTextView);

        long id;
        try{
            id = getIntent().getExtras().getLong("id");
        }catch (Exception e){
            id = -1;
        }

        if (id == -1) {
            alarmDetails = new AlarmModel();
        } else {
            // SET EXISTING PARAMETERS FROM DATABASE TO VIEW
            alarmDetails = dbHelper.getAlarm(id);
        }

        //the following appends the cursor with the cursor that is used when the ringtone picker pops up
        mRingtoneManager = new RingtoneManager(this);
        mcursor = mRingtoneManager.getCursor();
        title = RingtoneManager.EXTRA_RINGTONE_TITLE;

        setAlarm = (Button)findViewById(R.id.setAlarm);
        setAlarm.setOnClickListener(this);

        showDialog(DEFAULTDATESELECTOR_ID);

    }

    private void updateModelFromLayout() {
        alarmDetails.isEnabled = true;
    }

    @Override
    public void onClick(View arg0) {
// TODO Auto-generated method stub
        setAlarm();
        startActivity(new Intent(SetAlarmActivity.this, HomeActivity.class));
        finish();

        /*
        Mringtone = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        Mringtone.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE);
        Mringtone.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, getString(R.string.addFallback));
        Mringtone.getBooleanExtra(RingtoneManager.EXTRA_RINGTONE_INCLUDE_DRM, true);

        String uri = null;
        if ( uri != null ) {
            Mringtone.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse( uri ));
        } else {
            Mringtone.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri)null);
        }

        startActivityForResult(Mringtone, 0);
*/
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent Mringtone) {
        switch (resultCode) {
/*
*
*/
            case RESULT_OK:
//sents the ringtone that is picked in the Ringtone Picker Dialog
                Uri uri = Mringtone.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

//send the output of the selected to a string
                String test = uri.toString();

//prints out the result in the console window
                Log.i("Sample", "uri " + uri);

//this passed the ringtone selected from the user to a new method
                play(uri);

//set default ringtone
                try
                {
                    RingtoneManager.setActualDefaultRingtoneUri(this, resultCode, uri);
                }
                catch (Exception localException)
                {

                }
                break;


        }

    }

    private View.OnClickListener toggleTextView = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            repeatDay.put((TextView)v, ClickToggleHelper.toggle(SetAlarmActivity.this, (TextView)v, R.color.blue, R.color.white));
        }
    };


    //this method captures the ringtone from the selection and plays it in the main activity
    private void play(Uri uri) {
        if (uri != null) {
            //in order to play the ringtone, you need to create a new Ringtone with RingtoneManager and pass it to a variable
            rt = RingtoneManager.getRingtone(this, uri);
            //rt.play();
        }
    }

}
