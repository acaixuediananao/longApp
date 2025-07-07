package com.newangle.healthy.login

import com.newangle.healthy.base.logger.LogUtils
import com.newangle.healthy.bean.User
import com.newangle.healthy.di.repository.RepositoryComponent
import com.newangle.healthy.net.ApiService
import com.newangle.healthy.net.Response
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(factory: RepositoryComponent.Factory) {
    @Inject
    lateinit var apiService: ApiService
    init {
        factory.create().inject(this)
    }

    suspend fun login(phoneNumber : String, password : String): Response<Any> =
        withContext(Dispatchers.IO) {
            LogUtils.i("main activity thread 2 %s", Thread.currentThread().name)
            apiService.login(phoneNumber, password)
        }

}