package com.newangle.healthy.register

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.evaluateY
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.R
import com.newangle.healthy.base.BaseActivity
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterActivity : BaseActivity() {
    @Inject
    lateinit var mViewModel: RegisterViewModel
    private val activityComponent by lazy {
        (application as NewAngleApp).appComponent.activityComponent().create()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupView()
        observeViewModel()
    }

    private fun observeViewModel() {
        mViewModel.userNameError.observe(this, ) { error ->
            Logger.e("validate error $error")
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }

    }

    private fun setupView() {
        val userNameEditText = findViewById<EditText>(R.id.edit_user_name)
        val userPhoneEditText = findViewById<EditText>(R.id.edit_user_phone)
        val spinner = findViewById<Spinner>(R.id.edit_user_gender)
        val birthdayEditText = findViewById<EditText>(R.id.edit_user_birthday)
        val emailEditText = findViewById<EditText>(R.id.edit_user_email)
        val nurseEditText = findViewById<EditText>(R.id.edit_user_nurse)
        val confirm = findViewById<TextView>(R.id.confirm)
        userNameEditText.doOnTextChanged { text, _, _, _ ->
            mViewModel.onUserNameChanged(text.toString())
        }
        userPhoneEditText.doOnTextChanged { text, _, _, _ ->
            mViewModel.onUserPhoneChanged(text.toString())
        }
        ArrayAdapter.createFromResource(
            this,
            R.array.genders,
            android.R.layout.simple_spinner_item).also {
                adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedGender = parent?.getItemAtPosition(position).toString()
                mViewModel.onGenderSelected(selectedGender)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                mViewModel.onGenderSelected("")
            }
        }
        birthdayEditText.doOnTextChanged { text, _, _, _ ->
            mViewModel.onBirthdayChanged(text.toString())
        }
        emailEditText.doOnTextChanged { text, _, _, _ ->
            mViewModel.onEmailChanged(text.toString())
        }
        nurseEditText.doOnTextChanged { text, _, _, _ ->
            mViewModel.onNurseChanged(text.toString())
        }
        confirm.setOnClickListener {
            mViewModel.register()
        }
    }
}