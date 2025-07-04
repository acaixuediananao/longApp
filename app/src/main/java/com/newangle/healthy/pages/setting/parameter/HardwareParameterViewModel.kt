package com.newangle.healthy.pages.setting.parameter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.newangle.healthy.di.fragment.FragmentComponent
import javax.inject.Inject

class HardwareParameterViewModel @Inject constructor(
    application: Application, fragmentComponent: FragmentComponent) : AndroidViewModel(application) {

    init {
        fragmentComponent.inject(this)
    }
}