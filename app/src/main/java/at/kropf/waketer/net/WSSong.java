package at.kropf.waketer.net;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import at.kropf.waketer.R;
import at.kropf.waketer.helper.WaketerApplication;

/**
 * Created by martinkropf on 05.11.14.
 */
public class WSSong extends BaseRequest {

    public void searchSong(WaketerApplication app, Context context, String trackName) throws JSONException {

        setWsName("track.search&track=");
        setApiKey(context.getResources().getString(R.string.last_fm_api_key));
        Gson gson = new Gson();
        String json = gson.toJson(this);

        JSONObject jsonObj = new JSONObject(json);

        jsonObj.put("url", trackName);
        jsonObj.put("api_key", context.getResources().getString(R.string.last_fm_api_key));

        performCall(app.getVolleyQueue(), context.getString(R.string.last_fm_url), jsonObj);

    }

    public void searchSongDetail(WaketerApplication app, Context context, String mbid, String artist, String trackname) throws JSONException {

        setWsName("track.getInfo&mbid=");
        setApiKey(context.getResources().getString(R.string.last_fm_api_key));
        Gson gson = new Gson();
        String json = gson.toJson(this);

        JSONObject jsonObj = new JSONObject(json);

        jsonObj.put("url", mbid+"&artist="+artist+"&track="+trackname);
        jsonObj.put("api_key", context.getResources().getString(R.string.last_fm_api_key));

        performCall(app.getVolleyQueue(), context.getString(R.string.last_fm_url), jsonObj);

    }

    public void getCorrection(WaketerApplication app, Context context, String trackName) throws JSONException{
        setWsName("track.getCorrection&track=");
        setApiKey(context.getResources().getString(R.string.last_fm_api_key));
        Gson gson = new Gson();
        String json = gson.toJson(this);

        JSONObject jsonObj = new JSONObject(json);

        jsonObj.put("url", trackName);
        jsonObj.put("api_key", context.getResources().getString(R.string.last_fm_api_key));

        performCall(app.getVolleyQueue(), context.getString(R.string.last_fm_url), jsonObj);
    }
}
