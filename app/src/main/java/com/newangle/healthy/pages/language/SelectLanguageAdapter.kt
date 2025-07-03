package com.newangle.healthy.pages.language

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newangle.healthy.databinding.SelectLanguageItemBinding
import java.util.Locale

class SelectLanguageAdapter(private val dataSource:List<Locale>, private val itemClick : (position : Int) -> Unit) :
    RecyclerView.Adapter<SelectLanguageAdapter.SelectLanguageViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectLanguageViewHolder {
        var inflate =
            SelectLanguageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectLanguageViewHolder(inflate)
    }

    override fun onBindViewHolder(
        holder: SelectLanguageViewHolder,
        position: Int
    ) {
        holder.binding.selectLanguageItemTv.text = dataSource[position].displayLanguage
        holder.binding.root.setOnClickListener {itemClick(position)}
    }

    override fun getItemCount(): Int = dataSource.size

    class SelectLanguageViewHolder(val binding: SelectLanguageItemBinding)
        : RecyclerView.ViewHolder(binding.root)
}