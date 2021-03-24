package com.example.githubuserdetailed.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserdetailed.ui.main.MainViewModel
import java.lang.IllegalArgumentException

class DetailViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel() as T
        }
        throw IllegalArgumentException("ViewModel yang diminta DetailViewModel")
    }
}