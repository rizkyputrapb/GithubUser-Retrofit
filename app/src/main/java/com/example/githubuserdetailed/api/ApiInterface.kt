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
    @Headers("Authorization: token $AUTH_TOKEN")
    suspend fun getUser(
        @Path("username") username: String?
    ): User

    @GET("https://api.github.com/users/{username}/followers")
    @Headers("Authorization: token $AUTH_TOKEN")
    suspend fun getFollowers(
        @Path("username") username: String?
    ): List<User>

    @GET("https://api.github.com/users/{username}/following")
    @Headers("Authorization: token $AUTH_TOKEN")
    suspend fun getFollowing(
        @Path("username") username: String?
    ): List<User>

    @GET("/search/users?")
    @Headers("Authorization: token $AUTH_TOKEN")
    suspend fun getSearchUsers(
        @Query("q") username: String
    ): Envelope<List<User>>
}