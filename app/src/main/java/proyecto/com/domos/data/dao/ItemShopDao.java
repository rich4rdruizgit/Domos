package proyecto.com.domos.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import proyecto.com.domos.data.model.ItemShop;

/**
 * Created by aranda on 17/02/18.
 */
@Dao
public interface ItemShopDao
{
    @Insert
    void insert(ItemShop itemShop);

    @Update
    void update(ItemShop itemShop);

    @Delete
    void delete(ItemShop itemShop);

    @Query("SELECT * FROM itemShop")
    List<ItemShop> all();

    @Query("SELECT * FROM itemShop")
    LiveData<List<ItemShop>> allItemsShop();


    @Query("DELETE FROM itemShop")
    void deleteAllItemsShop();
}
