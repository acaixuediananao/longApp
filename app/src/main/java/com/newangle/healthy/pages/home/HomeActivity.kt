package com.newangle.healthy.pages.home

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.R
import com.newangle.healthy.base.BaseFragment
import com.newangle.healthy.base.logger.LogUtils
import com.newangle.healthy.base.pageradapter.BasePagerAdapter
import com.newangle.healthy.databinding.ActivityHomeBinding
import com.newangle.healthy.pages.home.home.HomeOperationFragment
import com.newangle.healthy.pages.setting.about.AboutDeviceFragment
import com.newangle.healthy.pages.setting.info.DeviceInfoFragment
import com.newangle.healthy.pages.setting.parameter.HardwareParameterFragment


class HomeActivity : AppCompatActivity() {

    lateinit var homeBinding: ActivityHomeBinding
    private var navItemList: MutableList<LinearLayout> = mutableListOf()
    private val activityComponent by lazy {
        (application as NewAngleApp).appComponent.activityComponent().create()
    }
    private var lastSelectThumb = -1
    private var currentSelectThumb = HOME_NAV_POSITION
    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        window.setBackgroundDrawable(null)
        setUpView()
    }

    private fun  setUpView() {
        with(homeBinding) {
            navItemList.add(navHome)
            navItemList.add(navLinks)
            navItemList.add(navMusic)
            navItemList.add(navUsers)
            navItemList.add(navSettings)
            for ((index, value) in navItemList.withIndex()) {
                value.setOnClickListener {
                    LogUtils.i("home click nav index $index, currentPosition $currentSelectThumb, lastSelectThumb $lastSelectThumb " )
                    if (currentSelectThumb != index) {
                        changeSelectState(index)
                    }
                    moveSelectThumb(index)
                    currentSelectThumb = index
                    when(index) {
                        HOME_NAV_POSITION -> {
                            lastSelectThumb = HOME_NAV_POSITION
                            homeViewPager.setCurrentItem(index, false)
                        }
                        LINK_NAV_POSITION -> {
                            LogUtils.i("home click nav wifi open wifi")
                            openWifiSettings()
                        }
                        MUSIC_NAV_POSITION -> {
                            LogUtils.i("home click nav music open music")
                            openMediaLibrary()
                        }
                        USER_NAV_POSITION -> {
                            lastSelectThumb = USER_NAV_POSITION
                            homeViewPager.setCurrentItem(1, false)
                        }
                        SETTING_NAV_POSITION -> {
                            lastSelectThumb = SETTING_NAV_POSITION
                            homeViewPager.setCurrentItem(2, false)
                        }
                    }
                    LogUtils.i("home click end currentPosition $currentSelectThumb, lastSelectThumb $lastSelectThumb " )
                }
            }

            homeViewPager.apply {
                setUserInputEnabled(false)
                val data = listOf<BaseFragment>(HomeOperationFragment.newInstance(), HardwareParameterFragment(), AboutDeviceFragment())
                adapter = BasePagerAdapter(data, this@HomeActivity)
                currentItem = 0
                changeSelectState(HOME_NAV_POSITION)
                lastSelectThumb = currentSelectThumb
            }
        }
    }

    private fun changeSelectState(position: Int) {
        LogUtils.i("changeSelectState position $position, currentPosition $currentSelectThumb, lastSelectThumb $lastSelectThumb " )
        val defaultColor = ContextCompat.getColor(this@HomeActivity, R.color.color_FF838392)
        val selectColor = ContextCompat.getColor(this@HomeActivity, R.color.color_12DCB8)
        with(homeBinding) {
            navItemList[currentSelectThumb].findViewWithTag<TextView>(NAV_TEXT_TAG)?.setTextColor(defaultColor)
            navItemList[currentSelectThumb].findViewWithTag<ImageView>(NAV_ICON_TAG)?.setColorFilter(defaultColor)
            for ((index, value) in navItemList.withIndex()) {
                if (position == index) {
                    value.findViewWithTag<TextView>(NAV_TEXT_TAG)?.setTextColor(selectColor)
                    value.findViewWithTag<ImageView>(NAV_ICON_TAG)?.setColorFilter(selectColor)
                    break
                }
            }
        }
    }

    private fun moveSelectThumb(position: Int) {
        LogUtils.i("move select thumb $position")
        val desPosition = IntArray(2)
        when(position) {
            HOME_NAV_POSITION -> homeBinding.navHome.getLocationOnScreen(desPosition)
            LINK_NAV_POSITION -> homeBinding.navLinks.getLocationOnScreen(desPosition)
            MUSIC_NAV_POSITION -> homeBinding.navMusic.getLocationOnScreen(desPosition)
            USER_NAV_POSITION -> homeBinding.navUsers.getLocationOnScreen(desPosition)
            SETTING_NAV_POSITION -> homeBinding.navSettings.getLocationOnScreen(desPosition)
        }
        val dexY = desPosition[1]
        LogUtils.i("move select thumb dexY $dexY")
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
        LogUtils.i("onRestart currentPosition $currentSelectThumb, lastSelectThumb $lastSelectThumb")
        if (currentSelectThumb != lastSelectThumb) {
            moveSelectThumb(lastSelectThumb)
            changeSelectState(lastSelectThumb)
            currentSelectThumb = lastSelectThumb
        }
    }

    companion object {
        private const val HOME_NAV_POSITION = 0
        private const val LINK_NAV_POSITION = 1
        private const val MUSIC_NAV_POSITION = 2
        private const val USER_NAV_POSITION = 3
        private const val SETTING_NAV_POSITION = 4

        private const val NAV_TEXT_TAG = "Text"
        private const val NAV_ICON_TAG = "Icon"
    }
}