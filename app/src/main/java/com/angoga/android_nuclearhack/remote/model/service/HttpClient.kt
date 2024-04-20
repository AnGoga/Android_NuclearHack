package com.angoga.android_nuclearhack.remote.model.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HttpClient {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://localhost:8080/api/v1/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}