package com.elmirov.shiftlabtesttask.presentation.registration.viewmodel

import androidx.lifecycle.ViewModel
import com.elmirov.shiftlabtesttask.domain.entity.User
import com.elmirov.shiftlabtesttask.domain.usecase.RegistrationUseCase
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

    private val _isCorrectName = MutableStateFlow(true)
    val isCorrectName = _isCorrectName.asStateFlow()

    private val _isCorrectSecondName = MutableStateFlow(true)
    val isCorrectSecondName = _isCorrectSecondName.asStateFlow()

    private val _isCorrectDateOfBirth = MutableStateFlow(true)
    val isCorrectDateOfBirth = _isCorrectDateOfBirth.asStateFlow()

    private val _isCorrectPassword = MutableStateFlow(true)
    val isCorrectPassword = _isCorrectPassword.asStateFlow()

    private val _isPasswordConfirmed = MutableStateFlow(true)
    val isPasswordConfirmed = _isPasswordConfirmed.asStateFlow()

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
            registrationUseCase(user)
        }
    }

    private fun isValidName(name: String): Boolean {
        return if (name.length >= MIN_NAME_LENGTH) {
            _isCorrectName.value = true
            true
        } else {
            _isCorrectName.value = false
            false
        }
    }

    private fun isValidSecondName(secondName: String): Boolean {
        return if (secondName.length >= MIN_SECOND_NAME_LENGTH) {
            _isCorrectSecondName.value = true
            true
        } else {
            _isCorrectSecondName.value = false
            false
        }
    }

    private fun isValidDate(dateOfBirth: String): Boolean {
        //TODO() переделать правила валидации
        return if (dateOfBirth.length == DATE_OF_BIRTH_LENGTH) {
            _isCorrectDateOfBirth.value = true
            true
        } else {
            _isCorrectDateOfBirth.value = false
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
            _isCorrectPassword.value = true
            true
        } else {
            _isCorrectPassword.value = false
            false
        }
    }

    private fun isPasswordConfirmed(password: String, repeatedPassword: String): Boolean {
        return if (password == repeatedPassword) {
            _isPasswordConfirmed.value = true
            true
        } else {
            _isPasswordConfirmed.value = false
            false
        }
    }

    fun resetIsCorrectName() {
        _isCorrectName.value = true
    }

    fun resetIsCorrectSecondName() {
        _isCorrectSecondName.value = true
    }

    fun resetIsCorrectDateOfBirth() {
        _isCorrectDateOfBirth.value = true
    }

    fun resetIsCorrectPassword() {
        _isCorrectPassword.value = true
    }

    fun resetIsPasswordConfirmed() {
        _isPasswordConfirmed.value = true
    }
}