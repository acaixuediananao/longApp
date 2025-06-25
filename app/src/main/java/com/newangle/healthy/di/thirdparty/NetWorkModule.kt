package com.newangle.healthy.di.thirdparty

import com.newangle.healthy.net.ApiService
import com.newangle.healthy.net.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by xiaolong.wei on 2017/9/14.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetWorkModule {
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(LoggingInterceptor())
            .connectTimeout(20000, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides fun provideApiService(retrofit: Retrofit) : ApiService = retrofit.create(ApiService::class.java)

    companion object {
        private const val BASE_URL = "https://playlet.zonelian.com"
    }
}
