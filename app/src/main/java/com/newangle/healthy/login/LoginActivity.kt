package com.newangle.healthy.login

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.R
import com.newangle.healthy.base.logger.LogUtils
import com.newangle.healthy.pages.register.RegisterActivity
import com.orhanobut.logger.Logger
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    val activityComponent by lazy {
        (application as NewAngleApp).appComponent.activityComponent().create()
    }
    @Inject
    lateinit var loginViewModel: LoginViewModel
    lateinit var phone: String
    lateinit var password:String

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        loginViewModel.state.observe(this) {
            val value = loginViewModel.state.value
            when(value) {
                is LoginViewModel.State.LOADING ->
                    LogUtils.i("login showloading")
                is LoginViewModel.State.SUCCESS ->
                    jumpRegisterPage()
                is LoginViewModel.State.FAILED -> {
                    val fAILED = it as LoginViewModel.State.FAILED
                    Toast.makeText(this, fAILED.msg, Toast.LENGTH_LONG).show()
                }
                else -> {

                }
            }
        }
        setUpViews()
    }

    fun setUpViews() {
        findViewById<EditText>(R.id.phone).doOnTextChanged { text, start, before, count ->
            LogUtils.i("phone input is {$text}")
            phone = text.toString()
        }
        findViewById<EditText>(R.id.password).doOnTextChanged {text, start, before, count ->
            LogUtils.i("password input is $text")
            password = text.toString()
        }
       findViewById<TextView>(R.id.login).setOnClickListener {
            loginViewModel.login(phone, password)
        }
    }

    val jumpRegisterPage = {
        intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}