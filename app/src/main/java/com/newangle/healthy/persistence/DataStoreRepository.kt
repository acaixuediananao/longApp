package com.newangle.healthy.persistence

import android.content.Context
import androidx.compose.ui.graphics.vector.PathNode
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.newangle.healthy.base.logger.LogUtils
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.exp

val Context.dataStore by preferencesDataStore("NewAngleApp")

@Singleton
class DataStoreRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun save(value:Int) {
        dataStore.edit { pres ->
            pres[DARK_MODE] = value

        }
    }

    val key = dataStore.data.map {
        prefs -> prefs[DARK_MODE] ?: -1
    }.catch {
        ex -> LogUtils.e("read key error ${ex.message}")
    }

    suspend fun saveLogin(hasLogined:Boolean) {
        dataStore.edit { pres ->
            pres[LOGIN_STATE] = hasLogined
        }
    }

    val hasLogin =
        dataStore.data
        .map { prefs -> prefs[LOGIN_STATE] }
        .catch { ex -> LogUtils.e("login info error {${ex.message}}") }


    suspend fun saveFirstLauncher(firstLaunch: Boolean) {
        dataStore.edit { pres ->
            pres[FIRST_LAUNCH] = firstLaunch
        }
    }

    val firstLaunch =
        dataStore.data
            .map { prefs -> prefs[FIRST_LAUNCH] ?: true }
            .catch { ex -> LogUtils.e("first launch error {${ex.message}}") }

    companion object {
        private val DARK_MODE = intPreferencesKey("dark_mode")
        private val LOGIN_STATE = booleanPreferencesKey("has_login")
        private val FIRST_LAUNCH = booleanPreferencesKey("first_launch")
    }
}