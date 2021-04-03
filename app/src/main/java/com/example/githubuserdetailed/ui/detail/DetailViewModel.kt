package com.example.githubuserdetailed.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.githubuserdetailed.api.ApiRepository
import com.example.githubuserdetailed.api.Resource
import com.example.githubuserdetailed.model.User
import kotlinx.coroutines.Dispatchers


class DetailViewModel : ViewModel() {
    private var userRepository: ApiRepository = ApiRepository().getInstance()
    var user = MutableLiveData<User>()
    fun getUser(username: String?) = liveData(Dispatchers.Default) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = userRepository.getUser(username)))
            user.postValue(userRepository.getUser(username))
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