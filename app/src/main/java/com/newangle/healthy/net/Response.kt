package com.newangle.healthy.net

data class Response<T>(val data : T, val code : Int, val msg : String)