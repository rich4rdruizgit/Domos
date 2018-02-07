package proyecto.com.domos.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import proyecto.com.domos.data.model.User;


@Dao
public interface UserDao
{
    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user")
    List<User> all();

    @Query("SELECT * FROM user")
    LiveData<List<User>> allUsers();

    @Query("SELECT * FROM user WHERE userId = :userId")
    User findByUserId(String userId);

    @Query("DELETE FROM user")
    void deleteAllUsers();
}
