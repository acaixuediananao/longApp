package com.newangle.healthy.pages.language

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.newangle.healthy.pages.main.MainActivity
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.base.BaseActivity
import com.newangle.healthy.base.language.LanguageManager
import com.newangle.healthy.databinding.ActivitySelectLanguageBinding
import com.newangle.healthy.pages.password.PasswordActivity
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
        mBinding = ActivitySelectLanguageBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setUpView()
    }

    fun setUpView() {
        with(mBinding) {
            selectLanguageRv.apply {
                layoutManager = LinearLayoutManager(this@SelectLanguageActivity, LinearLayoutManager.VERTICAL, false)
                val dataSource = listOf(Locale.ENGLISH, Locale.CHINESE, Locale.FRANCE, Locale.TAIWAN)
                adapter = SelectLanguageAdapter(dataSource) { position ->
                    languageManager.switchLanguage(dataSource[position])
                }
            }
            nextStep.setOnClickListener {
                startActivity(Intent(this@SelectLanguageActivity, PasswordActivity::class.java))
            }
            nextStep.visibility = if(FROM_LAUNCH.equals(intent.getStringExtra(FROM_KEY)))
                View.VISIBLE
            else
                View.GONE
        }
    }

    companion object {
        const val FROM_KEY = "from_key"
        const val FROM_LAUNCH = "from_launch"
    }
}