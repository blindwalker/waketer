package at.kropf.waketer.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import at.kropf.waketer.R;
import at.kropf.waketer.animations.ProgressAnimation;
import at.kropf.waketer.customviews.TickPlusDrawable;
import at.kropf.waketer.helper.BitmapHelper;
import at.kropf.waketer.interfaces.Observable;
import at.kropf.waketer.interfaces.Observer;
import at.kropf.waketer.model.ErrorHandler;
import at.kropf.waketer.model.Track;
import at.kropf.waketer.net.WSSong;


public class AddSongActivity extends BaseActivity implements Observer{

    private String TAG = "WAKE_MainActivity";

    Track observable;

    public AddSongActivity(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        observable = new Track(getApp(), AddSongActivity.this);
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

                observable.doCallSongWS(((EditText) findViewById(R.id.editBandName)).getText().toString());
            }
        });

        ProgressAnimation.showProgress(findViewById(R.id.searchWrapper), findViewById(R.id.mainContent), (ImageView) findViewById(R.id.loading_cd));

        observable.doCallSongWS("Call me maybe");

    }

    @Override
    public void updateView(Object a) {
        Drawable dr = null;

        if(a instanceof Track){
            Track track = (Track) a;

            if(track.getName().length()==0){
            }else{
                ((TextView)findViewById(R.id.resultBandname)).setText(track.getName());
                Bitmap myImage = BitmapHelper.getBitmapFromURL(track.getAlbum().getImageBySize("extralarge").getImageName());
                dr = new BitmapDrawable(myImage);

                ((TextView)findViewById(R.id.resultBandtext)).setText(Html.fromHtml(track.getArtist().getName()));
                findViewById(R.id.addBandWrapper).setVisibility(View.VISIBLE);

                final TickPlusDrawable tickPlusDrawable = new TickPlusDrawable(8, getResources().getColor(R.color.blue), Color.WHITE);
                findViewById(R.id.checkView).setBackground(tickPlusDrawable);

                findViewById(R.id.addBandWrapper).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tickPlusDrawable.toggle();
                    }
                });
            }


            ProgressAnimation.hideProgress(findViewById(R.id.searchWrapper), findViewById(R.id.mainContent));
        }else if(a instanceof ErrorHandler){
            ((TextView)findViewById(R.id.resultBandname)).setText("Oh no!");
            findViewById(R.id.addBandWrapper).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.resultBandtext)).setText(getString(R.string.song_search_error));
            dr = getResources().getDrawable(R.drawable.oh_no);
        }
        if(dr != null){
            ((ImageView)findViewById(R.id.resultBandimage)).setImageDrawable(dr);

        }

    }
}
