package com.newangle.healthy.pages.setting.info

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.base.BaseFragment
import com.newangle.healthy.bean.DeviceInfo
import com.newangle.healthy.bean.UiState
import com.newangle.healthy.databinding.DeviceInfoFragmentBinding
import com.newangle.healthy.utils.DisplayUtils
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeviceInfoFragment() : BaseFragment() {

    lateinit var mBinding: DeviceInfoFragmentBinding
    @Inject
    lateinit var deviceInfoViewModel: DeviceInfoViewModel
    private val mAdapter = DeviceInfoAdapter()
    private val span get() = DisplayUtils.dip2px(requireContext(), 20f)

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
                    mBinding = DeviceInfoFragmentBinding.inflate(inflater, container, false)
                    setUpView()
                    return mBinding.root
                }

                private fun setUpView() {
                    with(mBinding){
                        deviceInfoListRv.apply {
                            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                            adapter = mAdapter
                            addItemDecoration(DeviceInfoItemDecoration(span))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                deviceInfoViewModel.mData.collect { value ->
                    when(value) {
                        is UiState.LOADING -> showLoading()
                        is UiState.SUCCESS -> updateUi(value.data)
                        is UiState.FAILED -> showError(value.msg)
                        else -> {

                        }
                    }
                }
            }
        }
        deviceInfoViewModel.loadAllDeviceInfo()
    }


    private fun  showLoading() {

    }

    private fun updateUi(deviceInfos : List<DeviceInfo>) {
        mAdapter.setData(deviceInfos)
    }
}

private class DeviceInfoItemDecoration(val span: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(span, span, span, span)
    }
}