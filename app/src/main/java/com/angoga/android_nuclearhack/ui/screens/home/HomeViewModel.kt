package com.angoga.android_nuclearhack.ui.screens.home

import androidx.lifecycle.ViewModel
import com.angoga.android_nuclearhack.remote.model.request.WebAuthLoginRequest
import com.angoga.android_nuclearhack.remote.model.response.WebAuthLoginResponse
import com.angoga.android_nuclearhack.remote.model.service.LoginAndRegistrationService
import com.angoga.kfd_workshop_mobile.remote.model.Result
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.withContext

class HomeViewModel(
    private val httpService: LoginAndRegistrationService
) : ViewModel() {
//    private val _flow = MutableSharedFlow<Result<UserResponse>>()
//    val flow: SharedFlow<Result<LoginResponse>> = _flow

    suspend fun getSelfProfile() = withContext(Dispatchers.IO) {
        try {
            val data = httpService.getSelfProfile()
            return@withContext Result.Success(data)
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext Result.Error(e)
        }
    }

    suspend fun tryGrantAccess(request: WebAuthLoginResponse) = withContext(Dispatchers.IO) {
        try {
            val message = httpService.grantAccess(request)
            Result.Success(message)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e)
        }
    }
}