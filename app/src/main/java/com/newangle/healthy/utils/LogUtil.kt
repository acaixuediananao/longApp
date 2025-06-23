package com.newangle.healthy.utils

import java.util.logging.Level
import java.util.logging.Logger

object LogUtil {
    fun d(info: String?) {
        Logger.getLogger("weixiaolong--").log(Level.INFO, info)
    }
}
