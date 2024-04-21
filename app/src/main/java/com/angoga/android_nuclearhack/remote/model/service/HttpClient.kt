package com.angoga.android_nuclearhack.remote.model.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HttpClient {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://158.160.1.175:8080")
//        .baseUrl("http://192.168.1.7:8080")
        .client(OkHttpClient().newBuilder().addInterceptor(AuthInterceptor()).build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}