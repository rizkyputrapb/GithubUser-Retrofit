package com.example.githubuserdetailed.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserdetailed.api.Envelope
import com.example.githubuserdetailed.model.User
import com.example.githubuserdetailed.api.UserListRepository

class MainViewModel() : ViewModel() {
    var userListRep: UserListRepository
    private var userListMutableLiveData: MutableLiveData<Envelope<List<User>>>
    private var userListLiveData = MutableLiveData<List<User>>()

    init {
        userListRep = UserListRepository().getInstance()
        userListMutableLiveData = MutableLiveData<Envelope<List<User>>>()
    }

    fun getUserList(username: String, context: Context): MutableLiveData<Envelope<List<User>>>? {
        return when(UserListRepository().getUserList(username, context)) {
            null -> null
            else -> UserListRepository().getUserList(username, context)
        }
    }

    fun listUserLiveData(): LiveData<List<User>> {
        return userListLiveData
    }
}