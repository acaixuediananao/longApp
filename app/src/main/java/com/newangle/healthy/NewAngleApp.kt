package com.newangle.healthy

import android.app.Application
import com.newangle.healthy.di.AppComponent
import com.newangle.healthy.di.DaggerAppComponent
import com.newangle.healthy.base.logger.LoggerConfig
import com.tencent.mmkv.MMKV


class NewAngleApp : Application() {

    private val mLoggerUtil by lazy {
        LoggerConfig(this)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        val rootDir = MMKV.initialize(this)
        mLoggerUtil.i("mmkv root: %s", rootDir)
    }
}
