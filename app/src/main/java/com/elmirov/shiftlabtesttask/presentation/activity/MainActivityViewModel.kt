package com.elmirov.shiftlabtesttask.presentation.activity

import androidx.lifecycle.ViewModel
import com.elmirov.shiftlabtesttask.domain.usecase.GetUserNameUseCase
import com.elmirov.shiftlabtesttask.navigation.router.MainActivityRouter
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val router: MainActivityRouter,
    private val getUserNameUseCase: GetUserNameUseCase,
): ViewModel() {

    fun navigate() {
        val name = getUserNameUseCase()

        if (name != null)
            router.openGreeting()
        else
            router.openRegistration()
    }
}