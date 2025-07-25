package com.example.messenger.dagger

import dagger.Module
import dagger.Provides
import repo.MessageRepo

@Module
class MessengerModule {

    @Provides
    fun provideMessageRepo(): MessageRepo {
        return MessageRepo()
    }
} 