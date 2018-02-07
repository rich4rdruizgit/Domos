package proyecto.com.domos.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import proyecto.com.domos.util.AppConstants;

/**
 * Created by aranda on 7/02/18.
 */

public class PreferencesManager
{

    /**
     * Instance
     */
    private static PreferencesManager preferencesManager = null;

    /**
     * Shared Preferences
     */
    private SharedPreferences sharedPreferences;

    /**
     * Preferences variables
     */
    private PreferencesManager(Context context) {
        sharedPreferences = context
                .getSharedPreferences(
                        AppConstants.Preferences.PREFERENCES_NAME,
                        Context.MODE_PRIVATE);
    }

    /**
     * Return the only instance
     * Singleton
     * @param context
     */
    public static PreferencesManager getInstance(Context context) {
        if (preferencesManager == null) {
            preferencesManager = new PreferencesManager(context);
        }
        return preferencesManager;
    }

    // Private mode
    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }


    /**
     * Ejemplo de un charedpreference
     */

    public boolean setExampleString(String exampleString) {
        return getEditor().putString(AppConstants.Preferences.KEY_EXAMPLE_STRING,
                exampleString).commit();
    }


    public String getExampleString() throws ClassCastException,
            NullPointerException {
        return sharedPreferences.getString(AppConstants.Preferences.KEY_EXAMPLE_STRING,
                "");
    }

}
