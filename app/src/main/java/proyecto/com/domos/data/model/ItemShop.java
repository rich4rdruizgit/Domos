package proyecto.com.domos.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity
public class ItemShop {

    @PrimaryKey(autoGenerate = true)
    private Long itemId;

    private String srcAudio;
    private String email;
    private String message;
    private int duration;
    /**
     * type 0:text and 1:audio
    */
    private int type;

    public ItemShop(Long itemId, String srcAudio, String message,int type,int duration) {
        this.itemId = itemId;
        this.srcAudio = srcAudio;
        this.message = message;
        this.type = type;
        this.duration = duration;
    }

    public ItemShop()
    {
    }

    public ItemShop(String srcAudio, String message,int type,int duration) {
        this.srcAudio = srcAudio;
        this.message = message;
        this.type = type;
        this.duration = duration;
    }


    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getSrcAudio() {
        return srcAudio;
    }

    public void setSrcAudio(String srcAudio) {
        this.srcAudio = srcAudio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
