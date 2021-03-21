package com.example.githubuserdetailed.ui.main

import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserdetailed.databinding.ItemSearchBinding
import com.example.githubuserdetailed.model.User

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var userList: List<User>? = null

    fun setUserList(userList: List<User>) {
        this.userList = userList
        notifyDataSetChanged()
    }

    inner class MainViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User?) {
            binding.user = user
            Glide.with(itemView).load(user?.avatar_url).circleCrop().into(binding.imageView2)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}