package com.example.githubuserdetailed.api

import retrofit2.Callback
import androidx.lifecycle.MutableLiveData
import com.example.githubuserdetailed.model.User
import retrofit2.Response

class UserRepository {
    var apiInterface: ApiInterface = ApiClient.createService(ApiInterface::class.java)
    var user: MutableLiveData<User> = MutableLiveData()

    lateinit var userRepository: UserRepository

    constructor()

    fun getInstance(): UserRepository {
        userRepository = UserRepository()
        return userRepository
    }

    suspend fun getUser(username: String?) = apiInterface.getUser(username)

}