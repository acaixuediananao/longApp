package com.newangle.healthy.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeviceInfo(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "showName") val showName:String,
    @ColumnInfo(name = "value") val value:String,
    @ColumnInfo(name = "type") val type:String)
