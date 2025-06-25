package com.newangle.healthy.setting

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.R
import com.newangle.healthy.base.BaseActivity
import com.newangle.healthy.base.language.LanguageManager
import java.util.Locale
import javax.inject.Inject

class SettingActivity : BaseActivity() {
    @Inject
    lateinit var languageManager: LanguageManager
    @Inject
    lateinit var settingViewModel: SettingViewModel
    private val activityComponent by lazy {
        (application as NewAngleApp).appComponent.activityComponent().create()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<TextView>(R.id.taiwan).setOnClickListener {
            languageManager.switchLanguage(Locale.TAIWAN)
        }
        findViewById<TextView>(R.id.china).setOnClickListener {
            languageManager.switchLanguage(Locale.CHINA)
        }
        findViewById<TextView>(R.id.fr).setOnClickListener {
            languageManager.switchLanguage(Locale.FRANCE)
        }
    }
}