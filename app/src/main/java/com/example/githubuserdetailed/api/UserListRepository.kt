package com.example.githubuserdetailed.api

import androidx.lifecycle.MutableLiveData
import com.example.githubuserdetailed.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserListRepository {
    var apiInterface: ApiInterface = ApiClient.createService(ApiInterface::class.java)
    var userList: MutableLiveData<List<User>> = MutableLiveData()
    lateinit var userListRepository: UserListRepository

    fun getInstance(): UserListRepository {
        if (userListRepository != null) {
            userListRepository = UserListRepository()
        }
        return userListRepository
    }

    fun getUserList(username: String): MutableLiveData<List<User>> {
        var userData = this.apiInterface.getSearchUsers(username)
        userData.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                userList.value = response.body()
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        return userList
    }
}