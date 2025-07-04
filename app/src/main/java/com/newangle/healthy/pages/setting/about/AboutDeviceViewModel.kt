package com.newangle.healthy.pages.setting.about

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.newangle.healthy.di.fragment.FragmentComponent
import javax.inject.Inject

class AboutDeviceViewModel @Inject constructor(
    application: Application, fragmentComponent: FragmentComponent) : AndroidViewModel(application) {

    init {
        fragmentComponent.inject(this)
    }
}