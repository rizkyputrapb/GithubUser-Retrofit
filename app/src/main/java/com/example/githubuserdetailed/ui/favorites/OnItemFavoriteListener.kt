package com.example.githubuserdetailed.ui.favorites

import com.example.githubuserdetailed.model.User

interface OnItemFavoriteListener {
    fun OnBtnDeleteListener(username: String)
    fun onItemClick(user: User)
}