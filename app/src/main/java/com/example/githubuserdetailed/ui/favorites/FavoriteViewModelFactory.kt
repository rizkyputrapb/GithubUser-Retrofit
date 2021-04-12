package com.example.githubuserdetailed.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserdetailed.ui.detail.DetailViewModel
import java.lang.IllegalArgumentException

class FavoriteViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel() as T
        }
        throw IllegalArgumentException("ViewModel yang diminta FavoriteViewModel")
    }
}