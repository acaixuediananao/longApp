package com.newangle.healthy.pages.home

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.base.BaseFragment
import com.newangle.healthy.base.pageradapter.BasePagerAdapter
import com.newangle.healthy.databinding.ActivityHomeBinding
import com.newangle.healthy.pages.setting.about.AboutDeviceFragment
import com.newangle.healthy.pages.setting.info.DeviceInfoFragment
import com.newangle.healthy.pages.setting.parameter.HardwareParameterFragment


class HomeActivity : AppCompatActivity() {

    lateinit var homeBinding: ActivityHomeBinding
    private val activityComponent by lazy {
        (application as NewAngleApp).appComponent.activityComponent().create()
    }
    private var lastSelectThumb = HOME_TAG
    private var currentSelectThumb = HOME_TAG
    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        setUpView()
    }

    private fun  setUpView() {
        with(homeBinding) {
            navHome.setOnClickListener {
                currentSelectThumb = HOME_TAG
                moveSelectThumb(HOME_TAG)
                lastSelectThumb = HOME_TAG
                homeViewPager.setCurrentItem(0, false)
            }
            navLinks.setOnClickListener {
                currentSelectThumb = LINK_TAG
                moveSelectThumb(LINK_TAG)
                openWifiSettings()
            }
            navMusic.setOnClickListener {
                currentSelectThumb = MUSIC_TAG
                moveSelectThumb(MUSIC_TAG)
                openMediaLibrary()
            }
            navUsers.setOnClickListener {
                currentSelectThumb = USER_TAG
                moveSelectThumb(USER_TAG)
                lastSelectThumb = USER_TAG
                homeViewPager.setCurrentItem(1, false)
            }
            navSettings.setOnClickListener {
                currentSelectThumb = SETTING_TAG
                moveSelectThumb(SETTING_TAG)
                lastSelectThumb = SETTING_TAG
                homeViewPager.setCurrentItem(2, false)
            }
            homeViewPager.apply {
                setUserInputEnabled(false)
                val data = listOf<BaseFragment>(DeviceInfoFragment(), HardwareParameterFragment(), AboutDeviceFragment())
                adapter = BasePagerAdapter(data, this@HomeActivity)
                currentItem = 0
            }
        }
    }

    private fun moveSelectThumb(tag: String) {
        val desPosition = IntArray(2)
        when(tag) {
            HOME_TAG -> homeBinding.navHome.getLocationOnScreen(desPosition)
            LINK_TAG -> homeBinding.navLinks.getLocationOnScreen(desPosition)
            MUSIC_TAG -> homeBinding.navMusic.getLocationOnScreen(desPosition)
            USER_TAG -> homeBinding.navUsers.getLocationOnScreen(desPosition)
            SETTING_TAG -> homeBinding.navSettings.getLocationOnScreen(desPosition)
        }
        val dexY = desPosition[1]
        homeBinding.thumb.animate().y(dexY.toFloat()).setDuration(300).start()

    }

    // 打开WiFi设置
    private fun openWifiSettings() {
        try {
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    // 打开媒体库
    private fun openMediaLibrary() {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "audio/*")
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (currentSelectThumb != lastSelectThumb) {
            moveSelectThumb(lastSelectThumb)
        }
    }

    companion object {
        private const val HOME_TAG = "HomePage"
        private const val LINK_TAG = "LINKPage"
        private const val MUSIC_TAG = "MusicPage"
        private const val USER_TAG = "UserPage"
        private const val SETTING_TAG = "SettingPage"
    }
}