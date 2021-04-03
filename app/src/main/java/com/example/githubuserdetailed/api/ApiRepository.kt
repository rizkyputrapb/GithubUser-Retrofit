package com.example.githubuserdetailed.api

import androidx.lifecycle.MutableLiveData
import com.example.githubuserdetailed.model.User

class ApiRepository {
    var apiInterface: ApiInterface = ApiClient.createService(ApiInterface::class.java)
    var user: MutableLiveData<User> = MutableLiveData()
    lateinit var apiRepository: ApiRepository

    fun getInstance(): ApiRepository {
        apiRepository = ApiRepository()
        return apiRepository
    }

    suspend fun getUser(username: String?) = apiInterface.getUser(username)

    suspend fun getFollowingList(username: String?) = apiInterface.getFollowing(username)

    suspend fun getFollowerList(username: String?) = apiInterface.getFollowers(username)

    suspend fun getUserList(username: String) = apiInterface.getSearchUsers(username)
}