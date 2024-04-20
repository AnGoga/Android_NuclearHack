package com.angoga.android_nuclearhack.ui.screens.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.angoga.android_nuclearhack.databinding.ActivityHomeBinding
import com.angoga.android_nuclearhack.ui.screens.camera.CameraScanActivity
import com.google.zxing.integration.android.IntentIntegrator


class HomeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initOnClicks()
    }

    private fun initOnClicks() {
        binding.scanButton.setOnClickListener {
            scanQRCode()
        }
    }

    private fun scanQRCode() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Scan a QR code")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(false)
        integrator.setOrientationLocked(true)
        integrator.setBarcodeImageEnabled(true)
        integrator.setCaptureActivity(CameraScanActivity::class.java)

        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                println(result.contents)
                binding.scanResultTextView.text = result.contents
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}