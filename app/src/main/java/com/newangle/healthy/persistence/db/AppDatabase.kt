package com.newangle.healthy.persistence.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.newangle.healthy.bean.DeviceInfo
import com.newangle.healthy.bean.User

@Database(entities = [User::class, DeviceInfo::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
   abstract fun userDao() : UserDao
   abstract fun settingDao() : SettingDao
}
val MIGRATION_1_2 = object : Migration(1, 2) {
   override fun migrate(db: SupportSQLiteDatabase) {
      db.execSQL("CREATE TABLE IF NOT EXISTS `DeviceInfo` (`uid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `showName` TEXT NOT NULL, `value` TEXT NOT NULL, `type` TEXT NOT NULL)")
   }
}