package com.newangle.healthy.pages.wifi

import android.Manifest
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.lifecycle.AndroidViewModel
import com.newangle.healthy.bean.UiState
import com.newangle.healthy.bean.WifiInfoBean
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ConnectWifiViewModel @Inject constructor( application: Application) : AndroidViewModel(application) {

    val wifiManager = application.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val wifiScanReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
            if (success) {
                scanSuccess()
            } else {
                scanFailure()
            }
        }
    }

    private val _uiState = MutableStateFlow<UiState<List<WifiInfoBean>>>(UiState.INIT)
    val uiState get() = _uiState.asStateFlow()

    fun loadWifiInfos(context: ConnectWifiActivity) {
        _uiState.value = UiState.LOADING
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        context.registerReceiver(wifiScanReceiver, intentFilter)
        val success = wifiManager.startScan()
        if (!success) {
            // scan failure handling
            scanFailure()
        }
    }

    private fun scanSuccess() {
//        val results = wifiManager.scanResults
//        val list : MutableList<WifiInfoBean> = mutableListOf()
//        results.map {
//            list.add(WifiInfoBean(it.wifiSsid.toString(), ""))
//        }
//        _uiState.value = UiState.SUCCESS(list)
    }

    private fun scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
//        val results = wifiManager.scanResults

    }
}