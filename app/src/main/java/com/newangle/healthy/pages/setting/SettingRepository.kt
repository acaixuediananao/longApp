package com.newangle.healthy.pages.setting

import com.newangle.healthy.bean.DeviceInfo
import com.newangle.healthy.di.repository.RepositoryComponent
import com.newangle.healthy.persistence.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepository  @Inject constructor(factory: RepositoryComponent.Factory) {
    @Inject
    lateinit var appDatabase: AppDatabase

    init {
        factory.create().inject(this)
    }

    suspend fun loadDeviceInfo()  =
        withContext(Dispatchers.IO) {
            var deviceInfos = appDatabase.settingDao().getAllDeviceInfo()
            mockData()

        }

    fun mockData() = listOf(
        DeviceInfo(showName = "app版本号", value = "1.1.1", type = "appVersion"),
        DeviceInfo(showName = "主机版本号", value = "1.2.3", type = "hostVersion"),
        DeviceInfo(showName = "手柄型号", value = "2.3.4", type = "handleMode"),
        DeviceInfo(showName = "设备类型", value = "3.4.5", type = "808"),
        DeviceInfo(showName = "当前计数", value = "00000000000231", type = "count")
        )
}