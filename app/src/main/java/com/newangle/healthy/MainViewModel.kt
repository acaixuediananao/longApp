package com.newangle.healthy

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.newangle.healthy.bean.User
import com.newangle.healthy.di.activity.ActivityComponent
import com.newangle.healthy.login.LoginRepository
import com.newangle.healthy.net.Response
import com.newangle.healthy.persistence.DataStoreRepository
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(application: Application, activityComponent: ActivityComponent)
    : AndroidViewModel(application) {

            @Inject
            lateinit var loginRepository: LoginRepository
            @Inject
            lateinit var dataStoreRepository: DataStoreRepository
            private var _state = MutableStateFlow<State>(State.NOT_BEGIN)
            val state = _state.asLiveData()

            init {
                activityComponent.inject(this)
            }

            fun  login(userNumber:Long, userName:String) {
                _state.value = State.LOADING
                viewModelScope.launch {
                    Logger.i("main activity thread 1 %s", Thread.currentThread().name)
                    val result : Response<User> = loginRepository.login(123456,"zhangsan")
                    if (result.code == 200) {
                        _state.value = State.SUCCESS(result.data)
                        dataStoreRepository.save(1000)
                    } else {
                        _state.value = State.FAILED(result.code, result.msg)
                    }
                    Logger.i("result ", result)
                }

        }

    sealed interface State {
        object NOT_BEGIN : State
        object LOADING : State
        data class SUCCESS(val user: User):State
        data class FAILED(val code : Int, val msg : String):State
    }
}