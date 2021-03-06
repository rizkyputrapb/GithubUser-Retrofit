package com.example.githubuserdetailed.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserdetailed.dao.Favorites
import com.example.githubuserdetailed.databinding.FragmentSettingsBinding
import com.example.githubuserdetailed.databinding.ItemFavoritesBinding
import com.example.githubuserdetailed.databinding.ItemSearchBinding
import com.example.githubuserdetailed.model.User

class FavoriteAdapter(onItemFavoriteListener: OnItemFavoriteListener) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private var favoriteList: List<User>? = null
    private var onItemFavoriteListener: OnItemFavoriteListener? = onItemFavoriteListener

    fun setFavList(favoritesList: List<User>?) {
        this.favoriteList = favoritesList
        notifyDataSetChanged()
    }

    class FavoriteViewHolder(private val binding: ItemFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User?, onItemFavoriteListener: OnItemFavoriteListener?) {
            binding.favorites = user
            Glide.with(itemView).load(user?.avatar_url).circleCrop()
                .into(binding.userAvatarFav)
            binding.onclick = onItemFavoriteListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFavoritesBinding.inflate(layoutInflater, parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorites = favoriteList?.get(position)
        holder.bind(favorites, onItemFavoriteListener)
    }

    override fun getItemCount(): Int {
        return if (favoriteList != null) {
            favoriteList!!.size
        } else {
            0
        }
    }
}