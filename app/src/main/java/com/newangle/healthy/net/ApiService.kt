package com.newangle.healthy.net

import com.newangle.healthy.bean.Operator
import com.newangle.healthy.bean.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("/login")
    @FormUrlEncoded
    suspend fun login(@Field("phoneNumber") phone: String, @Field("password") password:String)
            : Response<Any>


    @POST("/register")
    suspend fun register(@Body user: User) : Response<User>
}