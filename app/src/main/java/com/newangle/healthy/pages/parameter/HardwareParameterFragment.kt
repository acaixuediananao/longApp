package com.newangle.healthy.pages.parameter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.base.BaseFragment
import com.newangle.healthy.databinding.AboutDeviceFragmentBinding
import com.newangle.healthy.databinding.DeviceInfoFragmentBinding
import com.newangle.healthy.databinding.HardwareParameterFragmentBinding
import com.newangle.healthy.pages.about.AboutDeviceViewModel
import com.newangle.healthy.pages.info.DeviceInfoViewModel
import javax.inject.Inject

class HardwareParameterFragment() : BaseFragment() {

    lateinit var mBinding: HardwareParameterFragmentBinding
    @Inject
    lateinit var hardwareParameterViewModel: HardwareParameterViewModel

    private val fragmentComponent by lazy {
        (requireActivity().application as NewAngleApp).appComponent.fragmentComponent().create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentComponent.inject(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = HardwareParameterFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }
}