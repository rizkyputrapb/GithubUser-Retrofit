package com.example.githubuserdetailed.model

import android.net.Uri

data class User(
    val login: String,
    val avatar_url: Uri,
    val name: String,
    val company: String,
    val location: String,
    val followers: Double,
    val following: Double,
    val public_repos: Double
)