package proyecto.com.domos.net.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rich4 on 7/02/2018.
 */

public class User {

    @SerializedName("result")
    private String result;
    @SerializedName("sid")
    private String sid;

    @SerializedName("userData")
    private UserDatos userDatos;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public UserDatos getUserDatos() {
        return userDatos;
    }

    public void setUserDatos(UserDatos userDatos) {
        this.userDatos = userDatos;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
