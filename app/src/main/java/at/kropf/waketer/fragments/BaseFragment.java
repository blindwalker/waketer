package at.kropf.waketer.fragments;

import android.support.v4.app.Fragment;

import at.kropf.waketer.activities.BaseActivity;
import at.kropf.waketer.helper.WaketerApplication;

/**
 * @author martink
 * @since 19.08.13
 */
public class BaseFragment extends Fragment {

    public BaseActivity getABaseActivity() {
        if(getActivity() instanceof BaseActivity) {
            return (BaseActivity) getActivity();
        } else {
            return null;
        }
    }

    public boolean isUIActive() {
        return isAdded() && !isRemoving() && !isDetached() && isVisible();
    }

    public WaketerApplication getApp() {
        return getABaseActivity().getApp();
    }
}
