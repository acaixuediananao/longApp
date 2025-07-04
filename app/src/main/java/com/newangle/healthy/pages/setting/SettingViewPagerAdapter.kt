package com.newangle.healthy.pages.setting

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.newangle.healthy.base.BaseFragment

class SettingViewPagerAdapter(
    private val data:List<BaseFragment>, private val fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun createFragment(position: Int): Fragment {
       return data[position]
    }

    override fun getItemCount(): Int = data.size
}