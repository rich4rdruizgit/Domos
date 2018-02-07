package proyecto.com.domos.data.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class User
{
    @PrimaryKey(autoGenerate = true)
    private Long userId;

    //colocar los demas atributos que pueda tener esta clase

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }


}
