package com.elmirov.shiftlabtesttask.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elmirov.shiftlabtesttask.domain.usecase.GetAuthorizationUseCase
import com.elmirov.shiftlabtesttask.navigation.router.MainActivityRouter
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val router: MainActivityRouter,
    private val getAuthorizationUseCase: GetAuthorizationUseCase,
) : ViewModel() {

    fun navigate() {
        viewModelScope.launch {
            getAuthorizationUseCase().collect {
                if (it)
                    router.openGreeting()
                else
                    router.openRegistration()
            }
        }
    }
}