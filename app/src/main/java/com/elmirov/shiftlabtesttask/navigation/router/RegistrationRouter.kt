package com.elmirov.shiftlabtesttask.navigation.router

import com.elmirov.shiftlabtesttask.navigation.screen.getGreetingScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

interface RegistrationRouter {
    fun openGreeting()
}

class RegistrationRouterImpl @Inject constructor(
    private val router: Router,
) : RegistrationRouter {
    override fun openGreeting() {
        router.newRootScreen(getGreetingScreen())
    }
}