package com.elmirov.shiftlabtesttask.navigation.router

import com.elmirov.shiftlabtesttask.navigation.screen.getGreetingScreen
import com.elmirov.shiftlabtesttask.navigation.screen.getRegistrationScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

interface MainActivityRouter {
    fun openRegistration()

    fun openGreeting()
}

class MainActivityRouterImpl @Inject constructor(
    private val router: Router,
) : MainActivityRouter {
    override fun openRegistration() {
        router.newRootScreen(getRegistrationScreen())
    }

    override fun openGreeting() {
        router.newRootScreen(getGreetingScreen())
    }
}