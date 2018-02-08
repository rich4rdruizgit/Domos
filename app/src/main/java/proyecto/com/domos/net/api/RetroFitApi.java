package proyecto.com.domos.net.api;

import proyecto.com.domos.net.models.Login;
import proyecto.com.domos.net.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by rich4 on 7/02/2018.
 */

public interface RetroFitApi  {

    public static final String BASE_URL = "https://menjurje.000webhostapp.com/domos/indexws.php/";

    @POST("login")
    Call<User> login (@Body Login loginBody);
}
