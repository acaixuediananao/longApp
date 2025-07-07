package com.newangle.healthy.pages.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.newangle.healthy.base.logger.LogUtils
import com.newangle.healthy.di.activity.ActivityComponent
import com.newangle.healthy.login.LoginRepository
import com.newangle.healthy.net.Response
import com.newangle.healthy.persistence.DataStoreRepository
import com.newangle.healthy.persistence.db.AppDatabase
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(application: Application,
                                        val loginRepository: LoginRepository,
                                        val dataStoreRepository: DataStoreRepository,
                                        val appDatabase: AppDatabase)
    : AndroidViewModel(application) {

            private var _state = MutableStateFlow<State>(State.NOT_BEGIN)
            val state = _state.asLiveData()


            fun  login(phoneNumber:String, password:String) {
                _state.value = State.LOADING
                viewModelScope.launch {
                    LogUtils.i("main activity thread 1 %s", Thread.currentThread().name)
                    val result : Response<Any> = loginRepository.login(phoneNumber,password)
                    if (result.code == 200) {
                        _state.value = State.SUCCESS
                        dataStoreRepository.save(1000)
//                        appDatabase.userDao().insertUser(result.data)
                    } else {
                        _state.value = State.FAILED(result.code, result.msg)
                    }
                    LogUtils.i("result ", result.code.toString())
                }

        }

    sealed interface State {
        object NOT_BEGIN : State
        object LOADING : State
        object SUCCESS:State
        data class FAILED(val code : Int, val msg : String):State
    }
}