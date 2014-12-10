package at.kropf.waketer.animations;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Created by martinkropf on 07.11.14.
 */
public class ProgressAnimation {

    public static void rotate(ImageView image){

        AnimationSet animSet;

        animSet = new AnimationSet(true);
        animSet.setInterpolator(new DecelerateInterpolator());
        animSet.setFillAfter(true);
        animSet.setFillEnabled(true);

        final RotateAnimation animRotate = new RotateAnimation(0.0f,1440.0f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        animRotate.setDuration(5000);
        animRotate.setFillAfter(true);
        animSet.addAnimation(animRotate);
        animSet.setRepeatCount(12);
        image.startAnimation(animRotate);

    }

    public static void hideProgress(View progress, View content){
        progress.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
    }


    public static void hideProgressContentAnim(final View progress, final View content, final Animation animationIn, Animation animationOut){
        progress.setVisibility(View.GONE);

        animationOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                progress.setVisibility(View.GONE);
                content.startAnimation(animationIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animationIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                content.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        progress.startAnimation(animationOut);

    }

    public static void showProgress(View progress, View content){
        progress.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
    }

    public static void showProgress(View progress, View content, ImageView spinningDrawable){
        progress.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
        rotate(spinningDrawable);
    }
}
