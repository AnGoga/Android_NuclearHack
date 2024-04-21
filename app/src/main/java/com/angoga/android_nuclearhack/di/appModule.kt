package com.angoga.android_nuclearhack.di

import com.angoga.android_nuclearhack.remote.model.service.HttpClient
import com.angoga.android_nuclearhack.remote.model.service.LoginAndRegistrationService
import com.angoga.android_nuclearhack.ui.screens.home.HomeViewModel
import com.angoga.android_nuclearhack.ui.screens.login.LoginViewModel
import com.angoga.android_nuclearhack.ui.screens.qr_login.QrLoginViewModel
import com.angoga.android_nuclearhack.ui.screens.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit


val authModule = module {
    single { HttpClient.retrofit.create(LoginAndRegistrationService::class.java) }
//    single { LoginAndRegistrationServiceMockImpl as LoginAndRegistrationService }
}

val appModule = module {
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { QrLoginViewModel(get()) }
} + authModule

