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
    lateinit var userListRepository: UserListRepository

    fun getInstance(): UserListRepository {
        userListRepository = UserListRepository()
        return userListRepository
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
            }

        })
        return userList
    }
}