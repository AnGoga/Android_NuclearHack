package com.angoga.android_nuclearhack.remote.model.service

import com.angoga.android_nuclearhack.remote.model.request.LoginRequest
import com.angoga.android_nuclearhack.remote.model.request.RegistrationRequest
import com.angoga.android_nuclearhack.remote.model.request.WebAuthLoginRequest
import com.angoga.android_nuclearhack.remote.model.request.WebAuthRegistrationRequest
import com.angoga.android_nuclearhack.remote.model.response.LoginResponse
import com.angoga.android_nuclearhack.remote.model.response.MessageResponse
import com.angoga.android_nuclearhack.remote.model.response.UserResponse
import com.angoga.android_nuclearhack.remote.model.response.WebAuthLoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginAndRegistrationService {
    @POST("/public/auth/registration")
    suspend fun register(@Body request: RegistrationRequest): UserResponse

    @POST("/public/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("/user/me")
    suspend fun getSelfProfile(): UserResponse

    // ---------------------- WEB AUTH ----------------------------

    @POST("/web_auth/grant_access")
    suspend fun grantAccess(@Body request: WebAuthLoginResponse): MessageResponse

    @POST("/web_auth/register")
    suspend fun webAuthRegister(@Body request: WebAuthRegistrationRequest): MessageResponse

}