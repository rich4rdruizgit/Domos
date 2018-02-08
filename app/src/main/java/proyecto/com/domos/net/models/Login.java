package proyecto.com.domos.net.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rich4 on 7/02/2018.
 */

public class Login {

    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
