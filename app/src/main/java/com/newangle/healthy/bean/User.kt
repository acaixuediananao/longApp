package com.newangle.healthy.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "gender") val gender : String ?,
    @ColumnInfo(name = "username") val userName : String ?,
    @ColumnInfo(name = "birthday") val birthday: String ?,
    @ColumnInfo(name = "phoneNumber") val phoneNumber: String ?,
    @ColumnInfo(name = "email") val email : String ?,
    @ColumnInfo(name = "nurse") val nurse: String ?)
