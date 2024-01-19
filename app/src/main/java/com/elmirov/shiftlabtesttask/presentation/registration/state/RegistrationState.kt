package com.elmirov.shiftlabtesttask.presentation.registration.state

sealed interface RegistrationState {

    data object Initial: RegistrationState

    data object Content: RegistrationState
}