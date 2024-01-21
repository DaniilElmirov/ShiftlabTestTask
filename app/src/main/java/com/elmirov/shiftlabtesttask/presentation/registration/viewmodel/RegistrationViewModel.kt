package com.elmirov.shiftlabtesttask.presentation.registration.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.elmirov.shiftlabtesttask.domain.entity.User
import com.elmirov.shiftlabtesttask.domain.usecase.RegistrationUseCase
import com.elmirov.shiftlabtesttask.presentation.registration.state.ErrorType
import com.elmirov.shiftlabtesttask.presentation.registration.state.RegistrationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
) : ViewModel() {

    private companion object {
        private const val MIN_NAME_LENGTH = 2
        private const val MIN_SECOND_NAME_LENGTH = 2
        private const val DATE_OF_BIRTH_LENGTH = 10
    }

    private val _state = MutableStateFlow<RegistrationState>(RegistrationState.Initial)
    val state = _state.asStateFlow()

    fun registration(
        name: String,
        secondName: String,
        dateOfBirth: String,
        password: String,
        repeatedPassword: String,
    ) {
        if (
            isValidName(name) &&
            isValidSecondName(secondName) &&
            isValidDate(dateOfBirth) &&
            isValidPassword(password) &&
            isPasswordConfirmed(password, repeatedPassword)
        ) {
            val user = User(name, secondName, dateOfBirth, password)
            Log.d("USER", user.toString())
            registrationUseCase(user)
        }
    }

    fun reset() {
        _state.value = RegistrationState.Initial
    }

    fun allFilled(filled: Boolean) {
        if (filled)
            _state.value = RegistrationState.Filled
        else
            _state.value = RegistrationState.Initial
    }

    private fun isValidName(name: String): Boolean {
        return if (name.length >= MIN_NAME_LENGTH) {
            true
        } else {
            _state.value = RegistrationState.InputError(ErrorType.Name)
            false
        }
    }

    private fun isValidSecondName(secondName: String): Boolean {
        return if (secondName.length >= MIN_SECOND_NAME_LENGTH) {
            true
        } else {
            _state.value = RegistrationState.InputError(ErrorType.SecondName)
            false
        }
    }

    private fun isValidDate(dateOfBirth: String): Boolean {
        //TODO() переделать правила валидации
        return if (dateOfBirth.length == 1) {
            true
        } else {
            _state.value = RegistrationState.InputError(ErrorType.DateOfBirth)
            false
        }
    }

    private fun isValidPassword(password: String): Boolean {

        val digit = "\\d".toRegex()
        val smallLetter = "[a-z]".toRegex()
        val bigLetter = "[A-Z]".toRegex()
        val space = "\\s".toRegex()

        return if (
            password.contains(digit) &&
            password.contains(smallLetter) &&
            password.contains(bigLetter) &&
            !password.contains(space)
        ) {
            true
        } else {
            _state.value = RegistrationState.InputError(ErrorType.Password)
            false
        }
    }

    private fun isPasswordConfirmed(password: String, repeatedPassword: String): Boolean {
        return if (password == repeatedPassword) {
            true
        } else {
            _state.value = RegistrationState.InputError(ErrorType.RepeatedPassword)
            false
        }
    }
}