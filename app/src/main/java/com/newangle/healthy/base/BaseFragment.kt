package com.newangle.healthy.base

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.newangle.healthy.base.logger.LogUtils
import com.orhanobut.logger.Logger

open class BaseFragment() : Fragment() {

    fun showError(msg : String) {
        LogUtils.e("show error msg $msg")
        Toast.makeText(context, msg ?: "未知错误", Toast.LENGTH_LONG).show()
    }
}