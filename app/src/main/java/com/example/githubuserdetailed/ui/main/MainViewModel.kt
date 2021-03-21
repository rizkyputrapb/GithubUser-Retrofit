package com.example.githubuserdetailed.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserdetailed.api.Envelope
import com.example.githubuserdetailed.model.User
import com.example.githubuserdetailed.api.UserListRepository

class MainViewModel : ViewModel() {
    var userListRep: UserListRepository = UserListRepository().getInstance()
    private var userListMutableLiveData = MutableLiveData<Envelope<List<User>>>()
    private var userListLiveData = MutableLiveData<List<User>>()

    fun getUserList(username: String): MutableLiveData<Envelope<List<User>>> {
        return UserListRepository().getUserList(username)
    }

    fun listUserLiveData(): LiveData<List<User>> {
        return userListLiveData
    }
}