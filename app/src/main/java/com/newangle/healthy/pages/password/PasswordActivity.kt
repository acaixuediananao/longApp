package com.newangle.healthy.pages.password

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.newangle.healthy.R
import com.newangle.healthy.databinding.ActivityPasswordBinding
import com.newangle.healthy.pages.main.MainActivity

class PasswordActivity : AppCompatActivity() {
    lateinit var binding : ActivityPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpView()
    }

    private fun setUpView() {
        with(binding) {
            password.setOnClickListener {
                startActivity(Intent(this@PasswordActivity, MainActivity::class.java))
            }
        }
    }

    companion object {
        private const val TYPE = "page_type"
        const val TYPE_LOGIN_PASSWORD = "login_password"
        const val TYPE_SET_PASSWORD = "set_password"
    }
}