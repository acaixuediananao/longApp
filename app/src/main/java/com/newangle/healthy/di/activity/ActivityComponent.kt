package com.newangle.healthy.di.activity

import com.newangle.healthy.pages.main.MainActivity
import com.newangle.healthy.pages.main.MainViewModel
import com.newangle.healthy.login.LoginActivity
import com.newangle.healthy.login.LoginViewModel
import com.newangle.healthy.pages.home.HomeActivity
import com.newangle.healthy.pages.home.HomeViewModel
import com.newangle.healthy.pages.language.SelectLanguageActivity
import com.newangle.healthy.pages.register.RegisterActivity
import com.newangle.healthy.pages.register.RegisterViewModel
import com.newangle.healthy.pages.setting.SettingActivity
import com.newangle.healthy.pages.setting.SettingViewModel
import com.newangle.healthy.pages.welcome.WelcomeActivity
import com.newangle.healthy.pages.wifi.ConnectWifiActivity
import com.newangle.healthy.pages.wifi.ConnectWifiViewModel
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface ActivityComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): ActivityComponent
    }

    fun inject(welcomeActivity: WelcomeActivity)

    fun inject(mainActivity: MainActivity)
    fun inject(model: MainViewModel)

    fun inject(settingActivity: SettingActivity)
    fun inject(settingViewModel: SettingViewModel)

    fun inject(loginActivity: LoginActivity)
    fun inject(loginViewModel: LoginViewModel)

    fun inject(registerActivity: RegisterActivity)
    fun inject(registerViewModel: RegisterViewModel)

    fun inject(selectLanguageActivity: SelectLanguageActivity)

    fun inject(homeActivity: HomeActivity)
    fun inject(homeViewModel: HomeViewModel)

    fun inject(connectWifiActivity: ConnectWifiActivity)
    fun inject(connectWifiViewModel: ConnectWifiViewModel)
}