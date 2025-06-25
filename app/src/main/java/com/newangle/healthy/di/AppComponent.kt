package com.newangle.healthy.di

import android.content.Context
import com.newangle.healthy.MainActivity
import com.newangle.healthy.MainViewModel
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.login.LoginRepository
import com.newangle.healthy.setting.SettingActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ServiceModule::class, NetWorkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: NewAngleApp): AppComponent;
    }

    fun inject(app: NewAngleApp)
    fun inject(mainActivity: MainActivity)
    fun inject(loginRepository: LoginRepository)
    fun inject(mainViewModel: MainViewModel)
    fun inject(settingActivity: SettingActivity)
}