package com.example.githubuserdetailed.ui.following

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.githubuserdetailed.api.Resource
import com.example.githubuserdetailed.api.UserListRepository
import com.example.githubuserdetailed.model.User
import kotlinx.coroutines.Dispatchers

class FollowingViewModel : ViewModel {
    var userListRep: UserListRepository
    private var userListMutableLiveData: MutableLiveData<List<User>>

    constructor() {
        userListRep = UserListRepository().getInstance()
        userListMutableLiveData = MutableLiveData<List<User>>()
    }

    fun getUserList(username: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = userListRep.getFollowingList(username)))
        } catch (exception: Exception) {
            emit(
                Resource.error(
                    data = null,
                    message = exception.message ?: "Unknown Error Occured!"
                )
            )
        }
    }
}