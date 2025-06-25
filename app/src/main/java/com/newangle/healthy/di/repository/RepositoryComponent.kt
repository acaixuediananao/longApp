package com.newangle.healthy.di.repository

import com.newangle.healthy.login.LoginRepository
import dagger.Subcomponent

@RepositoryScope
@Subcomponent
interface RepositoryComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create() : RepositoryComponent
    }

    fun inject(loginRepository: LoginRepository)
}