package at.kropf.waketer.fragments;

import android.database.Cursor;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zenkun.datetimepicker.time.RadialPickerLayout;
import com.zenkun.datetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.HashMap;

import at.kropf.waketer.R;
import at.kropf.waketer.helper.AlarmDBHelper;
import at.kropf.waketer.helper.AlarmManagerHelper;
import at.kropf.waketer.helper.ClickToggleHelper;
import at.kropf.waketer.model.AlarmModel;

/**
 * Created by martinkropf on 02.12.14.
 *
 */
public class AlarmFragment extends Fragment implements View.OnClickListener{

    RingtoneManager mRingtoneManager;
    Button setAlarm;
    Cursor mcursor;
    String title;
    static final int DEFAULTDATESELECTOR_ID = 0;
    private Boolean pickerOpen = false;
    TimePickerDialog time;
    private AlarmDBHelper dbHelper;

    private AlarmModel alarmDetails;

    private TextView alarmText;

    private int currentHour;

    private int currentMinute;

    private HashMap<TextView, Boolean> repeatDay;

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

        AlarmManagerHelper.setAlarms(getActivity());

    }

    private void openTimePicker(){
        if(currentMinute == 0 && currentHour == 0){
            Calendar c = Calendar.getInstance();
            currentHour =  c.get(Calendar.HOUR);
            currentMinute =  c.get(Calendar.MINUTE);
        }
        time = TimePickerDialog.newInstance(listener , currentHour, currentMinute, true);
        if(!pickerOpen){
            time.show(getActivity().getSupportFragmentManager(), "Choose wisely");
            pickerOpen = true;

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_set_alarm, container, false);

        dbHelper = new AlarmDBHelper(getActivity());

        alarmText = (TextView) rootView.findViewById(R.id.alarmTime);

        alarmText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker();
            }
        });

        repeatDay = new HashMap<TextView, Boolean>();

        repeatDay.put((TextView)rootView.findViewById(R.id.monday), false);
        repeatDay.put((TextView)rootView.findViewById(R.id.tuesday), false);
        repeatDay.put((TextView)rootView.findViewById(R.id.wednesday), false);
        repeatDay.put((TextView)rootView.findViewById(R.id.thursday), false);
        repeatDay.put((TextView)rootView.findViewById(R.id.friday), false);
        repeatDay.put((TextView)rootView.findViewById(R.id.saturday), false);
        repeatDay.put((TextView)rootView.findViewById(R.id.sunday), false);

        rootView.findViewById(R.id.monday).setOnClickListener(toggleTextView);
        rootView.findViewById(R.id.tuesday).setOnClickListener(toggleTextView);
        rootView.findViewById(R.id.wednesday).setOnClickListener(toggleTextView);
        rootView.findViewById(R.id.thursday).setOnClickListener(toggleTextView);
        rootView.findViewById(R.id.friday).setOnClickListener(toggleTextView);
        rootView.findViewById(R.id.saturday).setOnClickListener(toggleTextView);
        rootView.findViewById(R.id.sunday).setOnClickListener(toggleTextView);

        long id;
        try{
            id = getActivity().getIntent().getExtras().getLong("id");
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
        mRingtoneManager = new RingtoneManager(getActivity());
        mcursor = mRingtoneManager.getCursor();
        title = RingtoneManager.EXTRA_RINGTONE_TITLE;

        setAlarm = (Button)rootView.findViewById(R.id.setAlarm);
        setAlarm.setOnClickListener(this);

        getActivity().showDialog(DEFAULTDATESELECTOR_ID);


        return rootView;
    }

    @Override
    public void onClick(View arg0) {
        setAlarm();

    }

    private View.OnClickListener toggleTextView = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            repeatDay.put((TextView)v, ClickToggleHelper.toggle(getActivity(), (TextView) v, R.color.blue, R.color.white));
        }
    };

}
