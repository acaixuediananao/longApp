package com.newangle.healthy.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.newangle.healthy.base.logger.LogUtils
import com.newangle.healthy.bean.User
import com.newangle.healthy.di.activity.ActivityComponent
import com.newangle.healthy.net.Response
import com.newangle.healthy.persistence.DataStoreRepository
import com.newangle.healthy.persistence.db.AppDatabase
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(application: Application, activityComponent: ActivityComponent) : AndroidViewModel(application) {

    @Inject
    lateinit var loginRepository: LoginRepository
    @Inject
    lateinit var dataStoreRepository: DataStoreRepository
    @Inject
    lateinit var appDatabase: AppDatabase
    private var _state = MutableStateFlow<State>(State.NOT_BEGIN)
    val state = _state.asLiveData()

    init {
        activityComponent.inject(this)
    }

    fun  login(phone:String, password:String) {
        _state.value = State.LOADING
        viewModelScope.launch {
            LogUtils.i("main activity thread 1 %s", Thread.currentThread().name)
            val result : Response<Any> = loginRepository.login(phone,password)
            if (result.code == 200) {
                _state.value = State.SUCCESS
                dataStoreRepository.saveLogin(true)
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