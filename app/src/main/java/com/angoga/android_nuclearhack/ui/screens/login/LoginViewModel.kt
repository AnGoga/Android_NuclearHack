package com.angoga.android_nuclearhack.ui.screens.login

import android.content.Context
import android.provider.Settings.Secure
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import java.security.AccessController.getContext


class LoginViewModel(
    private val httpService: LoginAndRegistrationService,
) : ViewModel() {
    private val _flow = MutableSharedFlow<Result<LoginResponse>>()
    val flow: SharedFlow<Result<LoginResponse>> = _flow


    fun login(email: String, password: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = httpService.login(LoginRequest(email, password))
                sendPublicKey(context)
                _flow.emit(Result.Success(result))
            } catch (e: Exception) {
                e.printStackTrace()
                _flow.emit(Result.Error(e))
            }
        }
    }

    suspend fun sendPublicKey(context: Context): MessageResponse {
        val publicKey = CryptoService.generateKeyPairAndSave(context).let { CryptoService.publicKeyToString(it.public) }
        val fingerprint = getUniqueID(context)
        return httpService.webAuthRegister(WebAuthRegistrationRequest(publicKey, fingerprint))
    }

    private fun getUniqueID(context: Context): String {
        return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID)
    }
}