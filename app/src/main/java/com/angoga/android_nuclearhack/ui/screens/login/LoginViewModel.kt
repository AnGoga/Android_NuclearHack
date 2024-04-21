package com.angoga.android_nuclearhack.ui.screens.login

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angoga.android_nuclearhack.App
import com.angoga.android_nuclearhack.remote.model.request.LoginRequest
import com.angoga.android_nuclearhack.remote.model.request.WebAuthRegistrationRequest
import com.angoga.android_nuclearhack.remote.model.response.LoginResponse
import com.angoga.android_nuclearhack.remote.model.response.MessageResponse
import com.angoga.android_nuclearhack.remote.model.service.LoginAndRegistrationService
import com.angoga.android_nuclearhack.service.CryptoService
import com.angoga.kfd_workshop_mobile.remote.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch


class LoginViewModel(
    private val httpService: LoginAndRegistrationService,
) : ViewModel() {
    private val _flow = MutableSharedFlow<Result<LoginResponse>>()
    val flow: SharedFlow<Result<LoginResponse>> = _flow


    fun login(email: String, password: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = httpService.login(LoginRequest(email, password))
                saveJwt(result)
                sendKeyPair(context)
                _flow.emit(Result.Success(result))
            } catch (e: Exception) {
                e.printStackTrace()
                _flow.emit(Result.Error(e))
            }
        }
    }

    suspend fun sendKeyPair(context: Context): MessageResponse {
        val pair = CryptoService.generateKeyPairAndSave(context)
        val publicKey = CryptoService.publicKeyToString(pair.public)
        val privateKey = CryptoService.privateKeyToString(pair.private)
        return httpService.webAuthRegister(WebAuthRegistrationRequest(publicKey, privateKey))
    }

    private fun saveJwt(result: LoginResponse) {
        App.app.getSharedPreferences("JWT", AppCompatActivity.MODE_PRIVATE).edit().putString("JWT", result.accessJwt).commit()
    }
}