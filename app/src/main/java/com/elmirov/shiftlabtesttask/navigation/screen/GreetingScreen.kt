package com.elmirov.shiftlabtesttask.navigation.screen

import com.elmirov.shiftlabtesttask.ui.GreetingFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getGreetingScreen(): FragmentScreen = FragmentScreen {
    GreetingFragment.newInstance()
}