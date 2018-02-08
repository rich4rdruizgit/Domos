package proyecto.com.domos;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import proyecto.com.domos.data.DB;

/**
 * Created by aranda on 7/02/18.
 */

public class App extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        //inicializamos el singleton de la Base de datos
        DB.init(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
