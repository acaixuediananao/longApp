package com.newangle.healthy.di.fragment

import com.newangle.healthy.pages.about.AboutDeviceFragment
import com.newangle.healthy.pages.about.AboutDeviceViewModel
import com.newangle.healthy.pages.info.DeviceInfoFragment
import com.newangle.healthy.pages.info.DeviceInfoViewModel
import com.newangle.healthy.pages.parameter.HardwareParameterFragment
import com.newangle.healthy.pages.parameter.HardwareParameterViewModel
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
}