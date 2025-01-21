package com.ritesh.movietvapp.activities

import android.app.Application
import com.ritesh.movietvapp.module.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidContext(this@MyApp)
            modules(appModule)
        }
    }
}