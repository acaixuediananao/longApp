package com.newangle.healthy.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


object NetworkUtils {

    /**
     * 检查设备是否连接到互联网
     *
     * @param context 上下文对象
     * @return true 如果设备已联网，false 否则
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        if (connectivityManager == null) {
            return false
        }


        // 针对不同Android版本使用不同方法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android 6.0 (M) 及以上版本
            val activeNetwork = connectivityManager.activeNetwork
            if (activeNetwork == null) {
                return false
            }

            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork)

            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)
                    )
        } else {
            // 兼容旧版本
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

    /**
     * 获取当前网络类型
     *
     * @param context 上下文对象
     * @return 网络类型字符串 (WiFi, 移动数据, 以太网, 未知)
     */
    fun getNetworkType(context: Context): String {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        if (connectivityManager == null) {
            return "无网络"
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork
            if (activeNetwork == null) {
                return "无网络"
            }

            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork)

            if (capabilities == null) {
                return "未知网络"
            }

            return if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                "WiFi"
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                "移动数据"
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                "以太网"
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)) {
                "蓝牙"
            } else {
                "其他网络"
            }
        } else {
            // 兼容旧版本
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo == null) {
                return "无网络"
            }

            val type = activeNetworkInfo.type
            return if (type == ConnectivityManager.TYPE_WIFI) {
                "WiFi"
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                "移动数据"
            } else if (type == ConnectivityManager.TYPE_ETHERNET) {
                "以太网"
            } else if (type == ConnectivityManager.TYPE_BLUETOOTH) {
                "蓝牙"
            } else {
                "其他网络"
            }
        }
    }
}
