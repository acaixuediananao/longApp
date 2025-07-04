package com.newangle.healthy.pages.setting.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newangle.healthy.bean.DeviceInfo
import com.newangle.healthy.databinding.DeviceInfoItemLayoutBinding
import okhttp3.internal.notify

class DeviceInfoAdapter(private var data : List<DeviceInfo> = emptyList())
    : RecyclerView.Adapter<DeviceInfoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val minding = DeviceInfoItemLayoutBinding
            .inflate(LayoutInflater.from(parent.context))
        return ViewHolder(minding)

    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        var deviceInfo = data[position]
        holder.displayName.text = deviceInfo.showName
        holder.displayValue.text = deviceInfo.value
    }

    override fun getItemCount(): Int = data.size

    fun setData(newData : List<DeviceInfo>) {
        data = newData.toList()
        notifyDataSetChanged()
    }

    class ViewHolder(val mBinding: DeviceInfoItemLayoutBinding) : RecyclerView.ViewHolder(mBinding.root) {
        val displayName = mBinding.deviceInfoItemCardDisplayName
        val displayValue = mBinding.deviceInfoItemCardDisplayValue
    }

}