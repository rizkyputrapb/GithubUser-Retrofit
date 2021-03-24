package com.example.githubuserdetailed.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserdetailed.api.Envelope
import com.example.githubuserdetailed.model.User
import com.example.githubuserdetailed.api.UserListRepository

class MainViewModel : ViewModel {
    var userListRep: UserListRepository
    private var userListMutableLiveData: MutableLiveData<Envelope<List<User>>>
    private val _navigatetoDetail = MutableLiveData<User?>()

    constructor() {
        userListRep = UserListRepository().getInstance()
        userListMutableLiveData = MutableLiveData<Envelope<List<User>>>()
    }

    fun getUserList(username: String): MutableLiveData<Envelope<List<User>>>? {
        return UserListRepository().getUserList(username)
    }

    fun navigatetoDetail(): LiveData<User?> {
        return _navigatetoDetail
    }

    fun onUserClicked(user: User?) {
        _navigatetoDetail.value = user
    }

    fun onUserMainDetailNavigated() {
        _navigatetoDetail.value = null
    }
}