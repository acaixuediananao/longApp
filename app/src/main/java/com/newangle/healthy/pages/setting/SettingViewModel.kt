package com.newangle.healthy.pages.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.newangle.healthy.base.logger.LogUtils
import com.newangle.healthy.di.activity.ActivityComponent
import com.newangle.healthy.login.LoginRepository
import com.newangle.healthy.persistence.DataStoreRepository
import com.newangle.healthy.persistence.db.AppDatabase
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingViewModel @Inject constructor(application: Application, activityComponent: ActivityComponent)
    : AndroidViewModel(application) {
        @Inject
        lateinit var dataStoreRepository : DataStoreRepository
        @Inject
        lateinit var appDatabase: AppDatabase
        init {
            activityComponent.inject(this)
            viewModelScope.launch {
                dataStoreRepository.key.collect {
                    k ->
                    LogUtils.i("weixiaolong" + k.toString())
//                    var allUsers = appDatabase.userDao().getAllUsers()
//                    LogUtils.i("weixiaolong ${allUsers.size} and ${allUsers.get(0).userName}")
                }
            }
        }
        @Inject
        lateinit var loginRepository: LoginRepository
        init {
            activityComponent.inject(this)
        }
}