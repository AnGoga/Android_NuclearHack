package com.angoga.android_nuclearhack.remote.model.service

import com.angoga.android_nuclearhack.remote.model.request.LoginRequest
import com.angoga.android_nuclearhack.remote.model.request.RegistrationRequest
import com.angoga.android_nuclearhack.remote.model.request.WebAuthLoginRequest
import com.angoga.android_nuclearhack.remote.model.response.WebAuthLoginResponse
import com.angoga.android_nuclearhack.remote.model.request.WebAuthGrantAccessRequest
import com.angoga.android_nuclearhack.remote.model.request.WebAuthRegistrationRequest
import com.angoga.android_nuclearhack.remote.model.response.GrantedAccessResponse
import com.angoga.android_nuclearhack.remote.model.response.LoginResponse
import com.angoga.android_nuclearhack.remote.model.response.MessageResponse
import com.angoga.android_nuclearhack.remote.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginAndRegistrationService {

    @POST("${API_V1_PUBLIC}auth/registration")
    suspend fun register(@Body request: RegistrationRequest): LoginResponse

    @POST("${API_V1_PUBLIC}auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("${API_V1}user/me")
    suspend fun getSelfProfile(): UserResponse

    // ---------------------- WEB AUTH ----------------------------

    @POST("${API_V1}web_auth/grant_access")
    suspend fun grantAccess(@Body request: WebAuthGrantAccessRequest): MessageResponse

    @POST("${API_V1_PUBLIC}web_auth/login")
    suspend fun webLogin(@Body request: WebAuthLoginRequest): WebAuthLoginResponse

    @POST("${API_V1_PUBLIC}web_auth/check_access")
    suspend fun checkAccess(): Response<GrantedAccessResponse>

    @POST("${API_V1}web_auth/registration")
    suspend fun webAuthRegister(@Body request: WebAuthRegistrationRequest): MessageResponse

    companion object {
        const val API_V1 = "api/v1/"
        const val API_V1_PUBLIC = "api/v1/public/"
    }

}