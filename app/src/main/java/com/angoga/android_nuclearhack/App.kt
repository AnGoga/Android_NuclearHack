package com.angoga.android_nuclearhack

import android.app.Application
import com.angoga.android_nuclearhack.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    companion object {
        lateinit var app: App
    }
    override fun onCreate() {
        super.onCreate()
        app = this
        initDI()
    }

    private fun initDI() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }
}