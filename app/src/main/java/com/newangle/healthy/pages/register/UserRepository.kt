package com.newangle.healthy.pages.register

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
class UserRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun register(user: User): Response<User> =
        withContext(Dispatchers.IO) {
            apiService.register(user)
        }
}