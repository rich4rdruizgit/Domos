package proyecto.com.domos.data;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import proyecto.com.domos.data.dao.ItemShopDao;
import proyecto.com.domos.data.dao.UserDao;
import proyecto.com.domos.data.model.ItemShop;
import proyecto.com.domos.data.model.User;

@Database(entities = {User.class, ItemShop.class}, version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase
{
    public abstract UserDao userDao();
    public abstract ItemShopDao itemShopDao();
}
