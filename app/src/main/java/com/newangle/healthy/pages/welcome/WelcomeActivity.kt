package com.newangle.healthy.pages.welcome


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.newangle.healthy.pages.main.MainActivity
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.R
import com.newangle.healthy.base.logger.LogUtils
import com.newangle.healthy.pages.language.SelectLanguageActivity
import com.newangle.healthy.pages.language.SelectLanguageActivity.Companion.FROM_KEY
import com.newangle.healthy.pages.language.SelectLanguageActivity.Companion.FROM_LAUNCH
import com.newangle.healthy.pages.wifi.ConnectWifiActivity
import com.newangle.healthy.persistence.DataStoreRepository
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

class WelcomeActivity : AppCompatActivity() {

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    private val handler: Handler = Handler(Looper.getMainLooper())

    private val activityComponent by lazy {
        (application as NewAngleApp).appComponent.activityComponent().create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                dataStoreRepository.firstLaunch
                    .take(1)
                    .collect {
                        LogUtils.i("first launch is ${it}")
                        dataStoreRepository.saveFirstLauncher(false)
                        handler.postDelayed({
                            startNexPage(it)
                        }, 5000)
                    }
            }
        }
    }

    private fun startNexPage(firstLaunch:Boolean) {
        val intent = if (firstLaunch) {
            Intent(
                this@WelcomeActivity,
                ConnectWifiActivity::class.java).putExtra(FROM_KEY, FROM_LAUNCH)
        } else {
            Intent(
                this@WelcomeActivity,
                MainActivity::class.java
            )
        }
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null);
    }
}