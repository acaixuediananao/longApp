package com.newangle.healthy.pages.user.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.newangle.healthy.bean.UiState
import com.newangle.healthy.bean.User
import com.newangle.healthy.di.fragment.FragmentComponent
import com.newangle.healthy.net.ApiService
import com.newangle.healthy.persistence.db.AppDatabase
import com.newangle.healthy.utils.NetworkUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserListViewModel @Inject constructor(application: Application, fragmentComponent: FragmentComponent) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel

    @Inject
    lateinit var apiService: ApiService
    @Inject
    lateinit var appDatabase: AppDatabase

    init {
        fragmentComponent.inject(this)
    }

    private val _uiState = MutableStateFlow<UiState<List<User>>>(UiState.INIT)
    val uiState get() = _uiState.asStateFlow()

    fun loadUserList() =
        viewModelScope.launch {
            _uiState.value = UiState.LOADING
            var result = listOf<User>()
            if (NetworkUtils.isNetworkAvailable(application)) {
                var queryUserList = apiService.queryUserList()
                if (queryUserList.code == 200) {
                    _uiState.value = UiState.SUCCESS(queryUserList.data)
                } else {
                    _uiState.value = UiState.FAILED(queryUserList.code, queryUserList.msg)
                }

            } else {
                try {
                    result = appDatabase.userDao().getAllUsers()
                    _uiState.value = UiState.SUCCESS(result)
                } catch (ex: Exception) {
                    _uiState.value = UiState.FAILED(-1, msg = ex.message?:"读取数据库失败")
                }
            }
        }

}