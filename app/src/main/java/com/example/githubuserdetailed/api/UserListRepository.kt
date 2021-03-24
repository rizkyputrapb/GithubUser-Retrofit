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

    fun getFollowingList(username: String?): MutableLiveData<List<User>> {
        var userData = this.apiInterface.getFollowing(username)
        userData.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                followingList.value = response.body()
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                followingList.value = null
            }

        })
        return followingList
    }

    fun getFollowerList(username: String?): MutableLiveData<List<User>> {
        var userData = this.apiInterface.getFollowers(username)
        userData.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                followerList.value = response.body()
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                followerList.value = null
            }

        })
        return followerList
    }

    fun getUserList(username: String): MutableLiveData<Envelope<List<User>>> {
        var userData = this.apiInterface.getSearchUsers(username)
        userData.enqueue(object : Callback<Envelope<List<User>>> {
            override fun onResponse(
                call: Call<Envelope<List<User>>>,
                response: Response<Envelope<List<User>>>
            ) {
                userList.value = response.body()
            }

            override fun onFailure(call: Call<Envelope<List<User>>>, t: Throwable) {
                Log.w("API", "Failed to retrieve api: $t")
                userList.value = null
            }

        })
        return userList
    }
}