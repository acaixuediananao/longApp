package com.newangle.healthy.common

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.NumberPicker
import com.newangle.healthy.R
import com.newangle.healthy.databinding.DatePickerDialogBinding
import com.orhanobut.logger.Logger
import java.util.Calendar
import java.util.LinkedList

class DatePickerDialog(
    time: Calendar = Calendar.getInstance(),
    context: Context,
    private val onDateSelected: (Int, Int, Int) -> Unit
) : Dialog(context, R.style.CustomDatePickerDialog) {

    private lateinit var mBinding: DatePickerDialogBinding
    private var mSelectYear = 0
    private var mSelectMonth = 0
    private var mSelectDay = 0

    init {
        mSelectYear = time.get(Calendar.YEAR)
        mSelectMonth = time.get(Calendar.MONTH)
        mSelectDay = time.get(Calendar.DAY_OF_MONTH)
    }

    private val mInitListener = DatePicker.OnDateChangedListener { _, year, month, day ->
        mSelectYear = year
        mSelectMonth = month
        mSelectDay = day
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DatePickerDialogBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        setupViews()
    }

    private fun setupViews() {
        with(mBinding) {
            cancel.setOnClickListener { dismiss() }
            confirm.setOnClickListener {
                onDateSelected(mSelectYear, mSelectMonth, mSelectDay)
                dismiss()
            }
            datePicker.init(mSelectYear, mSelectMonth, mSelectDay, mInitListener)
            datePicker.hideDividers()
        }
    }

    private fun  DatePicker.hideDividers() {
        try {
            traverseViews { view ->
                if (view is NumberPicker) view.removeDivider()
            }
        } catch (e: Exception) {
            Logger.e(e, "Error hiding date picker dividers")
        }
    }

    // 递归遍历视图的扩展函数
    private fun ViewGroup.traverseViews(action: (View) -> Unit) {
        val queue = LinkedList<View>()
        for (i in 0 until childCount) {
            queue.add(getChildAt(i))
        }

        while (queue.isNotEmpty()) {
            val current = queue.poll()
            if (current != null) {
                action(current)
            }

            if (current is ViewGroup) {
                for (i in 0 until current.childCount) {
                    queue.add(current.getChildAt(i))
                }
            }
        }
    }

    // 移除NumberPicker分割线的扩展函数
    private fun NumberPicker.removeDivider() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            selectionDividerHeight = 0
        }
        background = null
    }
}