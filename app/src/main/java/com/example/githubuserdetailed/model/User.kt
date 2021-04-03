package com.example.githubuserdetailed.model

import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var login: String? = "",
    var avatar_url: String? = "",
    var name: String? = "",
    var company: String? = "",
    var location: String? = "",
    var email: String? = "",
    var bio: String? = "",
    var blog: String? = "",
    var followers: Int? = 0,
    var following: Int? = 0,
    var public_repos: Int? = 0
) : Parcelable

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(url)
            .into(view)
    }
}