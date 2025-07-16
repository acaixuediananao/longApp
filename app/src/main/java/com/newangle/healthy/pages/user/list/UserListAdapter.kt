package com.newangle.healthy.pages.user.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newangle.healthy.bean.User
import com.newangle.healthy.databinding.UserListItemBinding
import com.newangle.healthy.pages.user.list.UserListAdapter.UserListViewHolder
import kotlin.collections.mutableListOf

class UserListAdapter(val data: MutableList<User> = mutableListOf<User>(),
                      val buttonClick:(user: User) -> Unit,
                      val deleteClick:(user:User) -> Unit
    ) : RecyclerView.Adapter<UserListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserListViewHolder {
        val binding = UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: UserListViewHolder,
        position: Int
    ) {
        val user = data[position]
        with(holder.binding) {
            userListItemName.text = user.userName
            userListItemEmail.text = user.email
            userListItemRightBtn.setOnClickListener { v -> buttonClick(user) }
        }
    }

    override fun getItemCount() = data.size

    fun refresh(data:List<User>) {
        this.data.apply {
            clear()
            addAll(data)
            notifyDataSetChanged()
        }
    }


    class UserListViewHolder(val binding: UserListItemBinding) : RecyclerView.ViewHolder(binding.root)
}