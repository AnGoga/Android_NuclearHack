package com.angoga.android_nuclearhack.ui.screens.qr_login

import android.R.attr
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.angoga.android_nuclearhack.App
import com.angoga.android_nuclearhack.databinding.ActivityQrLoginBinding
import com.angoga.android_nuclearhack.remote.model.dto.QrDto
import com.angoga.android_nuclearhack.remote.model.response.LoginResponse
import com.angoga.android_nuclearhack.service.CryptoService
import com.angoga.android_nuclearhack.ui.screens.home.HomeActivity
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.HttpURLConnection


class QrLoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityQrLoginBinding.inflate(layoutInflater) }
    private val viewModel: QrLoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        lifecycleScope.launch {
            val result = viewModel.getChallenge(intent.getStringExtra("email").toString())
            saveWebJwt(result.checkJwt)
            withContext(Dispatchers.Main) {
                renderQr(Gson().toJson(QrDto(result.sessionId, result.challenge)))
            }
            checkAccess()
        }
    }

    private suspend fun checkAccess() {
        while (true) {
            delay(1000)
            try {
                val result = viewModel.checkAccess()
                when (result.code()) {
                    HttpURLConnection.HTTP_OK -> {
                        val response = result.body()!!
                        saveJwt(response.accessJwt)
                        savePrivateKey(response.privateKey)

                        withContext(Dispatchers.Main) {
                            startActivity(Intent(this@QrLoginActivity, HomeActivity::class.java))
                            finish()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun saveWebJwt(jwt: String) {
        getSharedPreferences("JWT", MODE_PRIVATE).edit().putString("WEB_JWT", jwt).commit()
    }

    private fun savePrivateKey(key: String) {
        getSharedPreferences("KEYS", Context.MODE_PRIVATE).edit().putString("KEY_PRIVATE", key).commit()
    }

    private fun saveJwt(jwt: String) {
        getSharedPreferences("JWT", MODE_PRIVATE).edit().putString("JWT", jwt).commit()
    }

    private fun renderQr(text: String) {
        try {
            val writer = QRCodeWriter()
            val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 1024, 1024)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) BLACK else WHITE)
                }
            }
            binding.qrCodeImageView.setImageBitmap(bmp)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }
}