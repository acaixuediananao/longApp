package com.newangle.healthy.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "android_id") val androidId : String,
    @ColumnInfo(name = "nickname") val nickName : String ?,
    @ColumnInfo(name = "avatar") val avatar: String ?,
    @ColumnInfo(name = "teen_mode_pwd") val teen_mode_pwd: String ?,
    @ColumnInfo(name = "teen_mode") val teen_mode : Int ?)
