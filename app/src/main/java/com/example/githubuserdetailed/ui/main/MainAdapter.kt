package com.example.githubuserdetailed.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserdetailed.databinding.ItemSearchBinding
import com.example.githubuserdetailed.model.User

class MainAdapter(onItemUserListener: OnItemUserListener) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var userList: List<User>? = null
    private var onItemUserListener: OnItemUserListener? = onItemUserListener

    fun setUserList(userList: List<User>) {
        this.userList = userList
        notifyDataSetChanged()
    }

    inner class MainViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User?, onItemUserListener: OnItemUserListener?) {
            binding.user = user
            Glide.with(itemView).load(user?.avatar_url).circleCrop().into(binding.imageView2)
            binding.onitemclicklistener = onItemUserListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchBinding.inflate(layoutInflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val user = userList?.get(position)
        Log.d("onBindViewHolder", "username: ${user?.login}")
        holder.bind(user, onItemUserListener)
    }

    override fun getItemCount(): Int {
        return if (userList != null) {
            userList!!.size
        } else {
            0
        }
    }
}