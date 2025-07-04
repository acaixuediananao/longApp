package com.newangle.healthy.di.repository

import com.newangle.healthy.login.LoginRepository
import com.newangle.healthy.pages.setting.SettingRepository
import com.newangle.healthy.pages.register.UserRepository
import dagger.Subcomponent

@RepositoryScope
@Subcomponent
interface RepositoryComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create() : RepositoryComponent
    }

    fun inject(loginRepository: LoginRepository)
    fun inject(userRepository: UserRepository)
    fun inject(settingRepository: SettingRepository)
}