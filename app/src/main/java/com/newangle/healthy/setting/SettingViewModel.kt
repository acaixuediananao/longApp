package com.newangle.healthy.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.newangle.healthy.di.activity.ActivityComponent
import com.newangle.healthy.login.LoginRepository
import com.newangle.healthy.persistence.DataStoreRepository
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingViewModel @Inject constructor(application: Application, activityComponent: ActivityComponent)
    : AndroidViewModel(application) {
        @Inject
        lateinit var dataStoreRepository : DataStoreRepository
        init {
            activityComponent.inject(this)
            viewModelScope.launch {
                dataStoreRepository.key.collect {
                    k ->
                    Logger.i("weixiaolong" + k.toString())
                }
            }
        }
        @Inject
        lateinit var loginRepository: LoginRepository
        init {
            activityComponent.inject(this)
        }
}