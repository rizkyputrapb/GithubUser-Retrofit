package com.example.githubuserdetailed.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserdetailed.api.Envelope
import com.example.githubuserdetailed.api.UserRepository
import com.example.githubuserdetailed.model.User

class DetailViewModel : ViewModel {
    private var userMutableLiveData: MutableLiveData<User>
    private var userRepository: UserRepository

    constructor() {
        userRepository = UserRepository().getInstance()
        userMutableLiveData = MutableLiveData<User>()
    }

    fun getUser(username: String?): MutableLiveData<User> {
        return userRepository.getUser(username)
    }
}