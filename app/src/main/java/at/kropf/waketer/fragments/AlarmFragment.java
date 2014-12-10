package at.kropf.waketer.fragments;

import android.database.Cursor;
import android.graphics.Typeface;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import at.kropf.waketer.R;
import at.kropf.waketer.customviews.FontedTextView;
import at.kropf.waketer.helper.AlarmDBHelper;
import at.kropf.waketer.helper.AlarmManagerHelper;
import at.kropf.waketer.helper.ClickToggleHelper;
import at.kropf.waketer.model.AlarmModel;

/**
 * Created by martinkropf on 02.12.14.
 *
 */
public class AlarmFragment extends Fragment
        implements View.OnClickListener, Animation.AnimationListener {

    RingtoneManager mRingtoneManager;
    Button setAlarm;
    Cursor mcursor;
    String title;
    static final int DEFAULTDATESELECTOR_ID = 0;
    private Boolean pickerOpen = false;
    private AlarmDBHelper dbHelper;

    private ScrollView hourSlider;

    private AlarmModel alarmDetails;

    private FontedTextView alarmTextHour;

    private TextView alarmTextMinute;

    private Boolean isVisible = false;

    private int currentHour;

    private int currentMinute;

    private HashMap<TextView, Boolean> repeatDay;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_set_alarm, container, false);

        dbHelper = new AlarmDBHelper(getActivity());
        repeatDay = new HashMap<TextView, Boolean>();

        alarmTextHour = (FontedTextView) rootView.findViewById(R.id.alarmTimeHour);
        alarmTextMinute = (TextView) rootView.findViewById(R.id.alarmTimeMinute);

        Calendar c = Calendar.getInstance();

        alarmTextHour.setText(String.format("%02d", c.get(Calendar.HOUR))+"");
        alarmTextMinute.setText(":"+String.format("%02d", c.get(Calendar.MINUTE))+"");

        String theId = "hour" + String.format("%02d", c.get(Calendar.HOUR));
        int hourId = getActivity().getResources().getIdentifier(theId, "id", getActivity().getPackageName());

        TextView hour = (TextView)rootView.findViewById(hourId);
        hour.setTextColor(getResources().getColor(R.color.blue));

        hourSlider = (ScrollView) rootView.findViewById(R.id.hour_slider);
        hourSlider.setVisibility(View.VISIBLE);

        //finally

        final Animation a = new Animation() {

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rootView.findViewById(R.id.alarmSetView).getLayoutParams();
                params.leftMargin = (int)(200 * interpolatedTime);
                rootView.findViewById(R.id.alarmSetView).setLayoutParams(params);
                isVisible = true;
            }
        };
        a.setDuration(500);

        final Animation b = new Animation() {

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rootView.findViewById(R.id.alarmSetView).getLayoutParams();
                params.leftMargin = 0;
                rootView.findViewById(R.id.alarmSetView).setLayoutParams(params);
                isVisible = false;

            }
        };
        b.setDuration(500);

        alarmTextHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isVisible){
                    rootView.findViewById(R.id.alarmSetView).startAnimation(a);

                }else{
                    rootView.findViewById(R.id.alarmSetView).startAnimation(b);

                }

            }
        });

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

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
