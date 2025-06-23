package com.newangle.healthy

import android.app.Application
import com.newangle.healthy.di.AppComponent
import com.newangle.healthy.di.DaggerAppComponent

class NewAngleApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create()
    }
}
