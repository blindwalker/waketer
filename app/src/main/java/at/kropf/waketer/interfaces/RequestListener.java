package at.kropf.waketer.interfaces;

import org.json.JSONObject;

/**
 * Created by martinkropf on 07.04.14.
 */
public interface RequestListener {
    void onComplete(Object response) throws Exception;
}
