package com.newangle.healthy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.newangle.healthy.bean.User
import com.newangle.healthy.di.DaggerAppComponent
import com.newangle.healthy.login.LoginRepository
import com.newangle.healthy.net.Response
import com.newangle.healthy.utils.LogUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel: ViewModel() {

            @Inject
            lateinit var loginRepository: LoginRepository
            private var _state = MutableStateFlow<State>(State.NOT_BEGIN)
            val state = _state.asLiveData()

            init {
                DaggerAppComponent.create().inject(this)
            }

            fun  login(userNumber:Long, userName:String) {
                _state.value = State.LOADING
                viewModelScope.launch {
                    LogUtil.d("thread 1" + Thread.currentThread().name)
                    val result : Response<User> = loginRepository.login(123456,"zhangsan")
            if (result.code == 200) {
                _state.value = State.SUCCESS(result.data)
            } else {
                _state.value = State.FAILED(result.code, result.msg)
            }
            LogUtil.d("result ")
        }

    }

    sealed interface State {
        object NOT_BEGIN : State
        object LOADING : State
        data class SUCCESS(val user: User):State
        data class FAILED(val code : Int, val msg : String):State
    }
}