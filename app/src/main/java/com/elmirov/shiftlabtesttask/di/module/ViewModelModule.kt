package com.elmirov.shiftlabtesttask.di.module

import androidx.lifecycle.ViewModel
import com.elmirov.shiftlabtesttask.di.annotation.ViewModelKey
import com.elmirov.shiftlabtesttask.presentation.activity.MainActivityViewModel
import com.elmirov.shiftlabtesttask.presentation.greeting.viewmodel.GreetingViewModel
import com.elmirov.shiftlabtesttask.presentation.registration.viewmodel.RegistrationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(GreetingViewModel::class)
    fun bindGreetingViewModel(viewModel: GreetingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    fun bindRegistrationViewModel(viewModel: RegistrationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    fun bindMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel
}