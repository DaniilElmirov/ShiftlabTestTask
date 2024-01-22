package com.elmirov.shiftlabtesttask.di.module

import com.elmirov.shiftlabtesttask.di.annotation.ApplicationScope
import com.elmirov.shiftlabtesttask.navigation.router.MainActivityRouter
import com.elmirov.shiftlabtesttask.navigation.router.MainActivityRouterImpl
import com.elmirov.shiftlabtesttask.navigation.router.RegistrationRouter
import com.elmirov.shiftlabtesttask.navigation.router.RegistrationRouterImpl
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [NavigationBindModule::class])
class NavigationModule {
    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @ApplicationScope
    fun provideRouter(): Router = cicerone.router

    @Provides
    @ApplicationScope
    fun provideNavigatorHolder(): NavigatorHolder = cicerone.getNavigatorHolder()
}

@Module
interface NavigationBindModule {

    @Binds
    @ApplicationScope
    fun bindMainActivityRouter(impl: MainActivityRouterImpl): MainActivityRouter

    @Binds
    @ApplicationScope
    fun bindRegistrationRouter(impl: RegistrationRouterImpl): RegistrationRouter
}