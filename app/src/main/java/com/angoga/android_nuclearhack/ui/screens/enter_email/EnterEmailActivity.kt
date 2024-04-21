package com.angoga.android_nuclearhack.ui.screens.enter_email

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.angoga.android_nuclearhack.R
import com.angoga.android_nuclearhack.databinding.ActivityEnterEmailBinding
import com.angoga.android_nuclearhack.databinding.ActivityQrLoginBinding
import com.angoga.android_nuclearhack.ui.screens.qr_login.QrLoginActivity

class EnterEmailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityEnterEmailBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initOnClicks()
    }

    private fun initOnClicks() {
        binding.continueButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            if (email.isBlank()) {
                Toast.makeText(this, R.string.enter_your_email, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            startActivity(Intent(this, QrLoginActivity::class.java).also {
                it.putExtra("email", email)
            })
        }
    }
}