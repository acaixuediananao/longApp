package com.newangle.healthy

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.newangle.healthy.base.BaseActivity
import com.newangle.healthy.setting.SettingActivity
import com.orhanobut.logger.Logger

class MainActivity : BaseActivity() {
    lateinit var mViewModel : MainViewModel
    lateinit var mStateTv : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mStateTv = findViewById<TextView>(R.id.state)
        mViewModel = MainViewModel(application as NewAngleApp)
        mViewModel.state.observe(this) {
            Logger.i("main activity thread ${Thread.currentThread().name}")
            when(it) {
                MainViewModel.State.NOT_BEGIN -> mStateTv.setText("")
                is MainViewModel.State.FAILED -> mStateTv.setText(it.msg)
                MainViewModel.State.LOADING -> mStateTv.setText("loading.....")
                is MainViewModel.State.SUCCESS -> mStateTv.setText(it.user.androidId)
            }
        }
        findViewById<TextView>(R.id.next_page).setOnClickListener {
            mViewModel.login(123456L, "")
        }
        findViewById<TextView>(R.id.setting_page).setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }
}