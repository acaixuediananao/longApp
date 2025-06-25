package com.newangle.healthy.di.activity

import com.newangle.healthy.MainActivity
import com.newangle.healthy.MainViewModel
import com.newangle.healthy.setting.SettingActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface ActivityComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): ActivityComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(model: MainViewModel)
    fun inject(settingActivity: SettingActivity)
}