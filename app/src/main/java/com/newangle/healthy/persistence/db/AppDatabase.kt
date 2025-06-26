package com.newangle.healthy.persistence.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.newangle.healthy.bean.User

@Database(entities = [User::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
   abstract fun userDao() : UserDao
}