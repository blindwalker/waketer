package at.kropf.waketer.net;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;
import at.kropf.waketer.helper.JsonUTF8Request;
import at.kropf.waketer.interfaces.RequestListener;

/**
 * Created with IntelliJ IDEA.
 * User: martinkropf
 * Date: 04.09.13
 */
public class BaseRequest {

    private RequestListener requestListener;

    public void setWsName(String wsName) {
        this.wsName = wsName;
    }

    private String wsName;

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    private String apiKey;

    void performCall(RequestQueue requestQueue, String url, JSONObject jsonObj) {
        String uri = null;
        try {
            uri = String.format(url+wsName+ jsonObj.getString("url") +"&limit=1&api_key="+apiKey+"&format=json");
            uri = uri.replace(" ", "%20");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("WAKE", uri);

        JsonUTF8Request myReq = new JsonUTF8Request(Request.Method.GET,
                uri,
                null,
                createMyReqSuccessListener(uri),
                createMyReqErrorListener(uri));


        myReq.setRetryPolicy(new DefaultRetryPolicy(
                500,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(myReq);
    }

    public void setListener(RequestListener listener) {
        this.requestListener = listener;

    }

    Response.ErrorListener createMyReqErrorListener(final String url) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.e("SOCC", "Exception "+error.toString()+" in response"+url);
                    requestListener.onComplete(error);

                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        requestListener.onComplete(e);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        };
    }

    Response.Listener<JSONObject> createMyReqSuccessListener(final String url) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               Log.d("SOCC", response.toString());
                try {
                    requestListener.onComplete(response);
                } catch (Exception e) {
                    try {
                        requestListener.onComplete(e);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    e.printStackTrace();
                }

            }
        };
    }
}
