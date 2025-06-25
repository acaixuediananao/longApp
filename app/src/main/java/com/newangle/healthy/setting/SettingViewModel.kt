package com.newangle.healthy.setting

import androidx.lifecycle.AndroidViewModel
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.di.activity.ActivityComponent
import com.newangle.healthy.login.LoginRepository
import com.orhanobut.logger.Logger
import javax.inject.Inject

class SettingViewModel @Inject constructor(application:NewAngleApp, activityComponent: ActivityComponent)
    : AndroidViewModel(application) {
        @Inject
        lateinit var loginRepository: LoginRepository
        init {
            activityComponent.inject(this)
        }
}