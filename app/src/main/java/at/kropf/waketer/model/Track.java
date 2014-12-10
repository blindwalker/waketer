package at.kropf.waketer.model;

import android.app.Activity;
import android.os.StrictMode;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import at.kropf.waketer.R;
import at.kropf.waketer.interfaces.RequestListener;
import at.kropf.waketer.helper.WaketerApplication;
import at.kropf.waketer.interfaces.Observable;
import at.kropf.waketer.interfaces.Observer;
import at.kropf.waketer.net.WSSong;

/**
 *  Created by martinkropf on 05.11.14.
 *  Well well well, what an observer this is!
 */
public class Track extends Music implements Observable {
    private ArrayList<Observer> observerList;
    private String name;
    private long id;
    private Artist artist;
    private WSSong wsSong;
    private WaketerApplication app;
    private Activity activity;
    private static String TAG = "WAKE";

    public Track(WaketerApplication app, Activity activity){
        observerList = new ArrayList<Observer>();
        wsSong = new WSSong();
        this.app = app;
        this.activity = activity;
    }

    public Track(){

    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    private Album album;

    private Track fetchTrack(JSONObject json) throws JSONException {
        Track tempTrack = new Track();

        try{
            JSONObject result = json.getJSONObject("track");

            tempTrack.setId(result.optLong("id", 0));
            tempTrack.setName(result.optString("name", ""));
            tempTrack.setAlbum(Album.fetchAlbum(result));
            tempTrack.setArtist(Artist.fetchArtist(result));
        }catch (Exception e){
            notifyObserver(new ErrorHandler(new Throwable("Error", null), activity.getString(R.string.song_search_error)));
        }


        return tempTrack;
    }

    public void doCallSongWS(final String songName){

        wsSong.setListener(new RequestListener() {
            @Override
            public void onComplete(final Object json) throws Exception {
                if(json instanceof JSONObject){
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String mbid = "";
                    String artist = "";
                    String track = "";
                    try{
                        mbid = ((JSONObject)json).getJSONObject("results").getJSONObject("trackmatches").getJSONObject("track").getString("mbid");
                        artist = ((JSONObject)json).getJSONObject("results").getJSONObject("trackmatches").getJSONObject("track").getString("artist");
                        track = ((JSONObject)json).getJSONObject("results").getJSONObject("trackmatches").getJSONObject("track").getString("name");
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                    doCallSongDetailWS(mbid, artist, track);
                }else if(json instanceof VolleyError){
                    notifyObserver(new ErrorHandler(new Throwable("Oh no!", null), activity.getString(R.string.error_timeout)));

                }else{
                    notifyObserver(new ErrorHandler(new Throwable("Oh no!", null), activity.getString(R.string.song_search_error)));

                }

            }

        });
        try {
            wsSong.searchSong(app, activity, songName);
        } catch (JSONException e) {
            e.printStackTrace();
            notifyObserver(new ErrorHandler(new Throwable("Error", null), activity.getString(R.string.song_search_error)));

        }
    }

    private void doCallSongDetailWS(String mbid, String artist, String trackname){
        wsSong.setListener(new RequestListener() {
            @Override
            public void onComplete(Object response) throws Exception {
                if(response instanceof JSONObject){
                    notifyObserver(fetchTrack((JSONObject)response));

                }else if(response instanceof VolleyError){
                    notifyObserver(new ErrorHandler(new Throwable("Oh no!"), activity.getString(R.string.error_timeout)));

                }else{
                    notifyObserver(new ErrorHandler(new Throwable("Oh no!", null), activity.getString(R.string.song_search_error)));

                }
            }

        });
        try {
            wsSong.searchSongDetail(app, activity, mbid, artist, trackname);
        } catch (JSONException e) {
            e.printStackTrace();
            notifyObserver(new ErrorHandler(new Throwable("Error", null), activity.getString(R.string.song_search_error)));

        }

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
