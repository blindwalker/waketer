package at.kropf.waketer.net;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import at.kropf.waketer.R;
import at.kropf.waketer.helper.WaketerApplication;
import at.kropf.waketer.net.BaseRequest;

/**
 * Created by martinkropf on 17.04.14.
 */
public class WSArtist extends BaseRequest {

    public void searchArtistByName(WaketerApplication app, Context context, String artistName) throws JSONException {

        setWsName("artist.search&artist=");
        setApiKey(context.getResources().getString(R.string.last_fm_api_key));
        Gson gson = new Gson();
        String json = gson.toJson(this);

        JSONObject jsonObj = new JSONObject(json);

        jsonObj.put("url", artistName);
        jsonObj.put("api_key", context.getResources().getString(R.string.last_fm_api_key));

        performCall(app.getVolleyQueue(), context.getString(R.string.last_fm_url), jsonObj);

    }

    public void getCorrection(WaketerApplication app, Context context, String artistName) throws JSONException{
        setWsName("artist.getCorrection&artist=");
        setApiKey(context.getResources().getString(R.string.last_fm_api_key));
        Gson gson = new Gson();
        String json = gson.toJson(this);

        JSONObject jsonObj = new JSONObject(json);

        jsonObj.put("url", artistName);
        jsonObj.put("api_key", context.getResources().getString(R.string.last_fm_api_key));

        performCall(app.getVolleyQueue(), context.getString(R.string.last_fm_url), jsonObj);
    }

    public void getInfo(WaketerApplication app, Context context, String artistName) throws JSONException{
        setWsName("artist.getInfo&artist=");
        setApiKey(context.getResources().getString(R.string.last_fm_api_key));
        Gson gson = new Gson();
        String json = gson.toJson(this);

        JSONObject jsonObj = new JSONObject(json);

        jsonObj.put("url", artistName);
        jsonObj.put("api_key", context.getResources().getString(R.string.last_fm_api_key));

        performCall(app.getVolleyQueue(), context.getString(R.string.last_fm_url), jsonObj);
    }
}