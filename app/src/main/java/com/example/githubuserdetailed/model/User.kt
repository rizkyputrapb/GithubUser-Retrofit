package com.example.githubuserdetailed.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var login: String,
    var avatar_url: String,
    var name: String,
    var company: String?,
    var location: String?,
    var email: String?,
    var bio: String?,
    var blog: String?,
    var followers: Int,
    var following: Int,
    var public_repos: Int
): Parcelable