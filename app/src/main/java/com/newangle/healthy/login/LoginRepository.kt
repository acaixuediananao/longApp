package com.newangle.healthy.login

import android.util.Log
import com.newangle.healthy.bean.User
import com.newangle.healthy.di.DaggerAppComponent
import com.newangle.healthy.net.ApiService
import com.newangle.healthy.net.Response
import com.newangle.healthy.utils.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import javax.inject.Inject

class LoginRepository @Inject constructor() {
    @Inject
    lateinit var apiService: ApiService
    init {
        DaggerAppComponent.create().inject(this)
    }

    suspend fun login(phoneNumber : Long, userName : String): Response<User> =
        withContext(Dispatchers.IO) {
            LogUtil.d("thread " + Thread.currentThread().name)
            apiService.login("8d6dddea337d709c", "zlsj")
        }

}