package com.newangle.healthy.di

import android.content.Context
import com.newangle.healthy.MainViewModel
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.login.LoginRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ServiceModule::class, NetWorkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(): AppComponent;
    }

    fun inject(app: NewAngleApp)
    fun inject(loginRepository: LoginRepository)
    fun inject(mainViewModel: MainViewModel)
}