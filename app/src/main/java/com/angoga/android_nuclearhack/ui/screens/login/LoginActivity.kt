package com.angoga.android_nuclearhack.ui.screens.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.angoga.android_nuclearhack.databinding.ActivityLoginBinding
import com.angoga.android_nuclearhack.remote.model.response.LoginResponse
import com.angoga.android_nuclearhack.ui.screens.register.RegisterActivity
import com.angoga.android_nuclearhack.ui.screens.home.HomeActivity
import com.angoga.kfd_workshop_mobile.remote.model.Result
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        tryGetJwt()
        initOnClicks()
        initSubscribe()
    }

    private fun initSubscribe() {
        lifecycleScope.launch {
            viewModel.flow.collect { result ->
                when(result) {
                    is Result.Success -> {
                        saveJwt(result)
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        finish()
                    }

                    is Result.Error -> {
                        Toast.makeText(this@LoginActivity, result.e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }

    private fun tryGetJwt() {
        val jwt = getSharedPreferences("JWT", MODE_PRIVATE).getString("JWT", null) ?: return
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        finish()
    }
    private fun saveJwt(result: Result.Success<LoginResponse>) {
        getSharedPreferences("JWT", MODE_PRIVATE).edit().putString("JWT", result.data.accessJwt).commit()
    }

    private fun initOnClicks() {
        binding.textViewNotRegisteredYet.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.buttonSignup.setOnClickListener {
            tryLogin()
        }
    }

    private fun tryLogin() {
        val email = binding.editTextMail.text.toString()
        val password = binding.editTextPassword.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Введите данные", Toast.LENGTH_LONG).show()
            return
        }
        viewModel.login(email, password, this)
    }
}