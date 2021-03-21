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

    fun getInstance(): UserRepository? {
        if (userRepository != null) {
            userRepository = UserRepository()
        }
        return userRepository
    }

    fun getUser(username: String): MutableLiveData<User> {
        var userData = this.apiInterface.getUser(username)
        userData.enqueue(object : Callback<User> {
            override fun onResponse(call: retrofit2.Call<User>, response: Response<User>) {
                user.value = response.body()
            }

            override fun onFailure(call: retrofit2.Call<User>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        return user
    }

}