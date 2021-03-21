package com.example.githubuserdetailed.api

import com.example.githubuserdetailed.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("/users/{username}")
    fun getUser(
        @Path("username") username: String
    ): Call<User>

    @GET("/search/users?")
    fun getSearchUsers(
        @Query("q") username: String
    ): Call<Envelope<List<User>>>
}