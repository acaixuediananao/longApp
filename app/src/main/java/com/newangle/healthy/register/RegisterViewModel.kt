package com.newangle.healthy.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.newangle.healthy.bean.User
import com.newangle.healthy.di.activity.ActivityComponent
import com.newangle.healthy.login.LoginViewModel
import com.newangle.healthy.net.Response
import com.newangle.healthy.persistence.DataStoreRepository
import com.newangle.healthy.persistence.db.AppDatabase
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    application: Application, activityComponent: ActivityComponent) : AndroidViewModel(application) {
        @Inject
        lateinit var userRepository: UserRepository
    @Inject
    lateinit var appDatabase: AppDatabase

        init {
            activityComponent.inject(this)
        }

        private val _validateResult = MutableLiveData<String>()
        val userNameError : LiveData<String>
            get() = _validateResult

        private val _userName = MutableLiveData<String>()
        val userName : LiveData<String>
            get() = _userName

        private val _userPhone = MutableLiveData<String>()
        val userPhone : LiveData<String>
            get() = _userPhone

        private val _gender = MutableLiveData<String>()
        val gender : LiveData<String>
            get() = _gender

        private val _birthday = MutableLiveData<String>()
        val birthday : LiveData<String>
            get() = _birthday

        private val _email = MutableLiveData<String>()
        val email : LiveData<String>
            get() = _email

        private val _nurse = MutableLiveData<String>()
        val nurse : LiveData<String>
            get() = _nurse

        init {
            activityComponent.inject(this)
        }

        private val _registerResult = MutableLiveData<LoginViewModel.State>()
        val registerResultState : LiveData<LoginViewModel.State>
            get() = _registerResult

        fun onUserNameChanged(userName: String) {
            Logger.i("onUserNameChanged: $userName")
            _userName.value = userName
        }

        fun onUserPhoneChanged(userPhone: String) {
            Logger.i("onUserPhoneChanged: $userPhone")
            _userPhone.value = userPhone
        }

        fun onGenderSelected(gender: String) {
            Logger.i("onGenderSelected: $gender")
            _gender.value = gender
        }

        fun onBirthdayChanged(string: String) {
            Logger.i("onBirthdayChanged: $string")
            _birthday.value = string
        }

        fun onEmailChanged(string: String) {
            Logger.i("onEmailChanged: $string")
            _email.value = string
        }

        fun onNurseChanged(string: String) {
            Logger.i("onNurseChanged: $string")
            _nurse.value = string
        }

        fun register() {
            if (!validateParams()) {
                return
            }

            viewModelScope.launch {
                val user = User(gender = _gender.value,
                    userName = _userName.value,
                    birthday = _birthday.value,
                    phoneNumber = _userPhone.value,
                    email = _email.value,
                    nurse = _nurse.value)
                val result : Response<User> = userRepository.register(user)
                if (result.code == 200) {
                    _registerResult.value = LoginViewModel.State.SUCCESS
                    appDatabase.userDao().insertUser(result.data)
                } else {
                    _registerResult.value = LoginViewModel.State.FAILED(result.code, result.msg)
                }
                Logger.i("register user result ", result)
            }


        }

        fun validateParams() : Boolean {
            if (_userName.value?.isBlank() == true) {
                _validateResult.value = "用户名不能为空"
                return false
            }
            return true
        }
}