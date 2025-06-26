package com.newangle.healthy.persistence.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.newangle.healthy.bean.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user:User)

    @Query("SELECT * FROM user")
    suspend fun getAllUsers() : List<User>
}