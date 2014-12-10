package at.kropf.waketer.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author MartinKropf
 * @since 05.09.13
 */
public class PreferenceHandler {

    private static String PREF_KEY_FALLBACK_SOUND = "fallbackSound";

    private SharedPreferences globalPublicPreferences;
    private Context context;

    public PreferenceHandler(Context context) {
        this.context = context;
    }

    public String getFallbackSound() {
        return getGlobalPublicPreferences().getString(PREF_KEY_FALLBACK_SOUND, "");
    }

    public void setFallbackSound(final String fallbackSound) {
        getGlobalPublicPreferences().edit().putString(PREF_KEY_FALLBACK_SOUND, fallbackSound).commit();
    }

	private SharedPreferences getGlobalPublicPreferences() {
		if (globalPublicPreferences == null) {
            String APP_PREFIX = "my_price_shared_pref";
            globalPublicPreferences = context.getSharedPreferences(APP_PREFIX, Context.MODE_PRIVATE);
		}

		return globalPublicPreferences;
	}
}
