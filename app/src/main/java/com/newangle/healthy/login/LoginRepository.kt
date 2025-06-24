package com.newangle.healthy.login

import com.newangle.healthy.bean.User
import com.newangle.healthy.di.DaggerAppComponent
import com.newangle.healthy.net.ApiService
import com.newangle.healthy.net.Response
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepository @Inject constructor() {
    @Inject
    lateinit var apiService: ApiService
    init {
        DaggerAppComponent.create().inject(this)
    }

    suspend fun login(phoneNumber : Long, userName : String): Response<User> =
        withContext(Dispatchers.IO) {
            Logger.i("main activity thread 2 %s", Thread.currentThread().name)
            apiService.login("8d6dddea337d709c", "zlsj")
        }

}