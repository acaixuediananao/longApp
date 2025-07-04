package com.newangle.healthy.pages.setting.parameter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.base.BaseFragment
import com.newangle.healthy.databinding.HardwareParameterFragmentBinding
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