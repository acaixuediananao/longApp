package com.newangle.healthy.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.newangle.healthy.di.activity.ActivityComponent
import com.newangle.healthy.login.LoginRepository
import javax.inject.Inject

class SettingViewModel @Inject constructor(application: Application, activityComponent: ActivityComponent)
    : AndroidViewModel(application) {
        @Inject
        lateinit var loginRepository: LoginRepository
        init {
            activityComponent.inject(this)
        }
}