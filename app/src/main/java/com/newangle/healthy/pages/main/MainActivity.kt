package com.newangle.healthy.pages.main

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.R
import com.newangle.healthy.base.BaseActivity
import com.newangle.healthy.base.logger.LogUtils
import com.newangle.healthy.pages.home.HomeActivity
import com.newangle.healthy.pages.register.RegisterActivity
import com.newangle.healthy.pages.setting.SettingActivity
import com.orhanobut.logger.Logger
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    lateinit var mViewModel : MainViewModel
    private val activityComponent by lazy {
        (application as NewAngleApp).appComponent.activityComponent().create()
    }
    lateinit var mStateTv : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mStateTv = findViewById<TextView>(R.id.state)
        mViewModel.state.observe(this) {
            LogUtils.i("main activity thread ${Thread.currentThread().name}")
            when(it) {
                MainViewModel.State.NOT_BEGIN -> mStateTv.setText("")
                is MainViewModel.State.FAILED -> mStateTv.setText(it.msg)
                MainViewModel.State.LOADING -> mStateTv.setText("loading.....")
                is MainViewModel.State.SUCCESS -> mStateTv.setText("登录成功")
            }
        }
        findViewById<TextView>(R.id.next_page).setOnClickListener {
            mViewModel.login("15803216916", "123456")
        }
        findViewById<TextView>(R.id.login_page).setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.register_user).setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}