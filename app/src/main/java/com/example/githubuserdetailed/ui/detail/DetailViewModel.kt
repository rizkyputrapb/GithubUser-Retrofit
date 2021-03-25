package com.example.githubuserdetailed.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.githubuserdetailed.api.Resource
import com.example.githubuserdetailed.api.UserRepository
import com.example.githubuserdetailed.model.User
import kotlinx.coroutines.Dispatchers

class DetailViewModel : ViewModel {
    private var userMutableLiveData: MutableLiveData<User>
    private var userRepository: UserRepository

    constructor() {
        userRepository = UserRepository().getInstance()
        userMutableLiveData = MutableLiveData<User>()
    }

    fun getUser(username: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = userRepository.getUser(username)))
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