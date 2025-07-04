package com.newangle.healthy.pages.setting

import android.os.Bundle
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.R
import com.newangle.healthy.base.BaseActivity
import com.newangle.healthy.base.BaseFragment
import com.newangle.healthy.base.language.LanguageManager
import com.newangle.healthy.databinding.ActivitySettingBinding
import com.newangle.healthy.pages.setting.about.AboutDeviceFragment
import com.newangle.healthy.pages.setting.info.DeviceInfoFragment
import com.newangle.healthy.pages.setting.parameter.HardwareParameterFragment
import javax.inject.Inject

class SettingActivity : BaseActivity() {
    @Inject
    lateinit var languageManager: LanguageManager
    @Inject
    lateinit var settingViewModel: SettingViewModel
    lateinit var mBinding : ActivitySettingBinding
    private val activityComponent by lazy {
        (application as NewAngleApp).appComponent.activityComponent().create()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        super.onCreate(savedInstanceState)
        mBinding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setUpViews()
    }

    private fun  setUpViews() {
        with(mBinding){
            settingPageBack.setOnClickListener {
                finish()
            }
            settingRadioGroup.check(R.id.device_info)
            settingRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                when(checkedId) {
                    R.id.device_info -> subSettingPageVp.setCurrentItem(0, false)
                    R.id.hardware_parameter -> subSettingPageVp.setCurrentItem(1, false)
                    R.id.about_device -> subSettingPageVp.setCurrentItem(2, false)
                }
            }
            deviceInfo.setButtonDrawable(R.drawable.setting_device_info_icon)
            deviceInfo.compoundDrawablePadding = 1000
            hardwareParameter.setButtonDrawable(R.drawable.setting_device_info_icon)
            aboutDevice.setButtonDrawable(R.drawable.setting_device_info_icon)

            subSettingPageVp.setUserInputEnabled(false)
            val data = listOf<BaseFragment>(DeviceInfoFragment(), HardwareParameterFragment(), AboutDeviceFragment())
            subSettingPageVp.adapter = SettingViewPagerAdapter(data, this@SettingActivity)

        }
    }
}