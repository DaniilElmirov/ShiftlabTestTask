package com.elmirov.shiftlabtesttask.presentation.registration.viewmodel

import androidx.lifecycle.ViewModel
import com.elmirov.shiftlabtesttask.domain.entity.User
import com.elmirov.shiftlabtesttask.domain.usecase.RegistrationUseCase
import com.elmirov.shiftlabtesttask.navigation.router.RegistrationRouter
import com.elmirov.shiftlabtesttask.presentation.registration.state.ErrorType
import com.elmirov.shiftlabtesttask.presentation.registration.state.RegistrationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    private val registrationRouter: RegistrationRouter,
) : ViewModel() {

    private companion object {
        private const val MIN_NAME_LENGTH = 2
        private const val MIN_SECOND_NAME_LENGTH = 2
        private const val TARGET_DATE_FORMAT = "dd.MM.yyyy"
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
            registrationUseCase(user)
            registrationRouter.openGreeting()
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
            _state.value = RegistrationState.InputError(ErrorType.NameLength)
            false
        }
    }

    private fun isValidSecondName(secondName: String): Boolean {
        return if (secondName.length >= MIN_SECOND_NAME_LENGTH) {
            true
        } else {
            _state.value = RegistrationState.InputError(ErrorType.SecondNameLength)
            false
        }
    }

    private fun isValidDate(dateOfBirth: String): Boolean {
        val targetFormat = SimpleDateFormat(TARGET_DATE_FORMAT, Locale.getDefault())
        targetFormat.isLenient = false

        val parsedDate = try {
            targetFormat.parse(dateOfBirth)
        } catch (e: Exception) {
            _state.value = RegistrationState.InputError(ErrorType.DateWrongFormat)
            return false
        }

        return if (parsedDate.after(Date())) {
            _state.value = RegistrationState.InputError(ErrorType.FutureDate)
            false
        } else {
            true
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
            _state.value = RegistrationState.InputError(ErrorType.SimplePassword)
            false
        }
    }

    private fun isPasswordConfirmed(password: String, repeatedPassword: String): Boolean {
        return if (password == repeatedPassword) {
            true
        } else {
            _state.value = RegistrationState.InputError(ErrorType.NoMatchPassword)
            false
        }
    }
}