package com.newangle.healthy.pages.language

import android.content.Intent
import android.os.Bundle
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.newangle.healthy.MainActivity
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.base.BaseActivity
import com.newangle.healthy.base.language.LanguageManager
import com.newangle.healthy.databinding.ActivitySelectLanguageBinding
import java.util.Locale
import javax.inject.Inject

class SelectLanguageActivity : BaseActivity() {

    @Inject
    lateinit var languageManager: LanguageManager
    lateinit var mBinding: ActivitySelectLanguageBinding

    private val activityComponent by lazy {
        (application as NewAngleApp).appComponent.activityComponent().create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivitySelectLanguageBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUpView()
    }

    fun setUpView() {
        mBinding.selectLanguageRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val dataSource = listOf(Locale.ENGLISH, Locale.CHINESE, Locale.FRANCE, Locale.TAIWAN)
        mBinding.selectLanguageRv.adapter =
            SelectLanguageAdapter(dataSource) {
            position ->
                languageManager.switchLanguage(dataSource[position])
                startActivity(Intent(this@SelectLanguageActivity, MainActivity::class.java))
                finish()
        }
    }
}