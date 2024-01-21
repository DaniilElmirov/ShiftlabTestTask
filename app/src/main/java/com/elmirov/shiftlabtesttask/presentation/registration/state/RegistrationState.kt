package com.elmirov.shiftlabtesttask.presentation.registration.state

sealed interface RegistrationState {

    data object Initial : RegistrationState

    data object Filled : RegistrationState

    data class InputError(
        val type: ErrorType,
    ) : RegistrationState
}

enum class ErrorType {
    Name,
    SecondName,
    DateOfBirth,
    Password,
    RepeatedPassword
}