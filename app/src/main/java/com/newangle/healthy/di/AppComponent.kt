package com.newangle.healthy.di

import com.newangle.healthy.MainViewModel
import com.newangle.healthy.login.LoginRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ServiceModule::class, NetWorkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(): AppComponent;
    }

    fun inject(loginRepository: LoginRepository)
    fun inject(mainViewModel: MainViewModel)
}