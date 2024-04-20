package com.angoga.android_nuclearhack.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angoga.android_nuclearhack.remote.model.request.LoginRequest
import com.angoga.android_nuclearhack.remote.model.request.RegistrationRequest
import com.angoga.android_nuclearhack.remote.model.response.LoginResponse
import com.angoga.android_nuclearhack.remote.model.response.UserResponse
import com.angoga.android_nuclearhack.remote.model.service.HttpClient
import com.angoga.android_nuclearhack.remote.model.service.LoginAndRegistrationService
import com.angoga.kfd_workshop_mobile.remote.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

import kotlinx.coroutines.launch

class RegisterViewModel(
    private val httpService: LoginAndRegistrationService
) : ViewModel() {
    private val _flow = MutableSharedFlow<Result<UserResponse>>()
    val flow: SharedFlow<Result<UserResponse>> = _flow

    fun register(email: String, password: String, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = httpService.register(RegistrationRequest(email, password, name))
                _flow.emit(Result.Success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                _flow.emit(Result.Error(e))
            }
        }
    }
}