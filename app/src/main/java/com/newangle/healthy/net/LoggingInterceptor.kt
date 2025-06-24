package com.newangle.healthy.net

import android.util.Log
import com.orhanobut.logger.Logger
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

/**
 *
 * @author xiaolong.wei
 * @date 2017/9/13
 */
class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // 记录请求开始时间
        val t1 = System.nanoTime()
        Logger.i(TAG + "Sending request url : ${request.url} \n connection : ${chain.connection()} \n headers : ${request.headers}")

        // 仅调用一次 proceed()，并缓存响应体内容
        val response = chain.proceed(request)
        val responseBody = response.body?.string() ?: "" // 读取并缓存响应体

        val t2 = System.nanoTime()
        Logger.i(TAG + "\n Received response for ${response.request.url} in ${(t2 - t1) / 1e6}ms\n${response.headers}"
        )
        Logger.i(TAG + "\n Received response data\n $responseBody"
        )

        // 重建响应对象（因 string() 消费了原始流）
        return response.newBuilder()
            .body(responseBody.toResponseBody(response.body?.contentType()))
            .build()
    }

    companion object {
        const val TAG: String = "com.newangle.healthy.net.LoggingInterceptor HTTP :"
    }
}

