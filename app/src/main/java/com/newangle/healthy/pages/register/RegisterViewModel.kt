package com.newangle.healthy.pages.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.newangle.healthy.base.Event
import com.newangle.healthy.bean.User
import com.newangle.healthy.di.activity.ActivityComponent
import com.newangle.healthy.login.LoginViewModel
import com.newangle.healthy.net.Response
import com.newangle.healthy.persistence.db.AppDatabase
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    application: Application, activityComponent: ActivityComponent
) : AndroidViewModel(application) {
    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var appDatabase: AppDatabase

    init {
        activityComponent.inject(this)
    }

    private val _uiState = MutableLiveData(RegisterUiState())
    val uiState: LiveData<RegisterUiState>
        get() = _uiState

    private val _validateResult = MutableStateFlow<Event<String>?>(null)
    val validateResult = _validateResult.asLiveData()


    private val _registerResult = MutableLiveData<LoginViewModel.State>()
    val registerResultState: LiveData<LoginViewModel.State>
        get() = _registerResult

    fun onUserNameChanged(userName: String) {
        Logger.i("onUserNameChanged: $userName")
        _uiState.value = _uiState.value?.copy(userName = userName)
    }

    fun onUserPhoneChanged(userPhone: String) {
        Logger.i("onUserPhoneChanged: $userPhone")
        _uiState.value = _uiState.value?.copy(userPhone = userPhone)
    }

    fun onGenderSelected(gender: String) {
        Logger.i("onGenderSelected: $gender")
        _uiState.value = _uiState.value?.copy(gender = gender)
    }

    fun onBirthdayChanged(year: Int, month: Int, day: Int) {
        Logger.i("onBirthdayChanged: $year month is $month day is $day")
        CALENDER.apply { set(year, month, day) }
        _uiState.value = _uiState.value?.copy(birthday = CALENDER)
    }

    fun onEmailChanged(email: String) {
        Logger.i("onEmailChanged: $email")
        _uiState.value = _uiState.value?.copy(email = email)
    }

    fun onNurseChanged(nurse: String) {
        Logger.i("onNurseChanged: $nurse")
        _uiState.value = _uiState.value?.copy(nurse = nurse)
    }

    fun register() {
        if (!validateParams()) return

        viewModelScope.launch {
            _registerResult.value = LoginViewModel.State.LOADING
            val state = _uiState.value?:return@launch
            val user = createUserFromState(state)
            val result: Response<User> = userRepository.register(user)
            if (result.code == 200) {
                _registerResult.value = LoginViewModel.State.SUCCESS
                appDatabase.userDao().insertUser(result.data)
            } else {
                _registerResult.value = LoginViewModel.State.FAILED(result.code, result.msg)
            }
            Logger.i("register user result ", result)
        }


    }

    private fun validateParams(): Boolean {
        val state = _uiState.value ?: return false

        return when {
            state.userName.isBlank() -> {
                _validateResult.value = Event("用户名不能为空")
                false
            }
            state.gender.isBlank() -> {
                _validateResult.value = Event("请选择性别")
                false
            }
            state.email.isBlank() -> {
                _validateResult.value = Event("请输入您的邮箱")
                false
            }
            state.birthday == null -> {
                _validateResult.value = Event("请选择生日")
                false
            }
            else -> true
        }
    }

    private fun createUserFromState(state: RegisterUiState): User =
        User(
            gender = state.gender,
            userName = state.userName,
            birthday = state.birthday?.timeInMillis?.toString() ?: "",
            phoneNumber = state.userPhone,
            email = state.email,
            nurse = state.nurse
        )


    data class RegisterUiState(val userName: String = "",
                               val userPhone: String = "",
                               val gender: String = "",
                               val birthday: Calendar? = null,
                               val email: String = "",
                               val nurse: String = "")

    companion object {
        private val CALENDER = Calendar.getInstance()
    }
}