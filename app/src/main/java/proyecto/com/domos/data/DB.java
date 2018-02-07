package proyecto.com.domos.data;


import android.arch.persistence.room.Room;
import android.content.Context;

public class DB {

    public static AppDataBase con;

    public static void init(Context context)
    {
        con = Room.databaseBuilder(context,AppDataBase.class,"domos.db")
                .build();
    }

}
