package com.angoga.android_nuclearhack.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angoga.android_nuclearhack.remote.model.request.LoginRequest
import com.angoga.android_nuclearhack.remote.model.response.LoginResponse
import com.angoga.android_nuclearhack.remote.model.service.HttpClient
import com.angoga.android_nuclearhack.remote.model.service.LoginAndRegistrationService
import com.angoga.kfd_workshop_mobile.remote.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

import kotlinx.coroutines.launch

class LoginViewModel(
    private val httpService: LoginAndRegistrationService
) : ViewModel() {
    private val _flow = MutableSharedFlow<Result<LoginResponse>>()
    val flow: SharedFlow<Result<LoginResponse>> = _flow


    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = httpService.login(LoginRequest(email, password))
                _flow.emit(Result.Success(result))
            } catch (e: Exception) {
                e.printStackTrace()
                _flow.emit(Result.Error(e))
            }
        }
    }
}