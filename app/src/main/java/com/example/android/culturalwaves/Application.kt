package com.example.android.culturalwaves

import android.app.Application
import com.example.android.culturalwaves.di.appModules
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Инициализация Koin
        startKoin {
            modules(appModules)
        }
    }
}