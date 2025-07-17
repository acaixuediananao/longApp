package com.newangle.healthy.pages.home.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.newangle.healthy.di.fragment.FragmentComponent
import javax.inject.Inject

class HomeOperationViewModel @Inject constructor(application: Application, fragmentComponent: FragmentComponent) : AndroidViewModel(application) {
    init {
        fragmentComponent.inject(this)
    }
}