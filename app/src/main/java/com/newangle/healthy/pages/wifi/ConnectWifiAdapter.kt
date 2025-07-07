package com.newangle.healthy.pages.wifi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newangle.healthy.bean.WifiInfoBean
import com.newangle.healthy.databinding.WifiListItemViewBinding

class ConnectWifiAdapter(val dataSource: List<WifiInfoBean> = emptyList())
    : RecyclerView.Adapter<ConnectWifiAdapter.ConnectWifiViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConnectWifiViewHolder {
        var binding = WifiListItemViewBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ConnectWifiViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ConnectWifiViewHolder,
        position: Int
    ) {
        var info = dataSource[position]
        holder.binding.wifiListRvItem.text = info.wifiName
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    class ConnectWifiViewHolder(val binding: WifiListItemViewBinding)
        : RecyclerView.ViewHolder(binding.root)
}