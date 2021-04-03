package com.example.githubuserdetailed.ui.following

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.githubuserdetailed.api.ApiRepository
import com.example.githubuserdetailed.api.Resource
import kotlinx.coroutines.Dispatchers

class FollowingViewModel : ViewModel() {
    var userListRep: ApiRepository = ApiRepository().getInstance()

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