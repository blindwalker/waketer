package at.kropf.waketer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.kropf.waketer.R;

/**
 * Created by martinkropf on 30.04.14.
 *
 */
public class StartupFirst extends BaseFragment {
    private static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static StartupFirst newInstance()
    {
        StartupFirst f = new StartupFirst();
        return f;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_startup_first, null);

    }
}
