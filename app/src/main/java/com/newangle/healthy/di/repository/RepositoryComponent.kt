package com.newangle.healthy.di.repository

import com.newangle.healthy.login.LoginRepository
import com.newangle.healthy.register.UserRepository
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
}