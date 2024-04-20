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

object LoginAndRegistrationServiceMockImpl : LoginAndRegistrationService {
    override suspend fun register(request: RegistrationRequest): UserResponse {
        return UserResponse(123, "111@gamil;.com", "NAME")
    }

    override suspend fun login(request: LoginRequest): LoginResponse {
        return LoginResponse("MY_JWT")
    }

    override suspend fun getSelfProfile(): UserResponse {
        return UserResponse(123, "111@gamil;.com", "NAME")
    }

    override suspend fun grantAccess(request: WebAuthLoginResponse): MessageResponse {
        return MessageResponse("GOOD???")
    }

    override suspend fun webAuthRegister(request: WebAuthRegistrationRequest): MessageResponse {
        return MessageResponse("GOOD???")
    }


}