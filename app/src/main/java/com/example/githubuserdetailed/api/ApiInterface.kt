package com.example.githubuserdetailed.api

import com.example.githubuserdetailed.api.Constants.AUTH_TOKEN
import com.example.githubuserdetailed.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import javax.annotation.Resource
import javax.annotation.Resources

interface ApiInterface {
    @GET("/users/{username}")
    fun getUser(
        @Path("username") username: String
    ): Call<User>

    @GET("/search/users?")
    @Headers("Authorization: token $AUTH_TOKEN")
    fun getSearchUsers(
        @Query("q") username: String
    ): Call<Envelope<List<User>>>
}