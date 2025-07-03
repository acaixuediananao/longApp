package com.newangle.healthy.di

import android.app.Application
import com.newangle.healthy.di.activity.ActivityComponent
import com.newangle.healthy.di.fragment.FragmentComponent
import com.newangle.healthy.di.thirdparty.DataStoreModule
import com.newangle.healthy.di.thirdparty.NetWorkModule
import com.newangle.healthy.persistence.DataStoreRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetWorkModule::class, SubAppComponent::class, DataStoreModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent;
    }

    fun activityComponent() : ActivityComponent.Factory
    fun fragmentComponent() : FragmentComponent.Factory
}