package com.elmirov.shiftlabtesttask.di.module

import androidx.lifecycle.ViewModel
import com.elmirov.shiftlabtesttask.di.annotation.ViewModelKey
import com.elmirov.shiftlabtesttask.presentation.greeting.viewmodel.GreetingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(GreetingViewModel::class)
    fun bindGreetingViewModel(viewModel: GreetingViewModel): ViewModel
}