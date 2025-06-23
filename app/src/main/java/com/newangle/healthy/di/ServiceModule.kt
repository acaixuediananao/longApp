package com.newangle.healthy.di

import com.newangle.healthy.net.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Provides fun provideApiService(retrofit: Retrofit) : ApiService = retrofit.create(ApiService::class.java)
}
