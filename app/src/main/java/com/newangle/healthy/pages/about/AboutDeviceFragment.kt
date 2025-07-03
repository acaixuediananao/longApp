package com.newangle.healthy.pages.about

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.base.BaseFragment
import com.newangle.healthy.databinding.AboutDeviceFragmentBinding
import com.newangle.healthy.databinding.DeviceInfoFragmentBinding
import com.newangle.healthy.pages.info.DeviceInfoViewModel
import javax.inject.Inject

class AboutDeviceFragment() : BaseFragment() {

    lateinit var mBinding: AboutDeviceFragmentBinding
    @Inject
    lateinit var aboutDeviceViewModel: AboutDeviceViewModel

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
        mBinding = AboutDeviceFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }
}