package com.example.githubuserdetailed.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MainViewModelFactory : ViewModelProvider.Factory {
    private var username: String

    constructor(username: String) {
        this.username = username
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(username) as T
        }
        throw IllegalArgumentException("ViewModel yang diminta MainViewModel")
    }
}