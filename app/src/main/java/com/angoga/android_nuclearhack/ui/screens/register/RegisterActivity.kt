package com.angoga.android_nuclearhack.ui.screens.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.angoga.android_nuclearhack.databinding.ActivityRegisterBinding
import com.angoga.android_nuclearhack.ui.screens.login.LoginActivity
import com.angoga.kfd_workshop_mobile.remote.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initOnClicks()
        initSubscribe()
    }

    private fun initSubscribe() {
        lifecycleScope.launch {
            viewModel.flow.collect {
                when(it) {
                    is Result.Success -> {
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    }
                    is Result.Error -> {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@RegisterActivity, it.e.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun initOnClicks() {
        binding.buttonSignup.setOnClickListener {
            val email = binding.editTextMail.text.toString()
            val password = binding.editTextPassword.text.toString()
            val name = binding.editTextName.text.toString()

            if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Введите данные", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            viewModel.register(email, password, name)
        }
    }
}