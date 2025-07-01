package com.newangle.healthy.common

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.NumberPicker
import com.newangle.healthy.R
import com.orhanobut.logger.Logger

class DataPickerDialog(context: Context,
                       private val onDateSelected: (year: Int, month: Int, day: Int) -> Unit)
    : Dialog(context, R.style.CustomDatePickerDialog) {

    lateinit var mDatePicker: DatePicker


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.date_picker_dialog)
        mDatePicker = findViewById<DatePicker>(R.id.date_picker)
        hideDatePickerDividers(mDatePicker)
        var instance = Calendar.getInstance()
        mDatePicker.init(instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(
            Calendar.DAY_OF_MONTH)) {
            view, year, monthOfYear, dayOfMonth ->
            Logger.e("weixiaolong ---- $year $monthOfYear $dayOfMonth")

        }
    }

    private fun removeDivider(numberPicker: NumberPicker) {
        // 移除分割线（API 11-28）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            numberPicker.selectionDividerHeight = 0
        }

        // 移除背景中的线条（所有API版本）
        if (Build.VERSION.SDK_INT >= 16) {
            numberPicker.background = null
        } else {
            numberPicker.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun hideDatePickerDividers(datePicker: DatePicker) {
        try {
            // 递归遍历所有子视图，移除所有NumberPicker的分割线
            removeNumberPickerDividers(datePicker)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun removeNumberPickerDividers(view: View) {
        if (view is NumberPicker) {
            removeDivider(view)
        } else if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                removeNumberPickerDividers(view.getChildAt(i))
            }
        }
    }
}