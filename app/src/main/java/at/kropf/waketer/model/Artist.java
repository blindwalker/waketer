package at.kropf.waketer.model;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import at.kropf.waketer.R;
import at.kropf.waketer.interfaces.RequestListener;
import at.kropf.waketer.helper.TextHelper;
import at.kropf.waketer.helper.WaketerApplication;
import at.kropf.waketer.interfaces.Observable;
import at.kropf.waketer.interfaces.Observer;
import at.kropf.waketer.net.WSArtist;


/**
 * Created by martinkropf on 04.07.14.
 *
 */
public class Artist extends Music implements Observable{

    private WaketerApplication app;
    private Activity activity;
    private static String TAG = "WAKE";
    private ArrayList<Observer> observerList;
    private WSArtist wsArtist;

    public Artist(WaketerApplication app, Activity activity){
        observerList = new ArrayList<Observer>();
        wsArtist = new WSArtist();
        this.app = app;
        this.activity = activity;
    }

    public Artist(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    String name;
    String mbid;
    String dateAdded;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public void doCallArtistWS(final String artistName){
        wsArtist.setListener(new RequestListener() {
            @Override
            public void onComplete(final Object json) throws Exception {

                wsArtist.setListener(new RequestListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onComplete(Object response) throws Exception {
                        if (response instanceof JSONObject) {
                            Artist artist = fetchArtist((JSONObject) response);
                            if (artist.getMbid().length() > 0 || artist.getName().length() > 0) {
                                notifyObserver(fetchArtist((JSONObject) response));

                            } else {
                                notifyObserver(new ErrorHandler(new Throwable("Oh no!"), activity.getString(R.string.error_searching_band)));

                            }
                        } else if (response instanceof VolleyError) {
                            notifyObserver(new ErrorHandler(new Throwable("Oh no!"), activity.getString(R.string.error_timeout)));

                        } else{
                            notifyObserver(new ErrorHandler(new Throwable("Oh no!"), activity.getString(R.string.error_searching_band)));
                        }
                    }


                });

                if (json instanceof JSONObject) {
                    JSONObject correction;
                    try{
                        JSONObject corrections = ((JSONObject)json).getJSONObject("corrections");
                        correction = corrections.getJSONObject("correction").getJSONObject("artist");
                        wsArtist.getInfo(app, activity, correction.get("name").toString());

                    }catch (Exception e){
                        wsArtist.getInfo(app, activity, artistName);

                    }

                }else if (json instanceof VolleyError) {
                    notifyObserver(new ErrorHandler(new Throwable("Oh no!"), activity.getString(R.string.error_timeout)));

                }else{
                    notifyObserver(new ErrorHandler(new Throwable("Oh no!"), activity.getString(R.string.error_searching_band)));

                }

            }

        });
        try {
            wsArtist.getCorrection(app, activity, artistName);
        } catch (JSONException e) {
            e.printStackTrace();
            notifyObserver(new ErrorHandler(new Throwable("Oh no!"), activity.getString(R.string.error_searching_band)));

        }
    }

    public static Artist fetchArtist(JSONObject json) throws JSONException {
        Artist tempArtist = new Artist();

        try{
            JSONObject result = json.getJSONObject("artist");

            tempArtist.setName(result.optString("name", ""));

            tempArtist.setMbid(result.optString("mbid", ""));
            tempArtist.setDescription(TextHelper.lastfmTextHelper(result.getJSONObject("bio").getString("content")));

            List<ArtistImage> imageList = new ArrayList<ArtistImage>();
            for (int e = 0; e < result.getJSONArray("image").length(); e++) {
                ArtistImage tempImage = new ArtistImage();
                JSONObject tempJsonImage = result.getJSONArray("image").getJSONObject(e);

                tempImage.setImageName(tempJsonImage.optString("#text", ""));
                tempImage.setImageSize(tempJsonImage.optString("size", ""));
                imageList.add(tempImage);

            }
            tempArtist.setImageList(imageList);

        }catch (Exception e){
            if(tempArtist.getMbid().length()==0){
                tempArtist.setMbid("");
            }
        }

        return tempArtist;
    }

    @Override
    public void registerObserver(Observer a) {
        observerList.add(a);
    }

    @Override
    public void removeObserver(Observer a) {
        int i = observerList.indexOf(a);
        if(i >=0){
            observerList.remove(i);
        }
    }

    @Override
    public void notifyObserver(Object a) {
        for (Observer b : observerList) {
            b.updateView(a);
        }
    }
}
