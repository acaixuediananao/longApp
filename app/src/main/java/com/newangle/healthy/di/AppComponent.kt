package com.newangle.healthy.di

import com.newangle.healthy.MainViewModel
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.di.activity.ActivityComponent
import com.newangle.healthy.di.thirdparty.NetWorkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetWorkModule::class, SubAppComponent::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: NewAngleApp): AppComponent;
    }

    fun activityComponent() : ActivityComponent.Factory
    fun inject(app: NewAngleApp)
}