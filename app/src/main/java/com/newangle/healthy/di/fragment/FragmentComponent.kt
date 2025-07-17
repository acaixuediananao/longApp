package com.newangle.healthy.di.fragment

import com.newangle.healthy.pages.home.home.HomeOperationFragment
import com.newangle.healthy.pages.home.home.HomeOperationViewModel
import com.newangle.healthy.pages.setting.about.AboutDeviceFragment
import com.newangle.healthy.pages.setting.about.AboutDeviceViewModel
import com.newangle.healthy.pages.setting.info.DeviceInfoFragment
import com.newangle.healthy.pages.setting.info.DeviceInfoViewModel
import com.newangle.healthy.pages.setting.parameter.HardwareParameterFragment
import com.newangle.healthy.pages.setting.parameter.HardwareParameterViewModel
import com.newangle.healthy.pages.user.list.UserListFragment
import com.newangle.healthy.pages.user.list.UserListViewModel
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface FragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): FragmentComponent
    }

    fun inject(deviceInfoFragment: DeviceInfoFragment)
    fun inject(deviceInfoViewModel: DeviceInfoViewModel)

    fun inject(deviceInfoFragment: AboutDeviceFragment)
    fun inject(deviceInfoViewModel: AboutDeviceViewModel)

    fun inject(hardwareParameterFragment: HardwareParameterFragment)
    fun inject(hardwareParameterViewModel: HardwareParameterViewModel)

    fun inject(userListFragment: UserListFragment)
    fun inject(userListViewModel: UserListViewModel)

    fun inject(homeOperationFragment: HomeOperationFragment)
    fun inject(homeOperationViewModel: HomeOperationViewModel)

}