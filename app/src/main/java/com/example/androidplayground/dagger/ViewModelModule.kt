package com.example.androidplayground.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import viewmodel.MessageScreenViewModel
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MessageScreenViewModel::class)
    abstract fun bindMessengerViewModel(viewModel: MessageScreenViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
} 