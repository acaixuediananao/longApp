package com.newangle.healthy.persistence.db

import androidx.room.Dao
import androidx.room.Query
import com.newangle.healthy.bean.DeviceInfo

@Dao
interface SettingDao {

    @Query("SELECT * FROM deviceinfo")
    suspend fun getAllDeviceInfo() : List<DeviceInfo>
}