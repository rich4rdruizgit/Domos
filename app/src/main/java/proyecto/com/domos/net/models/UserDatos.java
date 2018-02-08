package proyecto.com.domos.net.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rich4 on 7/02/2018.
 */

public class UserDatos {

    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDatos{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
