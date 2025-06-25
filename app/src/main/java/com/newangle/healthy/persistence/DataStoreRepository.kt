package com.newangle.healthy.persistence

import android.content.Context
import androidx.compose.ui.graphics.vector.PathNode
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore by preferencesDataStore("")

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
        ex -> Logger.e("read key error ${ex.message}")
    }

    companion object {
        private val DARK_MODE = intPreferencesKey("dark_mode")
    }
}