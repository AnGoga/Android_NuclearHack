package com.angoga.android_nuclearhack.remote.model.service

import android.content.Context
import com.angoga.android_nuclearhack.App
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val jwt = if (originalRequest.url().toString().contains("/public/web_auth/")
            || originalRequest.url().toString().contains("/waiting/web_auth/")) {
            App.app.getSharedPreferences("JWT", Context.MODE_PRIVATE).getString("WEB_JWT", "")!!
        } else {
            App.app.getSharedPreferences("JWT", Context.MODE_PRIVATE).getString("JWT", "")!!
        }

        val builder = originalRequest.newBuilder().header("Authorization", "Bearer $jwt")
        val newRequest = builder.build()
        return chain.proceed(newRequest)
    }
}

