package at.kropf.waketer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import at.kropf.waketer.R;

/**
 * Created by martinkropf on 30.11.14.
 */
public class AddBandFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_music_overview, container, false);
        return rootView;
    }
}