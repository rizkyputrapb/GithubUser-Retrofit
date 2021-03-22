package com.example.githubuserdetailed.api

import com.example.githubuserdetailed.api.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    var builder =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) as Retrofit.Builder
    var retrofit = builder.build() as Retrofit
    var logging =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) as HttpLoggingInterceptor
    var httpClient = OkHttpClient.Builder()

    fun <S> createService(serviceClass: Class<S>): S {
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging)
            builder.client(httpClient.build())
            retrofit = builder.build()
        }
        return retrofit.create(serviceClass)
    }
}