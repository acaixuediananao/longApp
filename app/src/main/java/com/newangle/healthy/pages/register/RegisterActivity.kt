package com.newangle.healthy.pages.register

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.R
import com.newangle.healthy.base.BaseActivity
import com.newangle.healthy.common.DatePickerDialog
import com.newangle.healthy.databinding.ActivityRegisterBinding
import com.newangle.healthy.login.LoginViewModel
import com.orhanobut.logger.Logger
import java.util.Calendar
import javax.inject.Inject

class RegisterActivity : BaseActivity() {

    private lateinit var mBinding : ActivityRegisterBinding;
    @Inject
    lateinit var mViewModel: RegisterViewModel
    private val activityComponent by lazy {
        (application as NewAngleApp).appComponent.activityComponent().create()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupView()
        observeViewModel()
    }

    private fun observeViewModel() {
        mViewModel.validateResult.observe(this) { error ->
            error?.getContentIfNotHandled()?.let {
                Logger.e("validate error $it")
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
        mViewModel.uiState.observe(this) { state ->
            state.birthday?.let {
                mBinding.editUserBirthday.text = it.toDisplayFormat()
            }
        }

        mViewModel.registerResultState.observe(this) { state ->
            when(state) {
                is LoginViewModel.State.LOADING -> showLoading()
                is LoginViewModel.State.SUCCESS -> Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show()
                is LoginViewModel.State.FAILED -> Toast.makeText(this, state.msg, Toast.LENGTH_LONG).show()
                else -> {

                }
            }
        }
    }

    private fun setupView() {
        with(mBinding) {
            editUserName.doOnTextChanged { text, _, _, _ ->
                mViewModel.onUserNameChanged(text.toString()) }
            editUserPhone.doOnTextChanged { text, _, _, _ ->
                mViewModel.onUserPhoneChanged(text.toString())
            }
            setupGender()
            editUserBirthday.setOnClickListener { showDatePickerDialog() }
            editUserEmail.doOnTextChanged { text, _, _, _ ->
                mViewModel.onEmailChanged(text.toString())
            }
            editUserNurse.doOnTextChanged { text, _, _, _ ->
                mViewModel.onNurseChanged(text.toString())
            }
            confirm.setOnClickListener { mViewModel.register() }
        }
    }

    private fun setupGender() {
        ArrayAdapter.createFromResource(
            this,
            R.array.genders,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mBinding.editUserGender.adapter = adapter
        }
        mBinding.editUserGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
    }

    private fun showDatePickerDialog() {
        val initialDate = mViewModel.uiState.value?.birthday?: Calendar.getInstance()
        DatePickerDialog(initialDate, this) { year, month, day ->
            mViewModel.onBirthdayChanged(year, month, day)

        }.show()

    }

    private fun showLoading()  {

    }

    private fun Calendar.toDisplayFormat(): String {
        return "${get(Calendar.YEAR)} - ${get(Calendar.MONTH) + 1} - ${get(Calendar.DAY_OF_MONTH)}"
    }

}