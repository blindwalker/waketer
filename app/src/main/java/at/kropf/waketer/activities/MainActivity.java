package at.kropf.waketer.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import at.kropf.waketer.R;
import at.kropf.waketer.animations.ActivityAnimator;
import at.kropf.waketer.animations.ProgressAnimation;
import at.kropf.waketer.customviews.TickPlusDrawable;
import at.kropf.waketer.helper.BitmapHelper;
import at.kropf.waketer.interfaces.Observer;
import at.kropf.waketer.model.Artist;
import at.kropf.waketer.model.ErrorHandler;


public class MainActivity extends BaseActivity implements Observer{

    private String TAG = "WAKE_MainActivity";

    Artist observable = new Artist();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        observable = new Artist(getApp(), MainActivity.this);
        observable.registerObserver(this);

        findViewById(R.id.resetBandname).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EditText)findViewById(R.id.editBandName)).setText("");
            }
        });

        findViewById(R.id.btnFindArtist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow((findViewById(R.id.editBandName)).getWindowToken(), 0);

                observable.doCallArtistWS(((EditText)findViewById(R.id.editBandName)).getText().toString());
                ProgressAnimation.showProgress(findViewById(R.id.searchWrapper), findViewById(R.id.mainContent), (ImageView) findViewById(R.id.loading_cd));
            }
        });
        ProgressAnimation.showProgress(findViewById(R.id.searchWrapper), findViewById(R.id.mainContent), (ImageView) findViewById(R.id.loading_cd));
        observable.doCallArtistWS("Metallica");

    }


    @Override
    public void updateView(Object a) {
        Drawable dr = null;

        ProgressAnimation.hideProgressContentAnim(findViewById(R.id.searchWrapper), findViewById(R.id.mainContent), AnimationUtils.loadAnimation(this, R.anim.slide_up), AnimationUtils.loadAnimation(this, R.anim.slide_down_out));

        if(a instanceof Artist){
            Artist artist = (Artist) a;
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            ((TextView)findViewById(R.id.resultBandname)).setText(artist.getName());
            Bitmap myImage = BitmapHelper.getBitmapFromURL(artist.getImageBySize("extralarge").getImageName());
            dr = new BitmapDrawable(myImage);

            ((TextView)findViewById(R.id.resultBandtext)).setText(artist.getDescription());
            findViewById(R.id.addBandWrapper).setVisibility(View.VISIBLE);

            final TickPlusDrawable tickPlusDrawable = new TickPlusDrawable(8, getResources().getColor(R.color.blue), Color.WHITE);
            findViewById(R.id.checkView).setBackground(tickPlusDrawable);

            findViewById(R.id.addBandWrapper).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tickPlusDrawable.toggle();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run() {
                            startActivity(new Intent(MainActivity.this, SetAlarmActivity.class));
                            finish();
                            new ActivityAnimator().slideForwardAnimation(MainActivity.this);
                        }
                    }, 1000 );
                }
            });
        }else if(a instanceof ErrorHandler){
            ((TextView)findViewById(R.id.resultBandname)).setText("Oh no!");
            findViewById(R.id.addBandWrapper).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.resultBandtext)).setText(((ErrorHandler) a).getUserErrorMessage());
            dr = getResources().getDrawable(R.drawable.oh_no);
        }

        if(dr != null){
            ((ImageView)findViewById(R.id.resultBandimage)).setImageDrawable(dr);

        }
    }
}
