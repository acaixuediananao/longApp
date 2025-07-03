package com.newangle.healthy.di

import com.newangle.healthy.di.activity.ActivityComponent
import com.newangle.healthy.di.fragment.FragmentComponent
import com.newangle.healthy.di.repository.RepositoryComponent
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(subcomponents = [RepositoryComponent::class, ActivityComponent::class, FragmentComponent::class])
interface SubAppComponent {
}