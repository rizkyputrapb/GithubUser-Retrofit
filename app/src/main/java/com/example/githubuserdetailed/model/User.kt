package com.example.githubuserdetailed.model

data class User(
    val login: String,
    val avatar_url: String,
    val name: String,
    val company: String,
    val location: String,
    val followers: Double,
    val following: Double,
    val public_repos: Double
)