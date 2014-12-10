package at.kropf.waketer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.kropf.waketer.R;
import at.kropf.waketer.activities.MainActivity;
import at.kropf.waketer.animations.ActivityAnimator;

/**
 * Created by martinkropf on 30.04.14.
 *
 */
public class StartupThird extends BaseFragment {
    private static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static StartupThird newInstance()
    {
        StartupThird f = new StartupThird();
        return f;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().findViewById(R.id.getStarted).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                new ActivityAnimator().slideForwardAnimation(getActivity());

            }
        });

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_startup_third, null);

    }
}
