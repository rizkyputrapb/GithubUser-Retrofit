package com.example.githubuserdetailed.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.githubuserdetailed.api.Repository
import com.example.githubuserdetailed.api.Resource
import com.example.githubuserdetailed.model.User
import kotlinx.coroutines.Dispatchers

class MainViewModel : ViewModel() {
    var userListRep: Repository = Repository().getInstance()
    private val _navigatetoDetail = MutableLiveData<User?>()

    fun getUserList(username: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = userListRep.getUserList(username)))
        } catch (exception: Exception) {
            emit(
                Resource.error(
                    data = null,
                    message = exception.message ?: "Unknown Error Occured!"
                )
            )
        }
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