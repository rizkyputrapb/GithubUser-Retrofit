package com.example.githubuserdetailed.ui.following

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserdetailed.databinding.ItemFollowsBinding
import com.example.githubuserdetailed.model.User

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {
    var userList: List<User>? = null

    fun setUsersList(userList: List<User>) {
        this.userList = userList
        notifyDataSetChanged()
    }

    class FollowingViewHolder(private val binding: ItemFollowsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User?) {
            binding.followName.text = user?.login
            Glide.with(itemView).load(user?.avatar_url ?: "").circleCrop().into(binding.imageView)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFollowsBinding.inflate(layoutInflater, parent, false)
        return FollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        val user = userList?.get(position)
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return userList?.size ?: 0
    }
}