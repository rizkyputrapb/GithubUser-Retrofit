package com.example.githubuserdetailed.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.githubuserdetailed.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserListRepository {
    var apiInterface: ApiInterface = ApiClient.createService(ApiInterface::class.java)
    var userList: MutableLiveData<Envelope<List<User>>> = MutableLiveData()
    var followerList: MutableLiveData<List<User>> = MutableLiveData()
    var followingList: MutableLiveData<List<User>> = MutableLiveData()
    lateinit var userListRepository: UserListRepository

    fun getInstance(): UserListRepository {
        userListRepository = UserListRepository()
        return userListRepository
    }

    suspend fun getFollowingList(username: String?) = apiInterface.getFollowing(username)

    suspend fun getFollowerList(username: String?) = apiInterface.getFollowers(username)

    suspend fun getUserList(username: String) = apiInterface.getSearchUsers(username)
}