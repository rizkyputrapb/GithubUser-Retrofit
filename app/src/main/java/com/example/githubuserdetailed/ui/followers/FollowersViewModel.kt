package com.example.githubuserdetailed.ui.followers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.githubuserdetailed.api.ApiRepository
import com.example.githubuserdetailed.api.Resource
import kotlinx.coroutines.Dispatchers

class FollowersViewModel : ViewModel() {
    var followerListRep: ApiRepository = ApiRepository().getInstance()
    fun getUserList(username: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = followerListRep.getFollowerList(username)))
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