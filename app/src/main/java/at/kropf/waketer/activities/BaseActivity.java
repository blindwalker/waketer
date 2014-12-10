package at.kropf.waketer.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

import at.kropf.waketer.helper.WaketerApplication;

/**
 * Base activity handling navigation drawer, actionbar and instance of SoccerengineApplication
 * extends SlidingFragmentActivity -  used for navigation drawer
 */
public class BaseActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    public WaketerApplication getApp() {
        if (getApplication() instanceof WaketerApplication) {
            return (WaketerApplication) getApplication();
        } else {
            return null;
        }
    }
}
