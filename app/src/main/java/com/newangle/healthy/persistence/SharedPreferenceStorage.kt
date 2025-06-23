package com.newangle.healthy.persistence

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferenceStorage @Inject constructor(private val context: Context, val name : String) {
    private val mSp : SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun getString(key:String) : String? = mSp.getString(key, "")
}