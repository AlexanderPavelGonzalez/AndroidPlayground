package com.example.androidplayground

import android.app.Application
import com.example.androidplayground.dagger.AppComponent
import com.example.androidplayground.dagger.AppModule
import com.example.androidplayground.dagger.DaggerAppComponent

class TCApplication : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
} 