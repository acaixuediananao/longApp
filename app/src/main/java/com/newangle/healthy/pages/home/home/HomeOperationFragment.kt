package com.newangle.healthy.pages.home.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.base.BaseFragment
import com.newangle.healthy.base.logger.LogUtils
import com.newangle.healthy.databinding.HomeOperationFragmentLayoutBinding
import com.newangle.healthy.di.fragment.FragmentComponent
import javax.inject.Inject

class HomeOperationFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: HomeOperationViewModel
    private lateinit var binding: HomeOperationFragmentLayoutBinding

    private val fragmentComponent: FragmentComponent by lazy {
        ((requireActivity().application) as NewAngleApp).appComponent.fragmentComponent().create()
    }

    override fun onAttach(context: Context) {
        fragmentComponent.inject(this)
        super.onAttach(context)
        LogUtils.i("weixiaolong---- home operation fragment on attach ${System.currentTimeMillis()}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeOperationFragmentLayoutBinding.inflate(inflater, container, false)
        LogUtils.i("weixiaolong---- home operation fragment on create view  ${System.currentTimeMillis()}")
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        LogUtils.i("weixiaolong---- home operation fragment on resume ${System.currentTimeMillis()}")
    }

    companion object {
        fun newInstance(): HomeOperationFragment {
            val args = Bundle()
            val fragment = HomeOperationFragment()
            fragment.arguments = args
            return fragment
        }
    }
}