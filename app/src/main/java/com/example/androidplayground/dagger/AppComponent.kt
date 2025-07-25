package com.example.androidplayground.dagger

import com.example.androidplayground.MainActivity
import com.example.messenger.dagger.MessengerModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    MessengerModule::class,
    ViewModelModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        fun appModule(module: AppModule): Builder
        fun build(): AppComponent
    }
    fun inject(activity: MainActivity)
} 