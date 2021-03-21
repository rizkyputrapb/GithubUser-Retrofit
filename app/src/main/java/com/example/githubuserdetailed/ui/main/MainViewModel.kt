package com.example.githubuserdetailed.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserdetailed.model.User
import com.example.githubuserdetailed.api.UserListRepository

class MainViewModel : ViewModel {
    var userListRep: UserListRepository = UserListRepository().getInstance()
    private lateinit var userListMutableLiveData: MutableLiveData<List<User>>

    constructor(username: String?) {
        if (username != null) {
            this.userListMutableLiveData = UserListRepository().getUserList(username)
        }
    }

    fun getUserList(): MutableLiveData<List<User>> {
        return userListMutableLiveData
    }
}