package com.newangle.healthy.pages.setting

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.R
import com.newangle.healthy.base.BaseActivity
import com.newangle.healthy.base.BaseFragment
import com.newangle.healthy.base.language.LanguageManager
import com.newangle.healthy.databinding.ActivitySettingBinding
import com.newangle.healthy.pages.about.AboutDeviceFragment
import com.newangle.healthy.pages.info.DeviceInfoFragment
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
        enableEdgeToEdge()
        mBinding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpViews()
    }

    private fun  setUpViews() {
        with(mBinding){
            settingPageBack.setOnClickListener {
                finish()
            }
            settingRadioGroup.check(R.id.device_info)
            settingRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                if (checkedId == R.id.device_info) {
                    subSettingPageFragmentContainer.currentItem = 0
                } else if (checkedId == R.id.about_device) {
                    subSettingPageFragmentContainer.currentItem = 1
                }
            }
            val data = listOf<BaseFragment>(DeviceInfoFragment(), AboutDeviceFragment())
            subSettingPageFragmentContainer.adapter = SettingViewPagerAdapter(data, this@SettingActivity)

        }
    }
}