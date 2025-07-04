package com.newangle.healthy.pages.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.R
import com.newangle.healthy.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    lateinit var homeBinding: ActivityHomeBinding
    private val activityComponent by lazy {
        (application as NewAngleApp).appComponent.activityComponent().create()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
    }
}