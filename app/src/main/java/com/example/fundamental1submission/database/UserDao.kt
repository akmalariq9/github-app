package com.example.fundamental1submission.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fundamental1submission.MainUser

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: MainUser)

    @Delete
    fun delete(user: MainUser)

    @Query("SELECT * from MainUser")
    fun getAllUsers(): LiveData<List<MainUser>>
}