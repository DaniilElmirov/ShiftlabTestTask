package com.elmirov.shiftlabtesttask.navigation.screen

import com.elmirov.shiftlabtesttask.ui.RegistrationFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getRegistrationScreen(): FragmentScreen = FragmentScreen {
    RegistrationFragment.newInstance()
}
