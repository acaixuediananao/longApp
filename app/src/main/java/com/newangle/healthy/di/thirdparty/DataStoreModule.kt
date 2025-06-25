package com.newangle.healthy.di.thirdparty

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.newangle.healthy.persistence.DataStoreRepository
import com.newangle.healthy.persistence.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideDataStore(application: Application) : DataStore<Preferences> {
        return application.applicationContext.dataStore
    }

//    @Provides
//    fun provideDataStoreRepository(dataStore: DataStore<Preferences>) : DataStoreRepository {
//        return DataStoreRepository(dataStore)
//    }
}