package com.newangle.healthy.pages.wifi

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.R
import com.newangle.healthy.base.logger.LogUtils
import com.newangle.healthy.bean.UiState
import com.newangle.healthy.databinding.ActivityConnectWifiBinding
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConnectWifiActivity : AppCompatActivity() {

    lateinit var binding : ActivityConnectWifiBinding
    @Inject
    lateinit var viewModel: ConnectWifiViewModel

    private val activityComponent by lazy {
        (application as NewAngleApp).appComponent.activityComponent().create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        activityComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityConnectWifiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpView()
        setUpViewModel()
    }

    private fun  setUpView() {
        with(binding) {
            wifiListRv.apply {
                layoutManager = LinearLayoutManager(this@ConnectWifiActivity, LinearLayoutManager.HORIZONTAL, false);
                adapter = ConnectWifiAdapter()
            }
        }
    }

    private fun setUpViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { value ->
                    when(value) {
                        is UiState.LOADING -> LogUtils.i("")
                        else -> {

                        }
                    }
                }
            }
        }
        viewModel.loadWifiInfos(this)
    }
}