package com.newangle.healthy.pages.setting.info

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.newangle.healthy.bean.DeviceInfo
import com.newangle.healthy.bean.UiState
import com.newangle.healthy.di.fragment.FragmentComponent
import com.newangle.healthy.pages.setting.SettingRepository
import com.newangle.healthy.persistence.db.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeviceInfoViewModel @Inject constructor(
    application: Application, val settingRepository: SettingRepository
) : AndroidViewModel(application) {

    private val _data = MutableStateFlow<UiState<List<DeviceInfo>>>(UiState.INIT)
    val mData = _data.asStateFlow()

    fun loadAllDeviceInfo() {
        viewModelScope.launch {
            _data.value = UiState.LOADING
            try {
                val deviceInfos = settingRepository.loadDeviceInfo()
                _data.value = if (deviceInfos.isEmpty())
                    UiState.FAILED(-1, "no data")
                else
                    UiState.SUCCESS(deviceInfos)
            } catch (e : Exception) {
                _data.value = UiState.FAILED(-2, "error : ${e.message}")
            }
        }
    }
}