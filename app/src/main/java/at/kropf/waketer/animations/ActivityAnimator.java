package at.kropf.waketer.animations;

import android.app.Activity;

import at.kropf.waketer.R;


public class ActivityAnimator
{
    public void slideForwardAnimation(Activity a)
    {
        a.overridePendingTransition(R.anim.flip_horizontal_in, R.anim.flip_horizontal_out);

    }

    public void slideBackAnimation(Activity a)
    {
        a.overridePendingTransition(R.anim.flip_horizontal_in_reverse, R.anim.flip_horizontal_out_reverse);

    }
}