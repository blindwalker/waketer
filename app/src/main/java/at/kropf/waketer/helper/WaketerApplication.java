package at.kropf.waketer.helper;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * @author martinkropf
 * @since 19.08.13
 */
public class WaketerApplication extends Application {

    private static RequestQueue volleyQueue;

    private PreferenceHandler preferenceHandler;

    public SQLiteHelper getDb() {
        return db;
    }

    private SQLiteHelper db;

    public RequestQueue getVolleyQueue() {
        return volleyQueue;
    }

    public PreferenceHandler getPreferenceHandler() {
        return preferenceHandler;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        volleyQueue = Volley.newRequestQueue(this);
        db = new SQLiteHelper(this);
        preferenceHandler = new PreferenceHandler(getApplicationContext());
    }

}