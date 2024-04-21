package com.angoga.android_nuclearhack.ui.screens.qr_login

import androidx.lifecycle.ViewModel
import com.angoga.android_nuclearhack.remote.model.request.WebAuthLoginRequest
import com.angoga.android_nuclearhack.remote.model.response.GrantedAccessResponse
import com.angoga.android_nuclearhack.remote.model.service.LoginAndRegistrationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class QrLoginViewModel(
    private val httpService: LoginAndRegistrationService
) : ViewModel() {

    suspend fun getChallenge(email: String) = withContext(Dispatchers.IO) {
        httpService.webLogin(WebAuthLoginRequest(email))
    }

    suspend fun checkAccess(): Response<GrantedAccessResponse> {
        return httpService.checkAccess()
    }
}