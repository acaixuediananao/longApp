package com.newangle.healthy

import android.app.Application
import com.newangle.healthy.base.logger.LogUtils
import com.newangle.healthy.di.AppComponent
import com.newangle.healthy.di.DaggerAppComponent
import com.newangle.healthy.base.logger.LoggerConfig
class NewAngleApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        LogUtils.init(this);

        // 测试日志
        LogUtils.d("AppInit", "Application started");
    }
}
