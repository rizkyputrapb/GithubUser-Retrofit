package com.example.githubuserdetailed.ui.following

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserdetailed.api.UserListRepository
import com.example.githubuserdetailed.model.User

class FollowingViewModel : ViewModel{
    var userListRep: UserListRepository
    private var userListMutableLiveData: MutableLiveData<List<User>>

    constructor(){
        userListRep = UserListRepository().getInstance()
        userListMutableLiveData = MutableLiveData<List<User>>()
    }

    fun getUserList(username: String?): MutableLiveData<List<User>> {
        return UserListRepository().getFollowingList(username)
    }
}