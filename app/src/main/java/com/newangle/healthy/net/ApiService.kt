package com.newangle.healthy.net

import com.newangle.healthy.bean.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("/api/user/visitor_login")
    @FormUrlEncoded
    suspend fun login(@Field("androidId") androidId: String, @Field("zlsj") zlsj:String)
    : Response<User>
}