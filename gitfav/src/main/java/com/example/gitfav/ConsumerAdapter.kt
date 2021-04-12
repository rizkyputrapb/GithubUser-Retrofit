package com.example.gitfav

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitfav.databinding.ListConsumerfavBinding

class ConsumerAdapter : RecyclerView.Adapter<ConsumerAdapter.FavoriteViewHolder>() {
    private var favconsumerList: List<FavConsumer>? = null

    fun setConsumerList(consumerList: List<FavConsumer>?) {
        this.favconsumerList = consumerList
        this.notifyDataSetChanged()
    }

    class FavoriteViewHolder(private val binding: ListConsumerfavBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favConsumer: FavConsumer?) {
            binding.fav = favConsumer
            Glide.with(itemView).load(favConsumer?.avatar_url).into(binding.userAvatarFav)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListConsumerfavBinding.inflate(layoutInflater, parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorites = favconsumerList?.get(position)
        holder.bind(favorites)
    }

    override fun getItemCount(): Int {
        return if (favconsumerList != null) {
            favconsumerList!!.size
        } else {
            0
        }
    }
}