package com.angoga.android_nuclearhack.ui.screens.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.angoga.android_nuclearhack.R
import com.angoga.android_nuclearhack.databinding.ActivityHomeBinding
import com.angoga.android_nuclearhack.remote.model.request.WebAuthLoginRequest
import com.angoga.android_nuclearhack.remote.model.response.UserResponse
import com.angoga.android_nuclearhack.remote.model.response.WebAuthLoginResponse
import com.angoga.android_nuclearhack.service.CryptoService
import com.angoga.android_nuclearhack.ui.screens.camera.CameraScanActivity
import com.angoga.kfd_workshop_mobile.remote.model.Result
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel



class HomeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initOnClicks()
        initUserProfile()
    }

    private fun initUserProfile() {
        lifecycleScope.launch {
            val result = viewModel.getSelfProfile()
            when (result) {
                is Result.Success -> {
                    renderUserInfo(result.data)
                }
                is Result.Error -> {}
            }
        }
    }

    private fun renderUserInfo(data: UserResponse) {
        binding.userProfileTextView.text = "${data.name}\n${data.email}"
    }

    private fun initOnClicks() {
        binding.scanButton.setOnClickListener {
            scanQRCode()
        }
    }

    private fun scanQRCode() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt(getString(R.string.scan_for_sign_in))
        integrator.setCameraId(0)
        integrator.setBeepEnabled(false)
        integrator.setOrientationLocked(true)
        integrator.setBarcodeImageEnabled(true)
        integrator.setCaptureActivity(CameraScanActivity::class.java)

        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Scanned", Toast.LENGTH_LONG).show()
                    println(result.contents)
                    binding.scanResultTextView.text = result.contents
                    initTryGrantAccess(result.contents)
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initTryGrantAccess(content: String) {
        val response = Gson().fromJson(content, WebAuthLoginRequest::class.java)
        val solved = CryptoService.decode(response.challenge, this)
        lifecycleScope.launch {
            viewModel.tryGrantAccess(WebAuthLoginResponse(response.sessionId, solved))
        }
    }


}